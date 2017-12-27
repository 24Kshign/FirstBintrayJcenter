package cn.mifengkong.frhttp.http.cloudapi;


import com.mifengkong.frtools.util.FRDebugMode;
import com.mifengkong.frtools.util.FRString;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import cn.mifengkong.frhttp.constant.HttpConstant;
import cn.mifengkong.frhttp.constant.SdkConstant;
import cn.mifengkong.frhttp.http.cloudapi.sign.SignUtil;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class CloudInterceptorUtil {

    /****
     * 设置请求头的Interceptor
     *
     * @return
     * @throws Exception
     */
    public static Interceptor getCloudInterceptor(final boolean isKamao) {
        if (FRCloudAPIUtil.isUserCloudAPiHost())
            return new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = buildRequestToAddHeads(chain.request(), isKamao);
                    return chain.proceed(request);
                }
            };
        else
            return new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    return chain.proceed(chain.request());
                }
            };
    }

    /***
     * 设置请求的headers
     * @param oldrequest
     * @param isKamao
     * @return
     */
    public static Request buildRequestToAddHeads(Request oldrequest, boolean isKamao) {
        Date currentTime = new Date();
        Map<String, String> headerParams = new HashMap<>();
        for (String s : oldrequest.headers().names()) {
            oldrequest.headers().get(s);
            headerParams.put(s, oldrequest.headers().get(s));
        }
        Map<String, String> formParams = new HashMap<>();

        //设置请求头中的时间戳
        headerParams.put(HttpConstant.CLOUDAPI_HTTP_HEADER_DATE, getHttpDateHeaderValue(currentTime));

        //设置请求头中的时间戳，以timeIntervalSince1970的形式
        headerParams.put(SdkConstant.CLOUDAPI_X_CA_TIMESTAMP, FRString.valueOf(currentTime.getTime()));

        //请求放重放Nonce,15分钟内保持唯一,建议使用UUID
        headerParams.put(SdkConstant.CLOUDAPI_X_CA_NONCE, FRString.valueOf(UUID.randomUUID().toString()));

        //设置请求头中的UserAgent
        headerParams.put(HttpConstant.CLOUDAPI_HTTP_HEADER_USER_AGENT, SdkConstant.CLOUDAPI_USER_AGENT);

        //设置请求头中的主机地址
        headerParams.put(HttpConstant.CLOUDAPI_HTTP_HEADER_HOST, FRString.valueOf(oldrequest.url().host()));
        //设置请求头中的Api绑定的的AppKey
        headerParams.put(SdkConstant.CLOUDAPI_X_CA_KEY, FRCloudAPIUtil.getCloudAPIBean().getAPPKEY(isKamao));

        //正式／测试／预发
        headerParams.put(SdkConstant.CLOUDAPI_X_CA_STAGE, FRDebugMode.isDebugMode() ? "TEST" : (FRDebugMode.isPreMode() ? "PRE" : ""));

        //设置签名版本号
        headerParams.put(SdkConstant.CLOUDAPI_X_CA_VERSION, SdkConstant.CLOUDAPI_CA_VERSION_VALUE);

        //设置请求数据类型
        headerParams.put(HttpConstant.CLOUDAPI_HTTP_HEADER_CONTENT_TYPE, HttpConstant.CLOUDAPI_CONTENT_TYPE_FORM);

        //设置应答数据类型
        headerParams.put(HttpConstant.CLOUDAPI_HTTP_HEADER_ACCEPT, HttpConstant.CLOUDAPI_CONTENT_TYPE_JSON);

        if (oldrequest.body() instanceof FormBody) {
            for (int i = 0; i < ((FormBody) oldrequest.body()).size(); i++) {
                //((FormBody) oldrequest.body()).encodedName(i)这个会对字符串进行URL编码，这里用URLDecoder解码
                formParams.put(URLDecoder.decode(((FormBody) oldrequest.body()).encodedName(i)), URLDecoder.decode(((FormBody) oldrequest.body()).encodedValue(i)));
            }
        }

        /**
         *  如果formParams不为空
         *  将Form中的内容拼接成字符串后使用UTF8编码序列化成Byte数组后加入到Request中去
         */
        RequestBody requestBody = null;
        if (null != formParams && formParams.size() > 0) {
            requestBody = RequestBody.create(MediaType.parse(headerParams.get(HttpConstant.CLOUDAPI_HTTP_HEADER_CONTENT_TYPE)), buildParamString(formParams));
        }

        /**
         *  将Request中的httpMethod、headers、path、queryParam、formParam合成一个字符串用hmacSha256算法双向加密进行签名
         *  签名内容放到Http头中，用作服务器校验
         */
        headerParams.put(SdkConstant.CLOUDAPI_X_CA_SIGNATURE, SignUtil.sign(oldrequest.method(), FRCloudAPIUtil.getCloudAPIBean().getSECRET(isKamao), headerParams, oldrequest.url().encodedPath(), null, formParams));

        /**
         *  凑齐所有HTTP头之后，将头中的数据全部放入Request对象中
         *  Http头编码方式：先将字符串进行UTF-8编码，然后使用Iso-8859-1解码生成字符串
         */
        for (String key : headerParams.keySet()) {
            String value = headerParams.get(key);
            if (null != value && value.length() > 0) {
                byte[] temp = value.getBytes(SdkConstant.CLOUDAPI_ENCODING);
                headerParams.put(key, new String(temp, SdkConstant.CLOUDAPI_HEADER_ENCODING));
            }
        }
        Headers headers = Headers.of(headerParams);
        return new Request.Builder().method(oldrequest.method(), requestBody).url(oldrequest.url().toString()).headers(headers).build();
    }

    private static String getHttpDateHeaderValue(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return FRString.valueOf(dateFormat.format(date));
    }

    private static String buildParamString(Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        if (null != params && params.size() > 0) {
            boolean isFirst = true;
            for (String key : params.keySet()) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    result.append("&");
                }

                try {
                    result.append(key).append("=").append(URLEncoder.encode(params.get(key), SdkConstant.CLOUDAPI_ENCODING.displayName()));
                } catch (UnsupportedEncodingException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }

        return FRString.valueOf(result.toString());
    }
}

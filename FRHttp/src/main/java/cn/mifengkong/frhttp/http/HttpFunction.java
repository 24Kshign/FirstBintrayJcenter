package cn.mifengkong.frhttp.http;

import android.text.TextUtils;

import com.mifengkong.frtools.util.FRObject;
import com.mifengkong.frtools.util.FRString;

import io.reactivex.functions.Function;

/**
 * 变换: 将服务器返回的原始数据剥离成我们最终想要的数据
 *
 */

public class HttpFunction<T> implements Function<BaseResponse<T>, T> {
    public static final String DATA_AND_MESSAGE_NULL = "date and message is empty";

    @Override
    public T apply(BaseResponse<T> response) throws Exception {
        if (!response.isCodeSuccess()) {
            throw new ApiException(response.getStatus(), TextUtils.isEmpty(response.getMessage())?response.getMsg():response.getMessage());
        }
        return response.getData();
    }
}
package com.mifengkong.frtools.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class PictureUtil {


    /**
     * 把bitmap转换成String
     *
     * @param filePath
     * @return
     */
    public static String bitmapToString(String filePath) {

        if (FRString.isEmpty(filePath)) {
            return FRString.EMPTY_STRING;
        }

        Bitmap bm = getSmallBitmap(filePath);

//        long size = getBitmapsize(bm);
        if (bm == null){
            FRToast.showToastSafe("获取照片失败，请重新获取");
            return "";
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.NO_WRAP);

    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @param
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {
        /*final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		options.inSampleSize = 4;
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);*/
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, newOpts);
        return compressImage(bitmap, false);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 根据路径删除图片
     *
     * @param path
     */
    public static void deleteTempFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 添加到图库
     */
    public static void galleryAddPic(Context context, String path) {
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(path);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    /**
     * 获取保存图片的目录
     *
     * @return
     */
    public static File getAlbumDir() {
        File dir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                getAlbumName());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 获取保存 隐患检查的图片文件夹名称
     *
     * @return
     */
    public static String getAlbumName() {
        return "sheguantong";
    }

    public static long getBitmapsize(Bitmap bitmap) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }
        // Pre HC-MR1
        return bitmap.getRowBytes() * bitmap.getHeight();

    }

    public static Bitmap compressImage(Bitmap image, boolean again) {
        if (image != null) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            while (baos.toByteArray().length / 1024 > (again ? 100 : 180)) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset();//重置baos即清空baos
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
                options -= 10;//每次都减少10
                if (options == 10)
                    break;
            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
            return bitmap;
        }
        return null;
    }

}

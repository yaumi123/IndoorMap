package com.tq.imageloader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static com.tq.imageloader.DefaultParams.DEFAULT_RECEIVE_IMAGE_PATH;

/**
 * 文件操作类
 * Created by Nereo on 2015/4/8.
 */
public class FileUtils {

    private static final String cacheBaseDirName = "core";
    private static final String CACHE_DIR = "images";


    /**
     * 获取Imageloader缓存的文件
     *
     * @param context
     * @param cacheDirName
     * @param cacheFileName
     * @return
     */
    public static File getCacheFile(final Context context,
                                    final String cacheDirName, final String cacheFileName) {
        File cacheFile = null;
        File cacheDir = getCacheDir(context, cacheDirName);
        if (null != cacheDir && null != cacheFileName
                && 0 != cacheFileName.trim().length()) {
            cacheFile = new File(cacheDir, cacheFileName);
        }
        return cacheFile;
    }

    /**
     * 获取缓存目录
     *
     * @param context
     * @param cacheDirName
     * @return
     */
    private static File getCacheDir(final Context context,
                                    final String cacheDirName) {
        File cacheDir = null;
        File cacheBaseDir = getCacheBaseDir(context);

        if (null != cacheBaseDir) {
            if (null != cacheDirName && 0 != cacheDirName.trim().length()) {
                cacheDir = new File(cacheBaseDir, cacheDirName);
            } else {
                cacheDir = cacheBaseDir;
            }
        }

        if (null != cacheDir && !cacheDir.exists()) {
            if (!cacheDir.mkdirs()) {
                cacheDir = null;
            }
        }

        if (null == cacheDir) {
            cacheDir = cacheBaseDir;
        }

        return cacheDir;
    }


    /**
     * 得到缓存目录
     *
     * @param context
     * @return
     */
    private static File getCacheBaseDir(final Context context) {

        File baseDir = null;
        if (Build.VERSION.SDK_INT >= 8) {
            baseDir = context.getExternalCacheDir();
        } else {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                baseDir = Environment.getExternalStorageDirectory();
        }

        if (baseDir == null)
            baseDir = context.getCacheDir();

        if (baseDir != null) {
            File cacheBaseDir = new File(baseDir, cacheBaseDirName);
            if (!cacheBaseDir.exists())
                cacheBaseDir.mkdirs();
            return cacheBaseDir;
        }
        return null;
    }


    /**
     * 获取缓存路径
     *
     * @param context
     * @return 返回缓存文件路径
     */
    public static File getCacheImageDir(Context context) {
        File imageCache;
        if (hasExternalStorage()) {
            imageCache = new File(context.getExternalCacheDir(), CACHE_DIR);
        } else {
            imageCache = new File(context.getCacheDir(), CACHE_DIR);
        }
        if (!imageCache.exists())
            imageCache.mkdirs();
        return imageCache;
    }

    private static boolean hasExternalStorage() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 保存图片到本地
     *
     * @param bitmap
     */
    public static File saveBitmap(Bitmap bitmap, String filename) {
        File file = new File(DEFAULT_RECEIVE_IMAGE_PATH, filename);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static int getOrientation(String path) {
        int orientation = 0;
        if (TextUtils.isEmpty(path))
            return orientation;

        File file = new File(path);
        if (!file.exists())
            return orientation;

        try {
            ExifInterface exifInterface = new ExifInterface(path);
            switch (exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    orientation = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    orientation = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    orientation = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orientation;

    }

    /**
     * byte[]转Bitmap
     */
    public static Bitmap Bytes2Bitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }


    /**
     * 这个很有意思的是， MediaStore.Images.Media.insertImage 会把文件复制一份，
     * 结果会在系统图库里面有两张一样的图片
     *
     * @param context
     * @param file
     */
    public static void saveImageToGallery(Context context, File file) {

        // 其次把文件插入到系统图库
       /* try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + file.getAbsolutePath())));
    }


    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File loop : fileList) {
                if (loop.isDirectory())
                    size += getFolderSize(loop);
                else size += loop.length();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除文件夹，及文件夹下面的文件
     *
     * @param file
     * @return long
     */
    public static long removeDirectory(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    removeDirectory(fileList[i]);
                } else {
                    fileList[i].delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath
     * @param filePath
     * @return
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
//        if(megaByte < 1) {
//            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
//            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "K";
//        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "M";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "G";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "T";
    }


    public static File createTmpFile(Context context) {

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 已挂载
            File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!pic.exists())
                pic.mkdirs();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
            String fileName = timeStamp + ".jpg";
            File tmpFile = new File(pic, fileName);
            return tmpFile;
        } else {
            File cacheDir = context.getCacheDir();
            if (!cacheDir.exists())
                cacheDir.mkdirs();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
            String fileName = timeStamp + ".jpg";
            File tmpFile = new File(cacheDir, fileName);
            return tmpFile;
        }

    }


    public static File copyFile(File src, String path, String name) {
        File dest = null;
        if (!src.exists()) {
            Log.e(TAG, "copyFile: src file does not exist! -" + src.getAbsolutePath());
            return null;
        } else {
            dest = new File(path);
            if (!dest.exists()) {
                Log.d(TAG, "copyFile: dir does not exist!");
                dest.mkdirs();
            }

            dest = new File(path + name);

            try {
                FileInputStream e = new FileInputStream(src);
                FileOutputStream fos = new FileOutputStream(dest);
                byte[] buffer = new byte[1024];

                int length;
                while ((length = e.read(buffer)) != -1) {
                    fos.write(buffer, 0, length);
                }

                fos.flush();
                fos.close();
                e.close();
                return dest;
            } catch (IOException var8) {
                var8.printStackTrace();
                Log.e(TAG, "copyFile: Exception!");
                return dest;
            }
        }
    }

}

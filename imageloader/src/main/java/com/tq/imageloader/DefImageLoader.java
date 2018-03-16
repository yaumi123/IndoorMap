package com.tq.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;

import static com.tq.imageloader.DefaultParams.DEFAULT_IMAGE_PATH;
import static com.tq.imageloader.DefaultParams.sDEFAULT_IMAGE;

public class DefImageLoader {


    private static DefImageLoader defaultLoader;

    public static DefImageLoader getDefault() {
        if (defaultLoader == null) {
            defaultLoader = new DefImageLoader();
        }
        return defaultLoader;
    }

    private String imageHost;
    private final String TAG = DefImageLoader.class.getSimpleName();

    public void display(String uri, ImageView imageView) {
        if (TextUtils.isEmpty(uri)) {
            Log.d(TAG, "display: " + uri);
            return;
        }
        uri = check(uri);
        ImageLoader.getInstance().displayImage(uri, imageView,
                noCacheInMemory(sDEFAULT_IMAGE));
    }

    public void display(String uri, ImageView iv, ImageLoadingListener listener) {
        if (TextUtils.isEmpty(uri)) {
            Log.d(TAG, "display: " + uri);
            return;
        }
        uri = check(uri);
        ImageLoader.getInstance().displayImage(uri, iv, listener);

    }

    public void displayLarge(String uri, ImageView iv, ImageLoadingListener listener) {
        if (TextUtils.isEmpty(uri)) {
            Log.d(TAG, "displayLarge: " + uri);
            return;
        }
        uri = check(uri);
        ImageLoader.getInstance().displayImage(uri, iv,
                getDefaultImageOptions(sDEFAULT_IMAGE), listener);
    }


    /**
     * 加载指定尺寸大小的图片，
     * 限制大小的
     *
     * @param uri
     * @param iv
     * @param width
     * @param height
     */
    public void display(String uri, ImageView iv, int width, int height) {
        if (TextUtils.isEmpty(uri)) {
            Log.d(TAG, "display: " + uri);
            return;
        }
        uri = check(uri);
        ImageLoader.getInstance().displayImage(uri, iv, new ImageSize(width, height));
    }


    public void display(String uri, ImageView imageView, int defaultRes, ImageLoadingListener listener) {
        if (TextUtils.isEmpty(uri)) {
            Log.d(TAG, "display: " + uri);
            return;
        }
        uri = check(uri);
        ImageLoader.getInstance().displayImage(uri, imageView, getDefaultImageOptions(defaultRes), listener);
    }

    public void display(ImageView imageView, String url, int resId) {
        if (TextUtils.isEmpty(url)) {
            Log.d(TAG, "display: " + url);
            return;
        }
        url = check(url);
        ImageLoader.getInstance().displayImage(url, imageView, getDefaultImageOptions(resId));
    }


    public void display(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            Log.d(TAG, "display: " + url);
            return;
        }
        url = check(url);
        ImageLoader.getInstance().displayImage(url, imageView,
                getDefaultImageOptions(sDEFAULT_IMAGE));
    }

    /**
     * 异步获取bitmap
     *
     * @param url
     * @param listener
     */
    public void loadBitmap(String url, ImageLoadingListener listener) {
        if (TextUtils.isEmpty(url)) {
            Log.d(TAG, "loadBitmap: " + url);
            return;
        }
        url = check(url);
        ImageLoader.getInstance().loadImage(url,
                getDefaultImageOptions(sDEFAULT_IMAGE), listener);
    }

    /**
     * 同步获取bitmap，并指定大小喽，不准确，但是或有压缩
     *
     * @param url
     * @param maxSize
     * @return
     */
    public Bitmap loadBitmapSync(String url, int maxSize) {
        url = check(url);
        return ImageLoader
                .getInstance()
                .loadImageSync(url, new ImageSize(maxSize, maxSize), getImageOptionsNoCache());
    }

    /**
     * 获取缓存文件
     *
     * @param file
     * @return
     */
    public File displayCacheFile(String file) {
        return ImageLoader
                .getInstance()
                .getDiskCache()
                .get(file);
    }

    /**
     * 初始化Imageloader
     *
     * @param app
     */
    public void init(Context app) {

        try {
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                    app.getApplicationContext())
                    .defaultDisplayImageOptions(
                            getDefaultImageOptions(sDEFAULT_IMAGE))
                    .threadPriority(Thread.MIN_PRIORITY)
                    .memoryCache(new WeakMemoryCache())
                    .memoryCacheSize(30 * 1024 * 1024)
                    // 使用10% 的内存做缓存
                    .memoryCacheSizePercentage(10)
                    .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                    .denyCacheImageMultipleSizesInMemory()
                    .diskCache(
                            new UnlimitedDiskCache(new File(
                                    DEFAULT_IMAGE_PATH)))
                   /* .diskCacheFileNameGenerator(new Md5FileNameGenerator())*/
                    .diskCacheSize(50 * 1024 * 1024)
                    .diskCacheFileCount(100)
                    .threadPoolSize(3)
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .build();
            ImageLoader.getInstance().init(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init(Context context, String imageHost) {
        init(context);
        this.imageHost = imageHost;
    }

    /**
     * 默认加载图片配置，
     * 缓存在本地和内存中
     *
     * @param res
     * @return
     */
    private DisplayImageOptions getDefaultImageOptions(int res) {
        return new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .showImageForEmptyUri(res)
                .showImageOnFail(res)
                .showImageOnLoading(res)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();
    }

    /**
     * 不缓存到没存中，在本地创建缓存文件
     *
     * @return
     */
    private DisplayImageOptions noCacheInMemory(int res) {
        return new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(false)
                .showImageOnFail(res)
                .showImageForEmptyUri(res)
                .showImageOnLoading(res)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();
    }

    /**
     * 既不缓存到本地，也不缓存在内存中
     *
     * @param res
     * @return
     */
    protected DisplayImageOptions getDefaultImageOptionsNoCache(int res) {
        return new DisplayImageOptions.Builder()
                .cacheOnDisk(false)
                .cacheInMemory(false)
                .showImageOnLoading(res)
                .showImageForEmptyUri(res)
                .showImageOnFail(res)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();
    }

    /**
     * 既不缓存到本地，也不缓存在内存中
     * 也没有默认图
     *
     * @return
     */
    protected DisplayImageOptions getImageOptionsNoCache() {
        return new DisplayImageOptions.Builder()
                .cacheOnDisk(false)
                .cacheInMemory(false)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();
    }

    /**
     * 清除本地缓存
     */
    public void clearCache() {
        ImageLoader.getInstance().clearDiskCache();
        FileUtils.removeDirectory(ImageLoader.getInstance().getDiskCache().getDirectory());
    }

    /**
     * 获取的当前缓存大小
     *
     * @return
     */
    public long getCacheSize() {
        File cacheDir = ImageLoader.getInstance().getDiskCache().getDirectory();
        if (cacheDir == null || cacheDir.isFile()) {
            return 0;
        }
        long size = FileUtils.getFolderSize(cacheDir);
        return size;
    }

    /**
     * 返回一个正确的URL地址，如果没有设置图片主机，则返回原样
     *
     * @param url
     * @return
     */
    private String check(String url) {
        if (TextUtils.isEmpty(imageHost) || TextUtils.isEmpty(url))
            return url;

        if (url.startsWith("file://")) {
            return url;
        }

        if (url.startsWith("drawable://"))
            return url;

        if (!url.startsWith("http"))
            return imageHost + url;

        return url;
    }

}

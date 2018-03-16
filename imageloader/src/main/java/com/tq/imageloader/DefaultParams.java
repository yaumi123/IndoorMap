package com.tq.imageloader;

import android.os.Environment;

/**
 * Created by niantuo on 2017/3/1.
 * 图片缓存的文件夹路径配置
 */

public interface DefaultParams {

    /**
     * 基本图片缓存位置
     */
    String BASE_PATH = Environment
            .getExternalStorageDirectory().toString() + "/IndoorMap/";          //"/Multi-image-selector";

    String DEFAULT_IMAGE_PATH = BASE_PATH + "Images/";

    int sDEFAULT_IMAGE = R.drawable.def_image;

    String DEFAULT_RECEIVE_IMAGE_PATH = BASE_PATH + "Save";

    int DEF_IMAGE = R.drawable.def_image;
}

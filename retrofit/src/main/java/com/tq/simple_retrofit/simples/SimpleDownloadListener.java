package com.tq.simple_retrofit.simples;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;

/**
 * Created by niantuo on 2017/3/10.
 */

public abstract class SimpleDownloadListener extends FileDownloadListener {

    @Override
    protected void warn(BaseDownloadTask task) {

    }

    @Override
    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

    }


    @Override
    protected void completed(BaseDownloadTask task) {

    }

    @Override
    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

    }

    @Override
    protected void error(BaseDownloadTask task, Throwable e) {

    }
}

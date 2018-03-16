package com.tq.simple_retrofit.model;


import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadQueueSet;
import com.liulishuo.filedownloader.FileDownloader;
import com.tq.simple_retrofit.simples.SimpleDownloadListener;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by niantuo on 2017/3/10.
 */

public class FileDownloadHelper {
    private final static String TAG = FileDownloadHelper.class.getSimpleName();

    public static void downloadFile(String url, String path, SimpleDownloadListener listener) {

        int index = url.lastIndexOf("/");
        String filename = url.substring(index + 1);

        Log.d(TAG, "downloadFile: "+url);
        Log.d(TAG, "downloadFile: "+filename);

        FileDownloader.getImpl().create(url)
                .setPath(path+filename, false)
                .setListener(listener)
                .start();

    }

    public static void downloadFiles(List<String> urls,
                                     final String path,
                                     final FileDownloadListener downloadListener,
                                     Consumer<Boolean> consumer,
                                     Consumer<Throwable> throwableConsumer) {

        Log.d(TAG, "downloadFiles: urls->" + urls + "  ->" + path);

        final FileDownloadQueueSet queueSet = new FileDownloadQueueSet(downloadListener);
        queueSet.setAutoRetryTimes(1);
        Observable.fromIterable(urls)
                .flatMap(new Function<String, ObservableSource<BaseDownloadTask>>() {
                    @Override
                    public ObservableSource<BaseDownloadTask> apply(final String s) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<BaseDownloadTask>() {
                            @Override
                            public void subscribe(ObservableEmitter<BaseDownloadTask> emitter) throws Exception {

                                int index = s.lastIndexOf("/");
                                String filename = s.substring(index + 1);
                                emitter.onNext(FileDownloader.getImpl().create(s).setPath(path+filename, false));
                                emitter.onComplete();
                            }
                        });
                    }
                })
                .toList()
                .flatMap(new Function<List<BaseDownloadTask>, SingleSource<Boolean>>() {
                    @Override
                    public SingleSource<Boolean> apply(final List<BaseDownloadTask> tasks) throws Exception {
                        return new SingleSource<Boolean>() {
                            @Override
                            public void subscribe(SingleObserver<? super Boolean> observer) {
                                queueSet.downloadTogether(tasks).start();
                                observer.onSuccess(true);
                            }
                        };
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer, throwableConsumer);
    }


}

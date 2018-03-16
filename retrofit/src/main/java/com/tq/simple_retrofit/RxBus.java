package com.tq.simple_retrofit;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


/**
 * Created by niantuo on 2016/10/29.
 */

public class RxBus {

    public static final String TAG = RxBus.class.getSimpleName();

    private static RxBus rxBus;

    public static synchronized RxBus getBus() {
        if (rxBus == null) {
            rxBus = new RxBus();
        }
        return rxBus;
    }


    private ConcurrentHashMap<String, List<Subject>> subjectMap;

    private RxBus() {
        subjectMap = new ConcurrentHashMap<>();
    }

    public <T> Observable<T> register(Class<T> cls) {

        List<Subject> subjects = subjectMap.get(cls.getName());
        if (subjects == null) {
            subjects = new ArrayList<>();
            subjectMap.put(cls.getName(), subjects);
        }
        Subject<T> subject = PublishSubject.create();
        subjects.add(subject);
        Log.i(TAG, "register: " + cls.getName());
        return subject;

    }

    public <T> void unregister(Class<T> cls, Observable observable) {

        List<Subject> subjects = subjectMap.get(cls.getName());
        if (subjects != null) {
            subjects.remove(observable);
            if (subjects.isEmpty()) {
                subjectMap.remove(subjects);
            }
            Log.i(TAG, "unregister: " + cls.getName());
        }

    }

    public void post(Object event) {


        Log.i(TAG, "post: " + event.getClass().getName());

        List<Subject> subjects = subjectMap.get(event.getClass().getName());
        if (subjects != null) {
            for (Subject subject : subjects) {
                subject.onNext(event);
                //  subject.onCompleted();
                Log.i(TAG, "post: " + "---------subject--------onCompleted-------");
            }
        } else {
            Log.i(TAG, "post: " + event.getClass().getName() + " 还没有注册事件");
        }
    }


}

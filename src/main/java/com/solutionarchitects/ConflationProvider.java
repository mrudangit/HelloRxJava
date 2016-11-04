package com.solutionarchitects;

import rx.*;
import rx.Subscriber;

/**
 * Created by montu on 11/3/16.
 */
class ConflationProvider<T> implements Observable.OnSubscribe<T> {


    private final Observable<T> source;

    public ConflationProvider(Observable<T> source){
        this.source= source;
    }

    @Override
    public void call(Subscriber<? super T> subscriber) {

    }
}

package com.solutionarchitects.rxjava;


import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import rx.Observable;
//import rx.Scheduler;
//import rx.Subscription;

import java.util.concurrent.TimeUnit;


class Conflation {

    private static Logger logger = LoggerFactory.getLogger(ConflationSubscriber.class.getName());

    static <T> Observable<T> create(Observable<T> source, long timeout, Scheduler scheduler) {

        logger.info("Creating Conflation TimeOut : {}", timeout);

        return Observable.create(subscriber -> {

            final long[] lastUpdateTime = {0};
            final Object lock = new Object();
            final Disposable[] scheduled = new Disposable[1];
            final long[] scheduleInterval = {timeout};

            source.observeOn(scheduler).subscribe(t -> {

                long currentUpdateTime = System.currentTimeMillis();
                boolean scheduleRequired;

                synchronized (lock) {

                    scheduleInterval[0] = currentUpdateTime - lastUpdateTime[0];
                    scheduleRequired = scheduleInterval[0] < timeout;

                    if (scheduleRequired && scheduled[0] != null) {
                        scheduled[0].dispose();
                        scheduled[0] = null;
                    }

                }

                if (scheduleRequired) {

                    scheduled[0] = scheduler.createWorker().schedule(() -> {

                        subscriber.onNext(t);

                        synchronized (lock) {
                            lastUpdateTime[0] = System.currentTimeMillis();
                            scheduled[0] = null;
                        }

                    }, (timeout-scheduleInterval[0]), TimeUnit.MILLISECONDS);

                } else {

                    subscriber.onNext(t);
                    synchronized (lock) {
                        lastUpdateTime[0] = System.currentTimeMillis();
                    }
                }

            });
        });
    }
}

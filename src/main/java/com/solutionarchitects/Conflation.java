package com.solutionarchitects;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.*;
import rx.Observable;
import rx.Subscriber;
import rx.plugins.RxJavaHooks;
import rx.subjects.Subject;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by montu on 11/3/16.
 */
public class Conflation {


    private static Logger logger = LoggerFactory.getLogger(ConflationSubscriber.class.getName());


    public static <T> Observable<T> create(Observable<T> source, long timeout, Scheduler scheduler)
    {

        logger.info("Creating Conflation TimeOut : {}", timeout);

        return Observable.create(subscriber -> {


            final long[] lastUpdateTime = {0};
            Object lock = new Object();

            final Subscription[] scheduled = new Subscription[1];

            source.observeOn(scheduler).subscribe(t -> {


                long currentUpdateTime = System.currentTimeMillis();

                boolean scheduleRequired;

                synchronized (lock){

                    scheduleRequired = currentUpdateTime- lastUpdateTime[0] < timeout;


                    if( scheduleRequired && scheduled[0] != null){
                        scheduled[0].unsubscribe();
                        scheduled[0]=null;
                    }


                }


                if(scheduleRequired){

                    scheduled[0] = scheduler.createWorker().schedule(() -> {

                        logger.info("Conflating...");
                        subscriber.onNext(t);

                        synchronized (lock) {

                            lastUpdateTime[0] = System.currentTimeMillis();
                            scheduled[0]=null;


                        }

                    }, timeout, TimeUnit.MILLISECONDS);



                } else {

                    subscriber.onNext(t);
                    synchronized (lock){
                        lastUpdateTime[0]=System.currentTimeMillis();
                    }

                }






            });




        });





    }
}
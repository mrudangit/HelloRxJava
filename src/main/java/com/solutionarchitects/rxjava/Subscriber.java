package com.solutionarchitects.rxjava;


import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import rx.Observable;
//import rx.schedulers.Schedulers;
//import rx.schedulers.Timestamped;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by montu on 8/26/16.
 */

//@Service
public class Subscriber
{

    private static Logger logger = LoggerFactory.getLogger(Subscriber.class.getName());



    @Autowired
    Publisher publiser;


    ExecutorService executorService = Executors.newCachedThreadPool();



    @PostConstruct
    protected void Init(){


        Observable<Timed<Long>> timestamp = publiser.subject.timestamp(Schedulers.from(executorService));


        Observable<Timed<Long>> scan = timestamp.scan((longTimed, longTimed2) -> {
            if (longTimed2.time(TimeUnit.MILLISECONDS) - longTimed.time(TimeUnit.MILLISECONDS) > 200) {
                return longTimed2;
            }
            return null;
        });



        scan.subscribe(longTimed -> {

            logger.info("Scan == {} ", longTimed.value());
        });


//        for( int i=0;i<200;i++){
//            int finalI = i;
//            debounce.subscribe(s -> {
//
//                long currTime = System.nanoTime();
//
//                long l = (currTime - (Long)s)/1000;
//                logger.info("Subscriber {} Value = {} Diff = {}", finalI, s,l);
//
//            });
//
//        }
//



    }

}

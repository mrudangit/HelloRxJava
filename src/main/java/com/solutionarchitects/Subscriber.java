package com.solutionarchitects;


//import io.reactivex.Observable;
//import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.schedulers.Timestamped;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by montu on 8/26/16.
 */

@Service
public class Subscriber
{

    private static Logger logger = LoggerFactory.getLogger(Subscriber.class.getName());



    @Autowired
    Publisher publiser;


    ExecutorService executorService = Executors.newFixedThreadPool(100);



    @PostConstruct
    protected void Init(){


        Observable<Long> debounce = publiser.subject.debounce(100, TimeUnit.MILLISECONDS, Schedulers.from(executorService));


        for( int i=0;i<1;i++){
            int finalI = i;
            debounce.subscribe(s -> {

                long currTime = System.nanoTime();

                long l = (currTime - s)/1000;
                logger.info("Subscriber {} Value = {} Diff = {}", finalI, s,l);

            });

        }




    }

}

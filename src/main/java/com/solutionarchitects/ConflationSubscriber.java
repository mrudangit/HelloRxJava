package com.solutionarchitects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.schedulers.Schedulers;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



// @Service
public class ConflationSubscriber {

    private static Logger logger = LoggerFactory.getLogger(ConflationSubscriber.class.getName());

    private final
    Publisher publiser;


    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Autowired
    public ConflationSubscriber(Publisher publiser) {
        this.publiser = publiser;
    }


    @PostConstruct
    protected void Init() {


        Observable<Long> longObservable = Conflation.create(publiser.subject, 500, Schedulers.from(executorService));






        for ( int i = 0;i< 5;i++ ){

            int finalI = i;
            longObservable.subscribe(aLong -> {


                long currTime = System.nanoTime();

                long l = (currTime - aLong)/1000;
                logger.info("Conflation#{} Sub Value = {} Diff = {}", finalI, aLong,l);

            });
        }


    }


}

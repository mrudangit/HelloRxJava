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

/**
 * Created by montu on 11/3/16.
 */


@Service
public class ConflationSubscriber {

    private static Logger logger = LoggerFactory.getLogger(ConflationSubscriber.class.getName());

    @Autowired
    Publisher publiser;


    ExecutorService executorService = Executors.newFixedThreadPool(1);



    @PostConstruct
    protected void Init() {


        Observable<Long> longObservable = Conflation.create(publiser.subject, 100, Schedulers.from(executorService));


        longObservable.subscribe(aLong -> {


            long currTime = System.nanoTime();

            long l = (currTime - aLong)/1000;
            logger.info("Conflation 1 Sub Value = {} Diff = {}", aLong,l);

        });




        longObservable.subscribe(aLong -> {


            long currTime = System.nanoTime();

            long l = (currTime - aLong)/1000;
            logger.info("Conflation 2 Sub Value = {} Diff = {}", aLong,l);

        });


    }


}

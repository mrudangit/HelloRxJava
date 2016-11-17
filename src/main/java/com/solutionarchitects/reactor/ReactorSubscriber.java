package com.solutionarchitects.reactor;

import io.reactivex.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by montu on 11/12/16.
 */
//@Service
public class ReactorSubscriber  {


    private static Logger logger = LoggerFactory.getLogger(ReactorSubscriber.class.getName());


    private final ReactorPublisher eventBusService;

    ExecutorService executorService = Executors.newCachedThreadPool();




    public ReactorSubscriber(ReactorPublisher eventBusService){
        this.eventBusService = eventBusService;


    }


    @PostConstruct
    protected void Init() {


        for (int i = 0; i < 100; i++) {

            int finalI = i;
            this.eventBusService.topic.publishOn(Schedulers.fromExecutor(executorService)).subscribe(aLong -> {
                long currTime = System.nanoTime();

                long l = (currTime - aLong)/1000;
                logger.info("Subscriber {} Value = {} Diff = {}", finalI, aLong,l);
            });

        }
    }


}

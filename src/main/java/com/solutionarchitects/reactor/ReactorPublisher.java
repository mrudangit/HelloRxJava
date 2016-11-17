package com.solutionarchitects.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;

/**
 * Created by montu on 11/12/16.
 */

//@Service
public class ReactorPublisher {


    private static Logger logger = LoggerFactory.getLogger(ReactorPublisher.class.getName());
    public final EmitterProcessor<Long> topic;

    public ReactorPublisher(){



        topic = EmitterProcessor.<Long>create().connect();

        int availableProcessors = Runtime.getRuntime()
                .availableProcessors();

        logger.info("Number of Processors : {} ",availableProcessors);


    }



    @PostConstruct
    protected void Init(){


        Thread t = new Thread(() -> {

            int counter=0;
            boolean flag=true;

            while(flag){


                Long nanoTime = System.nanoTime();
                logger.info("Reactor Publisher : {} ", nanoTime);
                topic.onNext(nanoTime);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // LockSupport.parkNanos(1000);

                counter++;

//
//                if( counter == 11){
//                    try {
//                        logger.info("Sleeping ");
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }


                if( counter > 1000){
                    flag=false;
                }





            }

        });


        t.start();

    }
}

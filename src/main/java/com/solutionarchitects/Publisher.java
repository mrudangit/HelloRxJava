package com.solutionarchitects;


//import io.reactivex.subjects.PublishSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.target.ThreadLocalTargetSource;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.subjects.PublishSubject;


import javax.annotation.PostConstruct;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by montu on 8/26/16.
 */



@Service
public class Publisher {


    private static Logger logger = LoggerFactory.getLogger(Publisher.class.getName());


    public PublishSubject<Long> subject= PublishSubject.create();




    @PostConstruct
    protected void Init(){



        Thread t = new Thread(() -> {

            int counter=0;
            boolean flag=true;

            while(flag){


                Long nanoTime = System.nanoTime();
                logger.info("Publisher : {} ", nanoTime);


                subject.onNext(nanoTime);

                try {
                    Thread.sleep(11);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // LockSupport.parkNanos(1000);

                counter++;


                if( counter == 11){
                    try {
                        logger.info("Sleeping ");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                if( counter > 100){
                    flag=false;
                }





            }

        });

        t.setName("Publisher");


        t.start();

    }




}

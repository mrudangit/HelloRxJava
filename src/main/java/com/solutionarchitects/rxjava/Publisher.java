package com.solutionarchitects.rxjava;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
//import rx.Observable;
//import rx.subjects.PublishSubject;


import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class Publisher {


    private static Logger logger = LoggerFactory.getLogger(Publisher.class.getName());




    ExecutorService executorService = Executors.newCachedThreadPool();


    BehaviorSubject<Long> subject = BehaviorSubject.create();


    Random random = new Random();






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
                    int sl = random.nextInt(50) + 50;

                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // LockSupport.parkNanos(1000);

                counter++;


                if( counter == 51){
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

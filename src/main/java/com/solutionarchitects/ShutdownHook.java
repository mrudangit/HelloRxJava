package com.solutionarchitects;

import org.omg.SendingContext.RunTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by montu on 8/21/16.
 */
@Service("shutdownHook")
public class ShutdownHook {


    private static Logger logger = LoggerFactory.getLogger(ShutdownHook.class.getName());

    private final Thread shutdownThread ;

    public ShutdownHook(){

        shutdownThread = new Thread(() -> {


           logger.info("Application Shutting Down");



        });

        shutdownThread.setName("shutdownThread");
        shutdownThread.setDaemon(true);


        Runtime.getRuntime().addShutdownHook(shutdownThread);

    }

}

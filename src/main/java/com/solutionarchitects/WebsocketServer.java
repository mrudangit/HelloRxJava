package com.solutionarchitects;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.DataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by montu on 8/28/16.
 */



//@Service
public class WebsocketServer {

    private static Logger logger = LoggerFactory.getLogger(WebsocketServer.class.getName());



    public WebsocketServer(){




    }




    @PostConstruct
    protected void Init(){



        Configuration config = new Configuration();

        config.setPort(9092);

        config.setAllowCustomRequests(true);
        config.setTransports(Transport.WEBSOCKET);



        SocketConfig socketConfig = new SocketConfig();

        socketConfig.setReuseAddress(true);
        socketConfig.setTcpKeepAlive(false);


        config.setSocketConfig(socketConfig);


        final SocketIOServer  server = new SocketIOServer(config);


        server.addConnectListener(socketIOClient -> {

            logger.info("Client Connected : {} ", socketIOClient.getSessionId());

            logger.info("Client Connected : {} ", socketIOClient.getHandshakeData().getUrlParams());

            SocketIONamespace testNameSpace = server.addNamespace("/TestNameSpace");





            testNameSpace.addConnectListener(socketIOClient1 -> {

                logger.info("==== Connected on Namespace : {} Params : {}  ",socketIOClient1.getSessionId(), socketIOClient1.getHandshakeData().getUrlParams());
            });




            testNameSpace.addEventListener("chatevent", Object.class, new DataListener<Object>() {


                @Override
                public void onData(SocketIOClient client, Object data, AckRequest ackRequest) {

                    logger.info("Data = {}", data);

                }


            });

        });

        server.addDisconnectListener(socketIOClient -> {
            logger.info(" Client Dis-Connected : {} ", socketIOClient.getSessionId());
        });


        server.start();


    }






}



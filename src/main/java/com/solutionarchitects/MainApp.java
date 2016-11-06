package com.solutionarchitects;

import com.google.gson.Gson;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * Created by montu on 11/4/16.
 */
public class MainApp {


    public static void main(String[] args) throws IOException, URISyntaxException, DeploymentException {





        MarketData.MarketDataMessage.Builder marketDataBuilder = MarketData.MarketDataMessage.newBuilder();

        MarketData.Level.Builder levelBuilder = MarketData.Level.newBuilder();

        levelBuilder.setAskSize(100);
        levelBuilder.setBidSize(200);
        levelBuilder.setPrice(100.12345);


        MarketData.Level level1 = levelBuilder.build();
        marketDataBuilder.addPriceLevels(level1);






        for(int i=0;i<10;i++){

            levelBuilder.setAskSize((int) (Math.random()*1000));
            levelBuilder.setBidSize((int) (Math.random()*1000));

            levelBuilder.setPrice(Math.random()*100);


            level1 = levelBuilder.build();
            marketDataBuilder.addPriceLevels(level1);

        }





        marketDataBuilder.setAsk0(100);
        marketDataBuilder.setAsk1(101);
        marketDataBuilder.setAsk2(102);
        marketDataBuilder.setAsk3(103);
        marketDataBuilder.setAsk4(104);


        marketDataBuilder.setBid0(201);
        marketDataBuilder.setBid1(202);
        marketDataBuilder.setBid2(203);
        marketDataBuilder.setBid3(204);
        marketDataBuilder.setBid4(205);


        marketDataBuilder.setAskSize0(100000);
        marketDataBuilder.setAskSize1(100000);
        marketDataBuilder.setAskSize2(100000);
        marketDataBuilder.setAskSize3(100000);
        marketDataBuilder.setAskSize4(100000);


        marketDataBuilder.setBidSize0(200000);
        marketDataBuilder.setBidSize1(200000);
        marketDataBuilder.setBidSize2(200000);
        marketDataBuilder.setBidSize3(200000);
        marketDataBuilder.setBidSize4(200000);



        marketDataBuilder.setExchange("BTEC");

        marketDataBuilder.setRecordId("10_YEAR");






        MarketData.MarketDataMessage message = marketDataBuilder.build();


        System.out.println(message.toByteArray().length);

        //System.out.println(message.toString());




        Gson gson = new Gson();


        HashMap<Object , Object> map = new HashMap<>();


        map.put(new Double(99.1234),new PriceLevel(100,200));
        map.put(new Double(99.2456),new PriceLevel(2000,3000));
        map.put(new Double(99.3891),new PriceLevel(4000,500));
        map.put(new Double(99.4123),new PriceLevel(6000,7000));
        map.put(new Double(99.5234),new PriceLevel(8000,9000));



        map.put(new Double(100.1234),new PriceLevel(100,200));
        map.put(new Double(100.2567),new PriceLevel(2000,3000));
        map.put(new Double(100.3897),new PriceLevel(4000,500));
        map.put(new Double(100.4444),new PriceLevel(6000,7000));
        map.put(new Double(100.5555),new PriceLevel(8000,9000));


        map.put("ba0",1000);
        map.put("ba1",1000);
        map.put("ba2",1000);
        map.put("ba3",1000);
        map.put("ba4",1000);

        map.put("bb0",200);
        map.put("bb1",200);
        map.put("bb2",200);
        map.put("bb3",200);
        map.put("bb4",200);




        map.put("as0",1000);
        map.put("as1",1000);
        map.put("as2",1000);
        map.put("as3",1000);
        map.put("as4",1000);

        map.put("bs0",200);
        map.put("bs1",200);
        map.put("bs2",200);
        map.put("bs3",200);
        map.put("bs4",200);

        map.put("id","10_YEAR");
        map.put("ex","BTEC");


        String s = gson.toJson(map);



        System.out.println(String.format("Length = %d Data = %s", s.length(),s));


        Socket socket;

        socket = IO.socket("http://localhost:8888");
        Socket finalSocket = socket;
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {


            @Override
            public void call(Object... args) {

                System.out.println("Connected");
                finalSocket.emit("marketdata", message.toByteArray());


                finalSocket.disconnect();
            }

        }).on("event", new Emitter.Listener() {

            @Override
            public void call(Object... args) {}

        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {}

        });
        socket.connect();


//
//
//
//        Thread t = new Thread(() -> {
//
//            WebSocketEndPoint ws = null;
//            try {
//                ws = new WebSocketEndPoint(new URI("ws://localhost:8888"));
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
//
//
//            ws.sendBinaryMessage(message.toByteArray());
//
//        });
//
//        t.start();



//
//
//        Publisher p = new Publisher();
//
//
//        ConflationSubscriber c= new ConflationSubscriber(p);
//
//
//        c.Init();
//
//        p.Init();


    }
}

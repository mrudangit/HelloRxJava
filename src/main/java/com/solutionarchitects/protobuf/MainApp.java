package com.solutionarchitects.protobuf;

import com.google.gson.Gson;
import com.solutionarchitects.Level2MarketDataOuterClass;
import com.solutionarchitects.MarketData;
import com.solutionarchitects.Sample;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * Created by montu on 11/4/16.
 */
public class MainApp {


    public static void main(String[] args) throws IOException, URISyntaxException, DeploymentException {





        com.solutionarchitects.MarketData.MarketDataMessage.Builder marketDataBuilder = com.solutionarchitects.MarketData.MarketDataMessage.newBuilder();

        com.solutionarchitects.MarketData.Level.Builder levelBuilder = com.solutionarchitects.MarketData.Level.newBuilder();

        levelBuilder.setAskSize(100);
        levelBuilder.setBidSize(200);
        levelBuilder.setPrice(100.12345);


        com.solutionarchitects.MarketData.Level level1 = levelBuilder.build();
        marketDataBuilder.addPriceLevels(level1);






        for(int i=0;i<9;i++){

            levelBuilder.setAskSize((int) (Math.random()*1000));
            levelBuilder.setBidSize((int) (Math.random()*1000));

            levelBuilder.setPrice(Math.random()*100);


            level1 = levelBuilder.build();
            marketDataBuilder.addPriceLevels(level1);

        }





        marketDataBuilder.setAsk0(Math.random()*100);
        marketDataBuilder.setAsk1(Math.random()*100);
        marketDataBuilder.setAsk2(Math.random()*100);
        marketDataBuilder.setAsk3(Math.random()*100);
        marketDataBuilder.setAsk4(Math.random()*100);


        marketDataBuilder.setBid0(Math.random()*100);
        marketDataBuilder.setBid1(Math.random()*100);
        marketDataBuilder.setBid2(Math.random()*100);
        marketDataBuilder.setBid3(Math.random()*100);
        marketDataBuilder.setBid4(Math.random()*100);


        marketDataBuilder.setAskSize0((int) (Math.random()*1000));
        marketDataBuilder.setAskSize1((int) (Math.random()*1000));
        marketDataBuilder.setAskSize2((int) (Math.random()*1000));
        marketDataBuilder.setAskSize3((int) (Math.random()*1000));
        marketDataBuilder.setAskSize4((int) (Math.random()*1000));


        marketDataBuilder.setBidSize0((int) (Math.random()*1000));
        marketDataBuilder.setBidSize1((int) (Math.random()*1000));
        marketDataBuilder.setBidSize2((int) (Math.random()*1000));
        marketDataBuilder.setBidSize3((int) (Math.random()*1000));
        marketDataBuilder.setBidSize4((int) (Math.random()*1000));



        marketDataBuilder.setExchange("BTEC");

        marketDataBuilder.setRecordId("10_YEAR.BTEC");
        marketDataBuilder.setSymbol("10_YEAR");






        com.solutionarchitects.MarketData.MarketDataMessage message = marketDataBuilder.build();

//
//        System.out.println(message.toByteArray().length);
//
//        System.out.println(message.toString());


        Sample.AskPrices.Builder askPriceBuilder = Sample.AskPrices.newBuilder();


        askPriceBuilder.addAskPrices(100.1);

        askPriceBuilder.addAskPrices(100.2);

        Sample.AskPrices askPrices = askPriceBuilder.build();


        Level2MarketDataOuterClass.Level2MarketData.Builder level2Builder = Level2MarketDataOuterClass.Level2MarketData.newBuilder();


        level2Builder.addAskPrices(100.1);
        level2Builder.addAskPrices(100.2);
        level2Builder.addAskPrices(100.3);
        level2Builder.addAskPrices(2000);
        level2Builder.addAskPrices(100.5);

        level2Builder.addBidPrices(99.1);
        level2Builder.addBidPrices(99.2);
        level2Builder.addBidPrices(99.3);
        level2Builder.addBidPrices(99.4);
        level2Builder.addBidPrices(99.5);


        level2Builder.addAskSizes(1000);
        level2Builder.addAskSizes(1001);
        level2Builder.addAskSizes(1002);
        level2Builder.addAskSizes(1003);
        level2Builder.addAskSizes(1004);


        level2Builder.addBidSizes(2000);
        level2Builder.addBidSizes(2001);
        level2Builder.addBidSizes(2002);
        level2Builder.addBidSizes(2003);
        level2Builder.addBidSizes(2004);



        level2Builder.setExchange("BTEC");
        level2Builder.setRecordId("10_YEAR.BTEC");
        level2Builder.setSymbol("10_YEAR");


        Level2MarketDataOuterClass.Level2MarketData level2MarketData = level2Builder.build();


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




        com.solutionarchitects.MarketData.NewMarketDataMessage.Builder
                newMarketDataBuilder = com.solutionarchitects.MarketData.NewMarketDataMessage.newBuilder();

        MarketData.PriceLevel.Builder builder = MarketData.PriceLevel.newBuilder();




        for(int i = 0; i < 5;i++){
            builder.setPrice(Math.random()*100);
            builder.setSize((int)(Math.random()*1000));

            MarketData.PriceLevel priceLevel = builder.build();

            newMarketDataBuilder.addAskLevels(priceLevel);
        }



        for(int i = 0; i < 5;i++){
            builder.setPrice(Math.random()*100);
            builder.setSize((int)(Math.random()*1000));

            MarketData.PriceLevel priceLevel = builder.build();

            newMarketDataBuilder.addBidLevels(priceLevel);
        }




        newMarketDataBuilder.setExchange("BTEC");
        newMarketDataBuilder.setRecordId("10_YEAR.BTEC");
        newMarketDataBuilder.setSymbol("10_YEAR");

        MarketData.NewMarketDataMessage newMarketDataMessage = newMarketDataBuilder.build();


        String s = gson.toJson(map);



        System.out.println(String.format("Length = %d Data = %s", s.length(),s));


        Socket socket;

        IO.Options opts =  new IO.Options();


        socket = IO.socket("http://localhost:8888");
        Socket finalSocket = socket;

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {


            @Override
            public void call(Object... args) {

                System.out.println("Connected");


                byte[] bytes = newMarketDataMessage.toByteArray();



                System.out.println(String.format("Data Length = %d ", bytes.length));

                finalSocket.emit("marketdata", bytes);



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

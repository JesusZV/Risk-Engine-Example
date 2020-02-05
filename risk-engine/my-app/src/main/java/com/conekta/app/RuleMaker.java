package com.conekta.app;

import com.conekta.app.Models.Token;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class RuleMaker {


    public DataStream<String> getStreamKeyCount(DataStream<Token> stream,
                                                String tokenProp,
                                                Time time,
                                                Integer maxPetitions,
                                                String ruleType){

        return
               stream
                .flatMap(new FlatMapFunction<Token, Tuple3<String, Integer, String>>() {
                    @Override
                    public void flatMap(Token token, Collector<Tuple3<String, Integer, String>> collector) throws Exception {

                         String tokenSelection = "";
                        switch (tokenProp)
                        {
                            case "ip":
                                tokenSelection = token.getIpAddress();
                                break;
                            case "device":
                                tokenSelection = token.getDeviceFingerprint();
                                break;
                            case "cardHash":
                                tokenSelection = token.getCardHash();
                                break;
                        }
                        collector.collect(new Tuple3<>(tokenSelection, 1, token.get_tokenId()));
                    }
                })
                .keyBy(0)
                .timeWindow(time)
                .process(new MyProcessWindowFunction(maxPetitions, ruleType));
    }

    //Class to process the elements from the window
    private class MyProcessWindowFunction extends ProcessWindowFunction<
            Tuple3<String, Integer, String>,
            String,
            Tuple,
            TimeWindow
            > {

        private Integer _maxPetitions;
        private String  _ruleType;


        public MyProcessWindowFunction(Integer maxPetitions, String ruleType) {
            this._maxPetitions = maxPetitions;
            this._ruleType = ruleType;
        }

        @Override
        public void process(Tuple tuple, Context context, Iterable<Tuple3<String, Integer, String>> iterable, Collector<String> out) throws Exception {

            Integer counter = 0;
            for (Tuple3<String, Integer, String> element : iterable) {
                counter += element.f1++;
                if(counter > _maxPetitions){
                    out.collect("El elemeto ha sido declinado: " + element.f2 + " Num elements: " + counter + " rule type: " +  _ruleType + " token: " + element.f0 );
                    counter = 0;
                }
            }
        }
    }
}

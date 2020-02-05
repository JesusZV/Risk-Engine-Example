package com.conekta.app;

import com.conekta.app.Implementations.MapTokens;
import com.conekta.app.Models.Token;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SocketTextStreamFunction;
import org.apache.flink.streaming.api.windowing.time.Time;

public class RulesExecutor {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //This DataStream Would be  Converting the Json to a Token Object
        DataStream<Token> baseStream =
                env.addSource(new SocketTextStreamFunction("localhost",
                        9999,
                        "\n",
                        1))
                        .map(new MapTokens()); //Parse the Json received to a Model


        // 1- First rule Decline if number of tokens > 5 for this IP in last 10 seconds
       DataStreamSink<String> response1 =  new RuleMaker().getStreamKeyCount(baseStream, "ip", Time.seconds(10),
               5, "seconds").print();

        //2 -Decline if number of tokens > 15 for this IP in last minute
        DataStreamSink<String> response2 = new RuleMaker().getStreamKeyCount(baseStream, "ip", Time.minutes(1),
                62, "minutes").print();

        //3- Decline if number of tokens > 60 for this IP in last hour
        DataStreamSink<String> response3  = new RuleMaker().getStreamKeyCount(baseStream, "ip", Time.hours(1),
                60, "Hours").print();

        env.execute("Job2");
    }

}

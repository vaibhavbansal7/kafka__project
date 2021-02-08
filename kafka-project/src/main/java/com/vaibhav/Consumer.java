package com.vaibhav;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;


import java.io.FileWriter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Consumer {
    static final String TOPIC="User";
    static final String GROUP="test-group";
    public static void main(String[] args) {
        ConsumerListener c = new ConsumerListener();
        Thread thread = new Thread(c);
        thread.start();
    }
    public static void consumer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer","com.vaibhav.User_Deserializer");
        properties.put("group.id", "test-group");

        KafkaConsumer<String,user_data> k_consumer = new KafkaConsumer(properties);
        List topics = new ArrayList();
        topics.add("User");
        k_consumer.subscribe(topics);

        try{


            while (true){
                FileWriter fileWriter = new FileWriter("Consumed_Data.txt",true);
                ConsumerRecords<String, user_data> records = k_consumer.poll(Duration.ofSeconds(1000));
                ObjectMapper mapper = new ObjectMapper();
                for (ConsumerRecord<String, user_data> record: records){

                    fileWriter.append(mapper.writeValueAsString(record.value())+"\n");

                }
                fileWriter.close();
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            k_consumer.close();
        }
    }
}

class ConsumerListener implements Runnable {


    @Override
    public void run() {
        Consumer.consumer();
    }
}

package com.vaibhav;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;


import java.util.Properties;


public class Producer {


    public static void main(String[] args) {
        // For example 192.168.1.1:9092,192.168.1.2:9092

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","com.vaibhav.User_Serializer");

        KafkaProducer<String,user_data> producer=new KafkaProducer<String, user_data>(properties);

        user_data user=new user_data(1,"Vaibhav Bansal",242,"Btech");
        System.out.println("Hi this is producer sending msg to consumer");
        ProducerRecord<String,user_data> record=new ProducerRecord<String,user_data>("User",user.getName(),user);
        producer.send(record);
        producer.close();

    }
}

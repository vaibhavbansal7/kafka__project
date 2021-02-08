package com.vaibhav;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class User_Serializer<T> implements Serializer<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    public User_Serializer(){}

    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    public byte[] serialize(String topic, T data) {
        if (data == null){
            return null;
        }

        try{
            return objectMapper.writeValueAsBytes(data);
        }catch(Exception e){
            throw new SerializationException("Error serializing JSON message",e);
        }
    }

//    public byte[] serialize(String topic, Headers headers, T data) {
//        return new byte[0];
//    }

    public void close() {

    }
}


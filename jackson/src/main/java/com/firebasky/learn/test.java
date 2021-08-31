package com.firebasky.learn;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        // 序列化
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping();
        Man man = new Man(12, "哈哈");
        String jsonString = objectMapper.writeValueAsString(man);//get方法
        // 输出
        System.out.println(jsonString);
        //String poc = "{\"age\":12,\"name\":\"哈哈\"}";
        //Object o = objectMapper.readValue(poc,Man.class);//反序列化
        //System.out.println(o);
        //String s = objectMapper.writeValueAsString(o);//序列化
        //System.out.println(s);

        //String jsonResult = "[\"com.firebasky.learn.test_poc\",\"test\"]";
        //objectMapper.readValue(jsonResult,Object.class);
    }
}

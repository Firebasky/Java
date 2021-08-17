package com.venscor.demo.Bean;

/**
 * @ClassName HelloMessage
 * @Description TODO
 * @Author wangyu89
 * @Create Time 2018/12/13 22:40
 * @Version 1.0
 */
public class HelloMessage {
    private String name;

    public HelloMessage() {
    }

    public HelloMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

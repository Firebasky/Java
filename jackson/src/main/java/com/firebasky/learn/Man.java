package com.firebasky.learn;

public class Man {
    public int age;
    public String name;

    public Man(){
        System.out.println("无参数构造方法");
    }

    public Man(int age, String name) {
        System.out.println("有参数构造方法");
        this.age = age;
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        System.out.println("get方法");
        return name;
    }

    public void setName(String name) {
        System.out.println("set方法");
        this.name = name;
    }
}

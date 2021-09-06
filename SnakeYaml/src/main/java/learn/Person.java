package learn;

import java.sql.PreparedStatement;
import java.time.Period;

public class Person {
    public String name;

    public Person(){
    }
    public Person(String name){
        System.out.println(name);
    }

    public String getName() {
        System.out.println("get方法");
        return name;
    }

    public void setName(String name) {
        System.out.println("set方法");
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int age;
}

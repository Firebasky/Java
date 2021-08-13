package com.firebasky.exp;

/**
 * @author:Firebasky
 * 通过多线程方式
 * ref:http://www.lmxspace.com/2020/08/24/%E4%B8%80%E7%A7%8D%E5%8F%A6%E7%B1%BB%E7%9A%84shiro%E6%A3%80%E6%B5%8B%E6%96%B9%E5%BC%8F/
 */
public class Main {
    public static void main(String[] args) throws Exception{
        try {
            System.out.println("正在暴力破解................");
            ScanShiro thread = new ScanShiro();
            thread.start();
            Thread.sleep(20);
            thread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

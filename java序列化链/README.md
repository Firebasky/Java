# 关于一些自己分析的java序列化链的问题

+ [浅谈Java反序列化漏洞修复方案](https://github.com/Cryin/Paper/blob/master/%E6%B5%85%E8%B0%88Java%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E6%BC%8F%E6%B4%9E%E4%BF%AE%E5%A4%8D%E6%96%B9%E6%A1%88.md)
+ [一部分xmldecoder](xmldecoder)
+ [添加了C3P0利用链](C3P0)
+ [添加了Click利用链](Click)  **其中和cb链差不多，巧妙地方在通过getlowestSetBit排序后set进去值，可以避免排序和比较**
+ [添加了Clojure利用链](Clojure) **简单的说就是触发了BadAttributeValueExpException#toString方法然后触发hashcode方法到sink点main$eval_opt#invokeStatic**
+ [添加了ROME利用链](ROME) **简单的说就是调用toString方法然后去触发java内省的getReadMethod去调用任意的get方法去触发TemplatesImpl#getOutputProperties**
+ [添加了Vaadin利用链](Vaadin) **可以说和Rome利用链差不多。。。。**
+ [添加了Groovy利用链](Groovy) **简单的说利用AnnotationInvocationHandler的代理去触发ConvertedClosure.invokeCustom,yso中使用PriorityQueue去触发Comparator.compare()一样的会触发ConvertedClosure.invokeCustom**
+ [添加了Hibernate利用链](Hibernate) **Hibernate类似jdbc的东西，简单的说还是触发getter方法去利用TemplatesImpl#getOutputProperties**
+ [添加了spring利用链](spring) **个人感觉spring链中最重要的是代理，非常巧妙的利用了代理功能**
+ [添加了FileUpload利用链](FileUpload) **比较简单而且真实危害可能比较小，截断还需要jdk的支持。不过copy可以去出题**
+ [并没有添加Wicket链]()  **因为和FileUpload一样。。。。。**
+ [添加了CommonsBeanutils链](CommonsBeanutils) **来自p师傅的文章，不需要依赖cc组件**[CommonsBeanutils与无commons-collections的Shiro反序列化利用](https://www.leavesongs.com/PENETRATION/commons-beanutils-without-commons-collections.html#shiro)
+ [添加了AspectJWeaver利用链](AspectJWeaver) **主要是写文件触发put方法，自己尝试不需要cc组件去挖掘没有成功。。。。**
+ [添加了Mojarra利用链](Mojarra)  **没有详细的分析大概就是最后调用表达式**  **[jsf](https://www.oracle.com/cn/java/technologies/jsf.html)** The gadget chain is valid for the implementation of the JSF specification in version 2.3 and 3.0. 
+

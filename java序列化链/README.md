# 关于一些自己分析的java序列化链的问题

+ [浅谈Java反序列化漏洞修复方案](https://github.com/Cryin/Paper/blob/master/%E6%B5%85%E8%B0%88Java%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E6%BC%8F%E6%B4%9E%E4%BF%AE%E5%A4%8D%E6%96%B9%E6%A1%88.md)
+ [一部分xmldecoder](xmldecoder)
+ [添加了C3P0利用链](C3P0)
+ [添加了Click利用链](Click) **其中和cb链差不多，巧妙地方在通过getlowestSetBit排序后set进去值，可以避免排序和比较**
+ [添加了Clojure利用链](Clojure) **简单的说就是触发了BadAttributeValueExpException#toString方法然后触发hashcode方法到sink点main$eval_opt#invokeStatic **

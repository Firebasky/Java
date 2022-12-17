# spel防御

最直接的防御方法就是使用`SimpleEvaluationContext`替换`StandardEvaluationContext`。

官方文档：[SimpleEvaluationContext的API官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdocs.spring.io%2Fspring%2Fdocs%2F5.0.6.RELEASE%2Fjavadoc-api%2Forg%2Fspringframework%2Fexpression%2Fspel%2Fsupport%2FSimpleEvaluationContext.html)

![image-20220325230922109](img/image-20220325230922109.png)

SimpleEvaluationContext和StandardEvaluationContext是SpEL提供的两个EvaluationContext：

- SimpleEvaluationContext - 针对不需要SpEL语言语法的全部范围并且应该受到有意限制的表达式类别，公开SpEL语言特性和配置选项的子集。
- StandardEvaluationContext - 公开全套SpEL语言功能和配置选项。您可以使用它来指定默认的根对象并配置每个可用的评估相关策略。

SimpleEvaluationContext旨在仅支持SpEL语言语法的一个子集，不包括 Java类型引用、构造函数和bean引用；而StandardEvaluationContext是支持全部SpEL语法的。

http://rui0.cn/archives/1043
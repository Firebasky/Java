# c语言能不能实现agent?

>面试的时候被问到了，当时不会现在有时间去学习一下，如果真的可以实现那说不定有另一个利用思路？

在参考文章中已经非常清楚的介绍了，下面我来演示一下。

## 运行前

agent.cpp

```c
#include "pch.h"
#include "jvmti.h"
#include <iostream>

/*
 * java agent有2个启动函数分别为Agent_OnLoad和Agent_OnAttach
 * Agent_OnLoad在onload阶段被调用
 * Agent_OnAttach在live阶段被调用
 * 但是每个agent只有一个启动函数会被调用
 */

 /*
  * 此阶段JVM还没有初始化，所以能做的操作比较受限制
  * JVM参数都无法获取
  * The return value from Agent_OnLoad is used to indicate an error.
  * Any value other than zero indicates an error and causes termination of the VM.
  * 任何非零的返回值都会导致JVM终止。
  */

JNIEXPORT jint JNICALL Agent_OnLoad(JavaVM* vm, char* options, void* reserved) {
    printf("Agent_OnLoad\n");
    return JNI_OK;
}


JNIEXPORT jint JNICALL Agent_OnAttach(JavaVM* vm, char* options, void* reserved) {
    printf("Agent_OnAttach\n");
    return JNI_OK;
}

/*
* This function can be used to clean-up resources allocated by the agent.
*/
JNIEXPORT void JNICALL Agent_OnUnload(JavaVM* vm) {
    printf("Agent_OnUnload\n");
}
```

如果我们在Agent_OnLoad和Agent_OnAttach函数内添加恶意的代码呢？就成功执行命令了吧！

```c
JNIEXPORT jint JNICALL Agent_OnLoad(JavaVM* vm, char* options, void* reserved) {
    printf("Agent_OnLoad\n");
    system("calc");
    return JNI_OK;
}
```


![image-20220108231620529](https://user-images.githubusercontent.com/63966847/148650284-e3237b5b-010a-41c3-a361-5055c6c4d533.png)



## 运行中

调用了Agent_OnLoad函数和Agent_OnUnload函数，因为Agent_OnLoad和Agent_OnAttach函数在一个agent中只有一个会被调用，如果你希望在JVM启动时做些事情的话，就使用onload函数，如果希望有外部链接JVM时做一些工作的话就使用attach函数，unload函数在JVM关闭时调用。对于attach函数我们也可以在JVM运行时动态加载本地库并且调用。


![image-20220108232406053](https://user-images.githubusercontent.com/63966847/148650288-8b0a1930-c8f9-4c90-9406-d108a1189b80.png)


## 总结一下

其实通过c/c++实现的agent更加底层,并且实现的功能和java实现的agent一样(premain/agentmain)[java-agent](https://github.com/Firebasky/Java/blob/main/java%E5%86%85%E5%AD%98%E9%A9%AC/agent/java-agent%E5%AD%A6%E4%B9%A0.pdf)

```
Agent_OnLoad在onload阶段被调用
Agent_OnAttach在live阶段被调用
Agent_OnUnload在关闭jvm调用
```

不过，没有详细的看官方文档，不过我感觉哈通过c/c++方式实现的agent可扩展性高攻击面广(毕竟可以写c/c++去攻击了)

不过也要考虑环境，可能javaagent更加适合利用。（个人胡说八道。。。



参考:

>https://www.shuzhiduo.com/A/kvJ36qM9zg/
>
>https://luckymrwang.github.io/2020/12/28/%E7%A0%B4%E8%A7%A3-Java-Agent-%E6%8E%A2%E9%92%88%E9%BB%91%E7%A7%91%E6%8A%80/
>
>https://gist.github.com/hkalina/5897298

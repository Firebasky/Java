#include <iostream>
#include <stdlib.h>
#include <cstring>
#include <string>
#include "org_javaweb_jni_CommandExecution.h"

using namespace std;

JNIEXPORT jstring

JNICALL Java_org_javaweb_jni_CommandExecution_exec
        (JNIEnv *env, jclass jclass, jstring str) {
    if (str != NULL) {
        jboolean jsCopy;
        const char *cmd = env->GetStringUTFChars(str, &jsCopy);// 将jstring参数转成char指针
        FILE *fd  = popen(cmd, "r");// 使用popen函数执行系统命令
        if (fd != NULL) {
            string result; // 返回结果字符串
            char buf[128];// 定义字符串数组
            while (fgets(buf, sizeof(buf), fd) != NULL) { // 读取popen函数的执行结果
                result +=buf; // 拼接读取到的结果到result
            }
            pclose(fd);// 关闭popen
            return env->NewStringUTF(result.c_str());// 返回命令执行结果给Java
        }
    }
    return NULL;
}
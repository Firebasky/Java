# seacherobj

>学习一下searcherobj的方法
>
> https://blog.csdn.net/dhklsl/article/details/83992950 
>
>https://blog.csdn.net/dhklsl/article/details/84751008
>
>https://blog.csdn.net/dhklsl/article/details/88245460

## 递归

一个一个的去寻找

```java
/**
 * 判断是否是List或者ArrayList
 * @param field
 * @return
 */
public static boolean isList(Field field){
    boolean flag = false;
    String simpleName = field.getType().getSimpleName();
    if ("List".equals(simpleName) || "ArrayList".equals(simpleName)){
        flag = true;
    }
    return flag;
}
/**
 * 判断是否是Map或者HashMap
 * @param field
 * @return
 */
public static boolean isMap(Field field){
    boolean flag = false;
    String simpleName = field.getType().getSimpleName();
    if ("Map".equals(simpleName) || "HashMap".equals(simpleName)){
        flag = true;
    }
    return flag;
}
/**
 * 检查object是否为java的基本数据类型
 * @param object
 * @return
 */
public static boolean checkObjectIsSysType(Object object) {
    String objType = object.getClass().toString();
    if ("byte".equals(objType) || "short".equals(objType) || "int".equals(objType) || "long".equals(objType) || "double".equals(objType) || "float".equals(objType) || "boolean".equals(objType)) {
        return true;
    } else {
        return false;
    }
}
```

## json截取

思想非常简单就是将对象转换成json数据，然后在去截断我们需要的属性

```java
/**
     * 方法二：从复杂对象中获取string类型的目标属性targetProName的值
     * 把对象转换成json字符串，然后截取第一次出现的targetProName的值
     * 适用条件：同方法一
     * @param object 复杂对象
     * @param targetProName 目标属性
     * @return
     */
public static String getBusinessNoFromArg(Object object,String targetProName){
    String jsonString = JSON.toJSONString(object);
    System.err.println("jsonString=" + jsonString);
    jsonString = StringUtils.substringAfter(jsonString,"\""+targetProName + "\":\"");//去截断目标属性
    jsonString = StringUtils.substringBefore(jsonString,"\"");
    return jsonString;
}
```






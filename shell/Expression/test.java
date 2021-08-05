package shell.Expression;

import java.beans.Expression;

public class test {
    public static void main(String[] args) {
        String payload ="calc";
        Expression expression = new Expression(Runtime.getRuntime(),"\u0065"+"\u0078"+"\u0065"+"\u0063",new Object[]{payload});
        try {
            expression.getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

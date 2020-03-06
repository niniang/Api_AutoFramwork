package com.qa.tests;


import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RequestWithCookie {
    public static void main(String[] agrs) throws IOException, ClassNotFoundException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpClientContext context = HttpClientContext.create();

        HttpPost post = new HttpPost("http://192.168.2.60:10090/api/auth/session/login");
        HashMap<String,String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");
        for(Map.Entry<String,String> entity : headerMap.entrySet()){
            post.addHeader(entity.getKey(),entity.getValue());
        }
        String content = "{\"username\":\"940617\",\"password\":\"000000\",\"vcode\":\"\"}";
        post.setEntity(new StringEntity(content));
        //CloseableHttpResponse response =
                httpClient.execute(post,context);

        //从请求结果中获取Cookie，此时的Cookie已经带有登录信息了
        CookieStore cookieStore = context.getCookieStore();
        //这个CookieStore保存了我们的登录信息，我们可以先将它保存到某个本地文件，后面直接读取使用
        saveCookieStore(cookieStore,System.getProperty("user.dir") + "/src/main/java/com/qa/config/cookie.properties");

        //下面我们将演示如何使用Cookie来请求，首先我们将之前的Cookie读出来
        CookieStore cookieStore1 = readCookieStore(System.getProperty("user.dir") + "/src/main/java/com/qa/config/cookie.properties");
        //构造一个带这个Cookie的HttpClient
        CloseableHttpClient httpClient1 = HttpClientBuilder.create().setDefaultCookieStore(cookieStore1).build();
        //使用这个新的HttpClient请求就可以了。这时候我们的HttpClient已经带有了之前的登录信息，再爬取就不用登录了
        HttpGet get = new HttpGet("http://192.168.2.60:10090/api/auth/company_tree");
        //get.addHeader(new BasicHeader("Cookie",cookie));
        CloseableHttpResponse response02 = httpClient1.execute(get);
        int responseStatusCode = response02.getStatusLine().getStatusCode();
        System.out.println(responseStatusCode);

    }

    //使用序列化的方式保存CookieStore到本地文件，方便后续的读取使用
    private static void saveCookieStore(CookieStore cookieStore, String savePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(savePath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(cookieStore);
        oos.close();
    }

    //读取Cookie的序列化文件，读取后可以直接使用
    private static CookieStore readCookieStore(String savePath) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(savePath);
        ObjectInputStream ois = new ObjectInputStream(fis);
        CookieStore cookieStore = (CookieStore)ois.readObject();
        ois.close();
        return cookieStore;
    }
}

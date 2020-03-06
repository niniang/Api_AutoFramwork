package com.qa.tests;

import com.qa.util.FileUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CookieTest {

    @Test
    public void getCookie() throws UnsupportedEncodingException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpClient httpClient02 = HttpClients.createDefault();
        //HttpGet get=new HttpGet("http://www.baidu.com");
        HttpPost post = new HttpPost("http://192.168.2.60:10090/api/auth/session/login");
        HashMap<String,String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");
        for(Map.Entry<String,String> entity : headerMap.entrySet()){
            post.addHeader(entity.getKey(),entity.getValue());
        }
        String content = "{\"username\":\"940617\",\"password\":\"000000\",\"vcode\":\"\"}";
        post.setEntity(new StringEntity(content));
        HttpClientContext context = HttpClientContext.create();

        try {
            //CloseableHttpResponse response = httpClient.execute(get, context);
            CloseableHttpResponse response = httpClient.execute(post,context);
            String responseString = EntityUtils.toString(response.getEntity(),"Utf-8");
            System.out.println(responseString);
            try{
                System.out.println(">>>>>>headers:");
                Arrays.stream(response.getAllHeaders()).forEach(System.out::println);
                System.out.println(">>>>>>cookies:");
                //context.getCookieStore().getCookies().forEach(System.out::println);
                String cookie = context.getCookieStore().getCookies().toString();
                System.out.println(cookie);
                FileUtil.setValue("cookie",cookie);

            }
            finally {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    }
}

package com.qa.tests;

import com.qa.base.TestBase;
import com.qa.restclient.RestClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class LoginTest extends TestBase {
    TestBase testBase;
    String host;
    String api;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    HttpClientContext context = HttpClientContext.create();
    final static Logger Log = Logger.getLogger(LoginTest.class);

    @Test
    public void getCookie() throws UnsupportedEncodingException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //HttpGet get=new HttpGet("http://www.baidu.com");
        HttpPost post = new HttpPost("http://192.168.2.60:10090/api/auth/session/login");
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");
        for (Map.Entry<String, String> entity : headerMap.entrySet()) {
            post.addHeader(entity.getKey(), entity.getValue());
        }
        String content = "{\"username\":\"940617\",\"password\":\"000000\",\"vcode\":\"\"}";
        post.setEntity(new StringEntity(content));
        //context = HttpClientContext.create();

        try {
            //CloseableHttpResponse response = httpClient.execute(get, context);
            CloseableHttpResponse response = httpClient.execute(post, context);
            try {
                System.out.println(">>>>>>headers:");
                Arrays.stream(response.getAllHeaders()).forEach(System.out::println);
                System.out.println(">>>>>>cookies:");
                //context.getCookieStore().getCookies().forEach(System.out::println);
                String cookie = context.getCookieStore().getCookies().toString();
                System.out.println(cookie);

//                HttpGet get = new HttpGet("http://192.168.2.60:10090/api/auth/company_tree");
//                CloseableHttpResponse response02 = httpClient.execute(get,context);
//                int responseStatusCode = response02.getStatusLine().getStatusCode();
//                System.out.println(responseStatusCode);

            } finally {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

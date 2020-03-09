package com.qa.restclient;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.qa.util.CookieUtil.saveCookieStore;

public class RestClient {
    final static Logger Log = Logger.getLogger(RestClient.class);

    /**
     * 不带请求的get方法
     * @param url
     * @return 返回请求对象
     * @throws IOException
     */
    public  CloseableHttpResponse get(String url) throws IOException {
        //创建一个可关闭的HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个HttpGet对象
        HttpGet httpGet = new HttpGet(url);
        //执行请求,相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
        Log.info("开始发送get请求...");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        Log.info("发送请求成功！开始得到响应对象。");
        return httpResponse;
    }

    /**
     * 带请求头信息的get方法
     * @param url
     * @param headerMap，键值对形式
     * @return 返回响应对象
     * @throws IOException
     */
    public CloseableHttpResponse get(String url,HashMap <String,String> headerMap) throws IOException {
        //创建一个可关闭的HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个HttpGet对象
        HttpGet httpGet = new HttpGet(url);
        //加载请求头到httpGet对象
        for(Map.Entry<String,String> entity : headerMap.entrySet()){
            httpGet.addHeader(entity.getKey(),entity.getValue());
        }
        //执行请求,相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
        Log.info("开始发送get请求...");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        Log.info("发送请求成功！开始得到响应对象。");
        return httpResponse;
    }

    /**
     * 带请求头信息和cookie的get方法
     * @param url
     * @param headerMap，键值对形式
     * @return 返回响应对象
     * @throws IOException
     */
    public CloseableHttpResponse get(String url,HashMap <String,String> headerMap,CookieStore cookieStore) throws IOException {
        //创建一个可关闭且带cookie的HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
        //创建一个HttpGet对象
        HttpGet httpGet = new HttpGet(url);
        //加载请求头到httpGet对象
        for(Map.Entry<String,String> entity : headerMap.entrySet()){
            httpGet.addHeader(entity.getKey(),entity.getValue());
        }
        //执行请求,相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
        Log.info("开始发送get请求...");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        Log.info("发送请求成功！开始得到响应对象。");
        return httpResponse;
    }

    /**
     * 不带请求头信息和cookie的get方法
     * @param url
     * @return 返回响应对象
     * @throws IOException
     */
    public CloseableHttpResponse get(String url,CookieStore cookieStore) throws IOException {
        //创建一个可关闭且带cookie的HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
        //创建一个HttpGet对象
        HttpGet httpGet = new HttpGet(url);

        //执行请求,相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
        Log.info("开始发送get请求...");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        Log.info("发送请求成功！开始得到响应对象。");
        return httpResponse;
    }

    /**
     * 封装post方法
     * @param url
     * @param entityString，其实就是设置请求json参数
     * @param headerMap，带请求头
     * @return 返回响应对象
     * @throws IOException
     */
    public CloseableHttpResponse post(String url,String entityString,HashMap<String,String> headerMap) throws IOException {
        //创建一个可关闭的HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个HttpPost对象
        HttpPost httpPost = new HttpPost(url);
        //设置payload
        httpPost.setEntity(new StringEntity(entityString));
        //加载请求头到httpPost对象
        for(Map.Entry<String,String> entity : headerMap.entrySet()){
            httpPost.addHeader(entity.getKey(),entity.getValue());
        }
        //执行请求,相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
        Log.info("开始发送post请求...");
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        Log.info("发送请求成功！开始得到响应对象。");
        return httpResponse;
    }

    /**
     * 封装 put请求方法，参数和post方法一样
     * @param url
     * @param entityString，这个主要是设置payload,一般来说就是json串
     * @param headerMap，带请求的头信息，格式是键值对，所以这里使用hashmap
     * @return 返回响应对象
     * @throws IOException
     */
    public CloseableHttpResponse put(String url,String entityString,HashMap<String,String> headerMap) throws IOException {
        //创建一个可关闭的HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个HttpPut对象
        HttpPut httpPut = new HttpPut(url);
        //设置payload
        httpPut.setEntity(new StringEntity(entityString));
        //加载请求头到httpPut对象
        for (Map.Entry<String,String> entry : headerMap.entrySet()){
            httpPut.addHeader(entry.getKey(),entry.getValue());
        }
        //执行请求,相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
        Log.info("开始发送put请求...");
        CloseableHttpResponse httpResponse = httpClient.execute(httpPut);
        Log.info("发送请求成功！开始得到响应对象。");
        return httpResponse;
    }

    /**
     * 封装 delete请求方法，参数和get方法一样
     * @param url， 接口url完整地址
     * @return，返回一个response对象，方便进行得到状态码和json解析动作
     * @throws IOException
     */
    public CloseableHttpResponse delete(String url) throws IOException {
        //创建一个可关闭的HttpClient对象
        CloseableHttpClient httpClient =HttpClients.createDefault();
        //创建一个HttpDelete对象
        HttpDelete httpDelete = new HttpDelete(url);

        //执行请求,相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
        Log.info("开始发送delete请求...");
        CloseableHttpResponse httpResponse = httpClient.execute(httpDelete);
        Log.info("发送请求成功！开始得到响应对象。");
        return httpResponse;
    }
    /**
     * 登录方法，储存cookie到cookie.properties
     * @param url 接口完整地址
     * @param entityString json请求体
     * @param headerMap，带请求的头信息，格式是键值对，所以这里使用hashmap
     * @throws IOException
     * @return 返回响应对象
     */
    public CloseableHttpResponse login(String url,String entityString,HashMap<String,String> headerMap) throws IOException {
        //创建一个可关闭的HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个Context对象用于获取cookie
        HttpClientContext context = HttpClientContext.create();
        HttpPost httpPost = new HttpPost(url);
        //设置payload
        httpPost.setEntity(new StringEntity(entityString));
        //加载请求头到httpPost对象
        for(Map.Entry<String,String> entity : headerMap.entrySet()){
            httpPost.addHeader(entity.getKey(),entity.getValue());
        }
        //执行请求
        Log.info("开始发送post请求...");
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost,context);
        Log.info("发送请求成功！开始得到响应对象。");
        Log.info("从请求结果中获取Cookie");
        CookieStore cookieStore = context.getCookieStore();
        Log.info("将它保存到cookie.properties");
        saveCookieStore(cookieStore,System.getProperty("user.dir") + "/src/main/java/com/qa/config/cookie.properties");
        Log.info(">>>>>>headers:");
        Arrays.stream(httpResponse.getAllHeaders()).forEach(System.out::println);
        Log.info(">>>>>>cookies:");
        context.getCookieStore().getCookies().forEach(System.out::println);
        return httpResponse;
    }
}

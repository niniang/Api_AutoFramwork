package com.qa.tests;

import com.qa.base.TestBase;
import com.qa.restclient.RestClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class LoginTest02 extends TestBase {
    String host;
    String api;
    String url;
    String content;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    final static Logger Log = Logger.getLogger(LoginTest02.class);

    @BeforeClass
    public void setUp(){
        host = "http://192.168.2.60:10090";
        api = "/api/auth/session/login";
        url = host + api;
        content = "{\"username\":\"940617\",\"password\":\"000000\",\"vcode\":\"\"}";
    }

    @Test
    public void loginTest02() throws IOException {
        Log.info("----------开始执行用例loginTest02...----------");
        restClient = new RestClient();

        Log.info("url:" + url);
        Log.info("content:" + content);

        Log.info("导入请求头");
        HashMap<String,String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");

        Log.info("请求登录接口");
        closeableHttpResponse = restClient.login(url,content,headerMap);

        Log.info("测试响应状态码是否是200");
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode,RESPNSE_STATUS_CODE_200,"status code is not 200");
        Reporter.log("状态码：" + statusCode);
    }
}

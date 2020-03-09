package com.qa.tests;

import com.qa.base.TestBase;
import com.qa.restclient.RestClient;
import com.qa.util.CookieUtil;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetApiWithCookieTest extends TestBase {
    String host;
    String api;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    CookieStore cookieStore;
    final static Logger Log = Logger.getLogger(GetApiWithCookieTest.class);

    @BeforeClass
    public void setUp(){
        host = "http://192.168.2.60:10090";
        api = "/api/auth/company_tree";
        url = host + api;
    }

    @Test
    public void getApiWithCookieTest() throws IOException, ClassNotFoundException {
        Log.info("----------开始执行用例GetApiWithCookieTest...----------");
        restClient = new RestClient();
        Log.info("url:" + url);

        Log.info("获取cookie");
        cookieStore = CookieUtil.readCookieStore(System.getProperty("user.dir") + "/src/main/java/com/qa/config/cookie.properties");

        Log.info("请求get接口");
        closeableHttpResponse = restClient.get(url,cookieStore);

        Log.info("测试响应状态码是否是200");
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode,RESPNSE_STATUS_CODE_200,"status code is not 200");
        Log.info("状态码：" + statusCode);
    }
}

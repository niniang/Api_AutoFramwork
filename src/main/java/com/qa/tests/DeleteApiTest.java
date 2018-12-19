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

public class DeleteApiTest extends TestBase{
    TestBase testBase;
    String host;
    String api;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    final static Logger Log = Logger.getLogger(DeleteApiTest.class);

    @BeforeClass
    public void setUp(){
        testBase = new TestBase();
        host = prop.getProperty("HOST");
        api = prop.getProperty("DeleteApi");
        url = host + api;
        //url = host + "/api/users/2";
    }

    @Test
    public void deleteApiTest() throws IOException {
        Log.info("----------开始执行用例deleteApiTest...----------");
        restClient = new RestClient();

        Reporter.log("接口地址：" + url);
        closeableHttpResponse = restClient.delete(url);

        Log.info("测试响应状态码是否是204");
        int responseStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(responseStatusCode,RESPNSE_STATUS_CODE_204,"response status code is not 204");

        //String responseString = closeableHttpResponse.getEntity().toString();
        //Reporter.log("接口响应：" + responseString);

        Reporter.log("状态码：" + responseStatusCode,true);

        Log.info("----------用例执行结束...----------");


    }
}

package com.qa.tests;

import com.qa.base.TestBase;
import com.qa.restclient.RestClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class DelectApiTest extends TestBase{
    TestBase testBase;
    String host;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    final static Logger Log = Logger.getLogger(DelectApiTest.class);

    @BeforeClass
    public void setUp(){
        testBase = new TestBase();
        host = prop.getProperty("HOST");
        url = host + "/api/users/2";
    }

    @Test
    public void delectApiTest() throws IOException {
        Log.info("----------开始执行用例delectApiTest...----------");
        restClient = new RestClient();

        closeableHttpResponse = restClient.delect(url);

        Log.info("测试响应状态码是否是204");
        int responseStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(responseStatusCode,RESPNSE_STATUS_CODE_204,"response status code is not 204");
        Log.info("----------用例执行结束...----------");
    }
}

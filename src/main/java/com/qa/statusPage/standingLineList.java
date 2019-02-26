package com.qa.statusPage;

import com.qa.base.TestBase;
import com.qa.restclient.RestClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;


public class standingLineList extends TestBase {

    TestBase testBase;
    String host;
    String api;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    final static Logger log = Logger.getLogger(standingLineList.class);

    @BeforeClass
    public void setUp(){
        testBase = new TestBase();
        host = prop.getProperty("HOST");
        api = prop.getProperty("standing_line_list");
        url = host + api;

    }

    @Test
    public void standingLineList() throws IOException {
        log.info("开始执行用例standingLineList...");
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);
        Reporter.log("接口地址：" + url);

        log.info("验证返回码是否为200");
        int responseStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
        Reporter.log("返回码：" + responseStatusCode);
        Reporter.log("接口响应：" + responseString);
        log.info("用例执行结束...");
    }
}

package com.qa.tests;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.base.TestBase;
import com.qa.parameters.User;
import com.qa.restclient.RestClient;
import com.qa.util.TestUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class PutApiTest extends TestBase{
    TestBase testBase;
    String url;
    String host;
    String api;
    String jsonString;
    CloseableHttpResponse closeableHttpResponse;
    RestClient restClient;
    final static Logger Log = Logger.getLogger(PutApiTest.class);

    @BeforeClass
    public void setUp(){
        testBase = new TestBase();
        host = prop.getProperty("HOST");
        api = prop.getProperty("PutApi");
        url = host + api;
        //url = host + "/api/users/2";
        jsonString = content.getProperty("Put");
    }

    @Test
    public void putApiTest() throws IOException {
        Log.info("----------开始执行用例putApiTest...----------");
        restClient = new RestClient();

        Log.info("导入请求头");
        HashMap <String,String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");
        //User user = new User("Anthony","automation tester");
        //String jsonString = JSON.toJSONString(user);
        //System.out.println(jsonString);

        closeableHttpResponse = restClient.put(url,jsonString,headerMap);
        Reporter.log("接口地址：" + url);
        Reporter.log("JSON：" + jsonString);

        Log.info("测试响应状态码是否是200");
        int responseStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Reporter.log("状态码：" + responseStatusCode);
        Assert.assertEquals(responseStatusCode,RESPNSE_STATUS_CODE_200,"response status code is not 200");
        String responseRntity = EntityUtils.toString(closeableHttpResponse.getEntity());
        Reporter.log("接口响应：" + responseRntity);
        JSONObject responseJson = JSON.parseObject(responseRntity);
        String s = TestUtil.getValueByJPath(responseJson,"job");
        Log.info("执行JSON解析，解析的内容是 " + s);
        Log.info("接口内容响应断言");
        Assert.assertEquals(s,"automation tester","job is not same");
        Log.info("----------用例执行结束...----------");
    }
}

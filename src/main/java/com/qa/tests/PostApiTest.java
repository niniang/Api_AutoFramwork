package com.qa.tests;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.base.TestBase;
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

public class PostApiTest extends TestBase {
    TestBase testBase;
    String host;
    String api;
    String url;
    String userJsonString;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    final static Logger Log = Logger.getLogger(PostApiTest.class);

    @BeforeClass
    public void setUp(){
        testBase = new TestBase();
        host = prop.getProperty("HOST");
        api = prop.getProperty("PostApi");
        url = host + api;

        //配置文件写入请求体
        userJsonString = content.getProperty("Post");
        System.out.println(userJsonString);
        //url = host + "/api/users";
    }
    @Test
    public void postApiTest() throws IOException {
        Log.info("----------开始执行用例getApiTest...----------");
        restClient = new RestClient();

        Log.info("导入请求头");
        HashMap<String,String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");

        //User user = new User("Anthony","tester");
        //String userJsonString = JSON.toJSONString(user);
        //直接用字符串传入请求体
        //String userJsonString = "{\"job\":\"tester\",\"name\":\"Anthony\"}";
        //System.out.println(userJsonString);

        closeableHttpResponse = restClient.post(url,userJsonString,headerMap);
        Reporter.log("接口地址：" + url);
        Reporter.log("JSON：" + userJsonString);

        Log.info("测试响应状态码是否是201");
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode,RESPNSE_STATUS_CODE_201,"status code is not 201");
        Reporter.log("状态码：" + statusCode);

        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
        Reporter.log("接口响应：" + responseString);
        JSONObject responseJson = JSON.parseObject(responseString);
        String name = TestUtil.getValueByJPath(responseJson,"name");
        Log.info("执行JSON解析，解析的内容是 " + name);
        String job = TestUtil.getValueByJPath(responseJson,"job");
        Log.info("执行JSON解析，解析的内容是 " + job);
        Log.info("接口内容响应断言");
        Assert.assertEquals(name,"Anthony","name is not same");
        Assert.assertEquals(job,"tester","job is not same");
        Log.info("----------用例执行结束...----------");

    }
}

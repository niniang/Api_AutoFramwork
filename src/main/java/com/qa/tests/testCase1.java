package com.qa.tests;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.base.TestBase;
import com.qa.parameters.postParameters;
import com.qa.restclient.RestClient;
import com.qa.util.TestUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;

import static com.qa.util.TestUtil.dtt;


public class testCase1 extends TestBase {
    TestBase testBase;
    CloseableHttpResponse closeableHttpResponse;
    RestClient restClient;
    String host;
    String testCaseExcel;
    HashMap<String,String> postHeader = new HashMap<String, String>();
    final static Logger Log = Logger.getLogger(PostApiTest.class);
    String token;

    @BeforeClass
    public void setUp(){
        testBase = new TestBase();
        restClient = new RestClient();
        postHeader.put("Content-Type","application/json");
        //载入配置文件，接口endpoint
        host = prop.getProperty("HOST");
        //载入配置文件，post接口参数
        testCaseExcel = prop.getProperty("testCase1data");
    }

    @DataProvider(name = "postData")
    public Object[][] post() throws IOException {
        return dtt(testCaseExcel,0);
    }

    @Test(dataProvider = "postData")
    public void login(String loginUrl,String email,String password) throws IOException {

        Log.info("----------开始执行用例testCase1...----------");
        //使用构造函数将传入的用户名密码初始化成登录请求参数
        postParameters loginParameters = new postParameters(email,password);
        //将登录请求对象序列化成json对象
        String userJsonString = JSON.toJSONString(loginParameters);
        //发送登录请求
        closeableHttpResponse = restClient.post(host+loginUrl,userJsonString,postHeader);
        //从返回结果中获取状态码
        Log.info("测试响应状态码是否是200");
        Log.info("接口内容响应断言");
        int statusCode = TestUtil.getStatusCode(closeableHttpResponse);
        Assert.assertEquals(statusCode,200);
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
        JSONObject responseJson = JSON.parseObject(responseString);
        token = TestUtil.getValueByJPath(responseJson,"token");
        Log.info("token = " + token);
    }

    @AfterClass
    public void endTest(){
        Log.info("----------用例执行结束...----------");
    }
}

package com.qa.tests;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.base.TestBase;
import com.qa.restclient.RestClient;
import java.io.IOException;
import java.util.HashMap;
import com.qa.util.TestUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GetApiTest extends TestBase{
    TestBase testBase;
    String host;
    String api;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    final static Logger Log = Logger.getLogger(GetApiTest.class);

    @BeforeClass
    public void setUp(){
        testBase = new TestBase();
        host = prop.getProperty("HOST");
        api = prop.getProperty("GetApi");
        url = host + api;
        //url = host + "/api/users?page=2";
    }
    @Test
    public void getApiTest() throws IOException {
        Log.info("----------开始执行用例getApiTest...----------");
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);

        Log.info("测试响应状态码是否是200");
        int responseStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(responseStatusCode,RESPNSE_STATUS_CODE_200,"response status code is not 200");
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"Utf-8");

        JSONObject responseJson = JSON.parseObject(responseString);
        String s = TestUtil.getValueByJPath(responseJson,"data[0]/first_name");
        Log.info("执行JSON解析，解析的内容是 " + s);
        Log.info("接口内容响应断言");
        Assert.assertEquals(s, "Eve","first name is not Eve");
        Log.info("----------用例执行结束...----------");
    }
    @Test
    public void getApiTest02() throws IOException {
        Log.info("----------开始执行用例getApiTest02...----------");
        restClient = new RestClient();
        Log.info("导入请求头");
        HashMap <String,String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");
        closeableHttpResponse = restClient.get(url,headerMap);

        Log.info("测试响应状态码是否是200");
        int responseStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(responseStatusCode,RESPNSE_STATUS_CODE_200,"response status code is not 200");
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"Utf-8");
        JSONObject responseJson = JSON.parseObject(responseString);
        String s = TestUtil.getValueByJPath(responseJson,"data[0]/first_name");
        Log.info("执行JSON解析，解析的内容是 " + s);
        Log.info("接口内容响应断言");
        Assert.assertEquals(s,"Eve","first name is not Eve");
        Log.info("----------用例执行结束...----------");
    }
}

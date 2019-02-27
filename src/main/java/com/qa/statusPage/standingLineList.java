package com.qa.statusPage;

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
import java.sql.*;
import java.util.ArrayList;


public class standingLineList extends TestBase {

    TestBase testBase;
    String host;
    String api;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    final static Logger log = Logger.getLogger(standingLineList.class);
    String db_url;
    String user;
    String passwd;
    Connection conn;
    Statement stmt;

    @BeforeClass
    public void setUp(){
        testBase = new TestBase();
        host = prop.getProperty("CLOUD_HOST");
        api = prop.getProperty("Standing_Line_List");
        url = host + api;
        db_url = prop.getProperty("DB_URL");
        user = prop.getProperty("USER");
        passwd = prop.getProperty("PASSWD");

    }

    @Test
    public void standingLineList() throws IOException {
        log.info("开始执行用例standingLineList...");

        //请求接口
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);
        Reporter.log("接口地址：" + url);

        log.info("验证返回码是否为200");
        int responseStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
        log.info("返回码：" + responseStatusCode);
        log.info("接口相应：" + responseString);
        Reporter.log("返回码：" + responseStatusCode);
        Reporter.log("接口响应：" + responseString);

        //解析JSON
        JSONObject reponseJson = JSON.parseObject(responseString);
        String n = TestUtil.getValueByJPath(reponseJson,"data[0]/name");
        log.info("接口响应data[0]中包含：" + n);

        //查询数据库,查询type为1的location名称
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(db_url,user,passwd);
            stmt = conn.createStatement();
            String sql = "SELECT id,name,type FROM location where type = \"1\"";
            //String sql = "SELECT id,name,type FROM location WHERE id = \"f958679b62c9405081ec31742d0423a9\"";
            ResultSet rs = stmt.executeQuery(sql);

            //将查询结果存入ArrayList
            ArrayList<String> nameList = new ArrayList<String>();
            while (rs.next()){
                nameList.add(rs.getString("name"));
            }

            //遍历ArrayList
            for (String str : nameList){
                log.info("数据库中包含：" + str);
            }

            //断言nameList中是否包换data[0]name
            boolean contain = nameList.contains(n);
            Assert.assertEquals(contain,true,"is not equal");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        log.info("用例执行结束...");
    }
}

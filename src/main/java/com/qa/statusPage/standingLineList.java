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


public class standingLineList extends TestBase {

    TestBase testBase;
    String host;
    String api;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    final static Logger log = Logger.getLogger(standingLineList.class);
    String DB_URL = "jdbc:mariadb://192.168.2.60:31007/elec_cloud";
    String USER = "root";
    String PASSWD = "v@aseit.0N9B";
    Connection conn;
    Statement stmt;

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

        JSONObject reponseJson = JSON.parseObject(responseString);
        String s = TestUtil.getValueByJPath(reponseJson,"data[0]/id");
        System.out.println(s);

        //查询数据库
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL,USER,PASSWD);
            stmt = conn.createStatement();
            String sql = "SELECT id,name,type FROM location where id =\"" + s + "\"";

            ResultSet rs = stmt.executeQuery(sql);
            String id = null;
            while (rs.next()){
                id = rs.getString("id");
                System.out.println(id);
            }
//            Assert.assertEquals(id,s,"is equal");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        log.info("用例执行结束...");
    }
}

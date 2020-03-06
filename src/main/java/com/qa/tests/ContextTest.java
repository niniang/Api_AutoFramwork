package com.qa.tests;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * 失败之作，请勿参考
 */
public class ContextTest {
    LoginTest loginTest = new LoginTest();
    HttpClientContext context = loginTest.context;

    @Test
    public void contextTest() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet("http://192.168.2.60:10090/api/auth/company_tree");
        CloseableHttpResponse response02 = httpClient.execute(get,context);
        int responseStatusCode = response02.getStatusLine().getStatusCode();
        System.out.println(responseStatusCode);
    }
}

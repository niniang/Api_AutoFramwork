package com.qa.util;

import org.apache.http.client.CookieStore;

import java.io.*;

public class CookieUtil {
    //使用序列化的方式保存CookieStore到本地文件，方便后续的读取使用
    public static void saveCookieStore(CookieStore cookieStore, String savePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(savePath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(cookieStore);
        oos.close();
    }

    //读取Cookie的序列化文件，读取后可以直接使用
    public static CookieStore readCookieStore(String savePath) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(savePath);
        ObjectInputStream ois = new ObjectInputStream(fis);
        CookieStore cookieStore = (CookieStore)ois.readObject();
        ois.close();
        return cookieStore;
    }
}

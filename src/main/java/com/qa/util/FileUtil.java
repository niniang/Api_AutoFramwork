package com.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FileUtil {
    //设置文件存取路径
    public static String path = System.getProperty("user.dir") + "/src/main/java/com/qa/config/cookie.properties";

    // 检测文件及路径是否存在,不存在则创建该文件及相关路径
    static {
        try {
            int index = path.lastIndexOf("/");
            String path1 = path.substring(0, index);
            File file = new File(path1);
            if (file.exists() && !file.isDirectory()) {
                file.mkdir();
            }
            file = new File(path);
            if (file.exists()) {

                file.createNewFile();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // 根据键获取值
    public static String getValue(String key,String defaultValue){
        try{
            Properties properties = new Properties();
            // 加载文件,获取值
            properties.load(new FileInputStream(path));
            return properties.getProperty(key,defaultValue);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //插入值
    public static void setValue(String key,String value){
        FileOutputStream outputStream = null;
        try{
            Properties properties = new Properties();
            properties.load(new FileInputStream(path));
            properties.setProperty(key,value);
            outputStream = new FileOutputStream(path);
            properties.store(outputStream,"保存数据");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(outputStream != null){
                    outputStream.close();
                    outputStream = null;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // 根据Key删除相应的值
    public static void remove(String key){
        FileOutputStream outputStream = null;
        try{
            Properties properties = new Properties();
            properties.load(new FileInputStream(path));
            properties.remove(key);
            outputStream = new FileOutputStream(path);
            properties.store(outputStream,"保存数据");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(outputStream != null){
                    outputStream.close();
                    outputStream = null;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // 测试
    public static void main(String[] agrs){
        setValue("1","yi");
        setValue("2","b");
        remove("2");
    }

}

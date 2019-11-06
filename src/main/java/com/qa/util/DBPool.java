package com.qa.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DBPool {
    private static Logger log = Logger.getLogger(DBPool.class);
    private static DBPool dbPool = new DBPool();
    private static Map<String, ComboPooledDataSource> dataSources = new ConcurrentHashMap<String, ComboPooledDataSource>();

    private DBPool(){

    }

    public final static DBPool getInstance(){
        return dbPool;
    }

    public final Connection getConnection(String connName){
        try {
            Connection connection = getDataSource(connName).getConnection();
            return connection;
        } catch (SQLException e) {
            log.error("Cant get the connection:" + e);
            throw new RuntimeException("unable to connect to the database",e);
        }
    }

    private ComboPooledDataSource getDataSource(String connName){
        ComboPooledDataSource dataSource = dataSources.get(connName);
        if(dataSource != null){
            log.info(connName + "'s dataSources is not null");
            return dataSource;
        }else{
            dataSource = new ComboPooledDataSource(connName);
            synchronized (dataSources){
                dataSources.put(connName,dataSource);
                log.info("put a dataSource in " + connName +"'s DBPool");
            }
            return dataSource;
        }
    }

    /**
     * 关闭连接
     * @param connection
     */
    public final void close(Connection connection){
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            log.info("关闭connection");
        }
    }
}

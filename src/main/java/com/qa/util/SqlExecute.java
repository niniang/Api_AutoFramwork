package com.qa.util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlExecute {
    ResultSet resultSet;
    PreparedStatement preparedStatement;
    String target;
    final static Logger log = Logger.getLogger(SqlExecute.class);

    public void getSqlExecute(Connection connection,String sql) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
    }

    public String getSqlExecute(Connection connection,String sql,String row) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        target = resultSet.getString(row);
        return target;
    }

    public ResultSet getResultSet(Connection connection,String sql) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet;
    }

    public void close(){
        if(resultSet != null){
            try {
                log.info("关闭resultSet");
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null){
            try {
                log.info("关闭preparedStatement");
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

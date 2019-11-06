package com.qa.tests;


import com.qa.util.DBPool;
import com.qa.util.SqlExecute;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcTest {
    static Connection connection;
    static Connection connection01;

    public static void main(String[] agrs) {
//        Connect connect = new Connect();
//        Connection connection = connect.getConnection("DB_Url_Cloud");
//        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM elec_cloud.location WHERE type = '1'");
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        //System.out.println(resultSet.first());
//        while(resultSet.next()){
//            System.out.println(resultSet.getString(1));
//        }
//
//        resultSet.close();
//        connect.closeConnection();

        SqlExecute sqlExecute;
        SqlExecute sqlExecute01;
//        try {
//            connection = JDBCUtil.getConnection();
//            sqlExecute = new SqlExecute();
//            String sql = "SELECT id FROM elec_cloud.location WHERE type = '1'";
//            String id = sqlExecute.getSqlExecute(connection,sql,"id");
//            System.out.println(id);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JDBCUtil.close(connection);
//        }finally {
//            JDBCUtil.close(connection);
//        }

        try {
            connection = DBPool.getInstance().getConnection("cloud");
            if (connection != null)
                System.out.println("ok");
            sqlExecute = new SqlExecute();
            String sql = "SELECT id FROM elec_cloud.location WHERE type = '1'";
            String id = sqlExecute.getSqlExecute(connection,sql,"id");
            System.out.println(id);
        } catch (SQLException e) {
            e.printStackTrace();
            DBPool.getInstance().close(connection);
        } finally {
            DBPool.getInstance().close(connection);
        }

        try {
            connection01 = DBPool.getInstance().getConnection("fog");
            if (connection01 != null)
                System.out.println("ok");
            sqlExecute01 = new SqlExecute();
            String sql = "SHOW DATABASES";
            String id = sqlExecute01.getSqlExecute(connection01,sql,"Database");
            System.out.println(id);
        } catch (SQLException e) {
            e.printStackTrace();
            DBPool.getInstance().close(connection01);
        } finally {
            DBPool.getInstance().close(connection01);
        }

    }
}

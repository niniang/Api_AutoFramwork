package com.qa.tests;

import java.sql.*;

public class JDBCTest {
    static final String DB_URL = "jdbc:mariadb://192.168.2.60:31007/elec_cloud";
    static final String USER = "root";
    static final String PASSWD = "v@aseit.0N9B";

    public static void main(String[] args){
        Connection conn;
        Statement stmt;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("opem the database...");

            conn = DriverManager.getConnection(DB_URL,USER,PASSWD);
            System.out.println("get instance...");

            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id,name,type FROM location";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                int type = rs.getInt("type");
                System.out.println("id:" + id);
                System.out.println("name:" + name);
                System.out.println("type" + type);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

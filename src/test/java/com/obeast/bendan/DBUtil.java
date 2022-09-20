package com.obeast.bendan;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    static String ip = "localhost";
    static int port = 3306;
    static String database = "bendan";
    static String encoding = "UTF-8";
    static String loginName = "root";
    static String password = "mysql1234";
    static String useUnicode = "true";
    static String serverTimezone = "GMT";


    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception{
//        jdbc:mysql://localhost:3306/jdbc?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT
        String url = String.format("jdbc:mysql://%s:%d/%s?useUnicode=%s&characterEncoding=%s&serverTimezone=%s", ip, port, database, useUnicode, encoding, serverTimezone);
        return DriverManager.getConnection(url, loginName, password);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getConnection());
    }

}
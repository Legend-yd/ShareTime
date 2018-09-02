package com.sharetime.jdbc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @Author: yd
 * @Date: 2018/7/20 19:58
 * @Version 1.0
 */
public class JdbcUtil {
    /**
     * 读取配置文件，用于使用其他数据库是可灵活切换
     * 用properties保存 url user pwd
     * <p>
     * 写在静态代码块中可以让它只执行一次
     */
    private static final String DRIVERCLASS;
    private static final String URL;
    private static final String USER;
    private static final String PWD;

    static {
        Properties properties = new Properties();
        try {
            //加载该配置文件
            properties.load(new FileInputStream("src/jdbc.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DRIVERCLASS = properties.getProperty("loadDriver");
        URL = properties.getProperty("url");
        USER = properties.getProperty("user");
        PWD = properties.getProperty("pwd");
    }

    /**
     * 加载驱动
     *
     * @return void
     */
    public void loadDriver() {
        try {
            Class.forName(DRIVERCLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     *
     * @return java.sql.Connection
     */
    public Connection getConnection() {
        loadDriver();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PWD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 执行sql语句
     *
     * @param sql
     * @return java.sql.ResultSet
     */
    private ResultSet select(String sql) {

        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = getConnection().prepareStatement(sql);
            res = st.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * 释放资源
     *
     * @param ac
     * @return void
     */
    private void release(AutoCloseable ac) {
        if (ac != null) {
            if (ac instanceof Statement || ac instanceof ResultSet || ac instanceof Connection) {
                try {
                    ac.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ac = null;
            }
        }
    }

    /**
     * 释放资源
     *
     * @param st, conn
     * @return void
     */
    public void release(Statement st, Connection conn) {
        release(st);
        release(conn);
    }

    /**
     * 释放资源
     *
     * @param st, conn, re
     * @return void
     */
    public void release(Statement st, Connection conn, ResultSet re) {
        release(st, conn);
        release(re);

    }

}

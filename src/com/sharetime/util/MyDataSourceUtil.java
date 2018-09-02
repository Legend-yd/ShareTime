package com.sharetime.util;


import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 使用c3p0连接池
 *
 * @Author: yd
 * @Date: 2018/7/21 11:35
 * @Version 1.0
 */
public class MyDataSourceUtil {
    private static ComboPooledDataSource ds = new ComboPooledDataSource();

    /**
     * 获取数据源
     *
     * @return javax.sql.DataSource
     */
    public static DataSource getDataSource() {
        return ds;
    }

    /**
     * 获取连接
     *
     * @return java.sql.Connection
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    //释放资源
    public void releaseResource(Statement st, ResultSet re) {
        releaseStatement(st);
        releaseResultSet(re);
    }

    //释放资源
    public void releaseResource(Connection conn, Statement st, ResultSet re) {
        releaseResource(st, re);
    }

    /**
     * 释放Connection 资源
     *
     * @param conn
     * @return void
     */
    public void releaseConnection(Connection conn) {
        releaseAutoCloseable(conn);
    }

    /**
     * 释放Statement 资源
     *
     * @param st
     * @return void
     */
    public void releaseStatement(Statement st) {
        releaseAutoCloseable(st);
    }

    /**
     * 释放ResultSet 资源
     *
     * @param re
     * @return void
     */
    public void releaseResultSet(ResultSet re) {
        releaseAutoCloseable(re);
    }

    private void releaseAutoCloseable(AutoCloseable ac) {
        if (ac != null) {
            if (ac instanceof Connection || ac instanceof Statement || ac instanceof ResultSet) {
                try {
                    ac.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ac = null;
            }
        }
    }

}

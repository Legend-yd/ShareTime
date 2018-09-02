package com.sharetime.jdbc.util;

import com.sharetime.domain.User;
import com.sharetime.util.MyDataSourceUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * 增删改查的工具类
 *
 * @Author: yd
 * @Date: 2018/7/21 16:47
 * @Version 1.0
 */
public class CurdUtil {


    private QueryRunner qr = new QueryRunner(MyDataSourceUtil.getDataSource());

    public void select(String sql) {
        try {
            User user = qr.query(sql,new BeanHandler<>(User.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入数据
     *
     * @param sql
     * @return void
     */
    public void insert(String sql) {
        try {
            qr.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String sql = "select * from user";
        new CurdUtil().select(sql);
    }
}

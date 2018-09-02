package com.sharetime.dao;

import com.sharetime.domain.User;
import com.sharetime.util.DateUtils;
import com.sharetime.util.MyDataSourceUtil;
import net.sf.json.JSONObject;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;

import static java.util.concurrent.Executors.newCachedThreadPool;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

/**
 * 操作数据库中的信息
 * <p>
 * DAO (Data Access Objects)数据访问对象
 *
 * @Author: yd
 * @Date: 2018/7/21 20:52
 * @Version 1.0
 */
public class UserDao {

    public User findUser(String phoneNum, String password) {
        User user = null;
        QueryRunner qr = new QueryRunner(MyDataSourceUtil.getDataSource());
        String sql = "select * from user where phoneNum = ? and password = ?";
        try {
            user = qr.query(sql, new BeanHandler<>(User.class), phoneNum, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 根据手机号查找是否存在该用户
     *
     * @param phoneNum
     * @return com.sharetime.domain.User
     */
    public User findUser(String phoneNum) {
        User user = null;
        QueryRunner qr = new QueryRunner(MyDataSourceUtil.getDataSource());
        String sql = "select * from user where phoneNum = ?";
        try {
            user = qr.query(sql, new BeanHandler<>(User.class), phoneNum);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 注册用户，添加手机号和密码
     *
     * @param phoneNum, password
     * @return void
     */

    public void addUser(String phoneNum, String password) {
        QueryRunner qr = new QueryRunner(MyDataSourceUtil.getDataSource());
        String sql = "insert into user (phoneNum,password) values (" + phoneNum + "," + password + ")";
        try {
            qr.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新密码
     *
     * @param phoneNum, password
     * @return void
     */
    public void updatePassword(String phoneNum, String password) {
        String sql = " password='" + password + "'";
        updateUser(phoneNum, sql);
    }


    public void updateInfo(String phoneNum, JSONObject jsonObject) {
        String sql = "";
        if (jsonObject.containsKey("image")) {
            String image = jsonObject.getString("image");
            sql += " image='" + image + "',";
        }
        if (jsonObject.containsKey("password")) {
            String password = jsonObject.getString("password");
            sql += " password='" + password + "',";
        }
        if (jsonObject.containsKey("petName")) {
            String petName = jsonObject.getString("petName");
            sql += " petName='" + petName + "',";
        }
        if (jsonObject.containsKey("birthday")) {
            String birthday = jsonObject.getString("birthday");
            int age = getAge(birthday);
            sql += " age='" + age + "', birthday='" + birthday + "',";
        }
        if (jsonObject.containsKey("sex")) {
            int sex = Integer.parseInt(jsonObject.getString("sex"));
            sql += " sex='" + sex + "',";
        }
        if (jsonObject.containsKey("school")) {
            String school = jsonObject.getString("school");
            sql += " school='" + school + "',";
        }
        if (jsonObject.containsKey("province")) {
            String province = jsonObject.getString("province");
            sql += " province='" + province + "',";
        }
        if (jsonObject.containsKey("department")) {
            String department = jsonObject.getString("department");
            sql += " department='" + department + "',";
        }

        if (jsonObject.containsKey("studentId")) {
            String studentId = jsonObject.getString("studentId");
            sql += " studentId='" + studentId + "',";
        }
        String newsql = sql.substring(0, sql.length() - 1);
        updateUser(phoneNum, newsql);
    }

    /**
     * 更新phoneNum的信息
     *
     * @param phoneNum, inSql
     * @return void
     */
    public void updateUser(String phoneNum, String inSql) {
        QueryRunner qr = new QueryRunner(MyDataSourceUtil.getDataSource());
        String sql = "update user set" + inSql + " where phoneNum = '" + phoneNum + "'";
        try {
            qr.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过当前时间计算年龄
     *
     * @param birthday
     * @return int
     */
    private int getAge(String birthday) {
        int birthYear = Integer.parseInt(birthday.substring(0, 4));
        //获取网络当前时间
        int thisYear = Integer.parseInt(new DateUtils().getYear());
        return thisYear - birthYear;

    }

    public String getImage(String phoneNum) {
        User user = findUser(phoneNum);
        return user.getImage();
    }

}

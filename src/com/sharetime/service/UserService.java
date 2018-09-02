package com.sharetime.service;

import com.sharetime.dao.UserDao;
import com.sharetime.domain.User;
import com.sharetime.util.Base64Utils;
import net.sf.json.JSONObject;

/**
 * user的服务类
 *
 * @Author: yd
 * @Date: 2018/7/21 20:52
 * @Version 1.0
 */
public class UserService {

    /**
     * 用户登录，根据用户名密码在数据库中擦找如果没有找到则返回一个空对象，否则将用户信息传入
     *
     * @param phoneNum, password
     * @return net.sf.json.JSONObject
     */
    public JSONObject login(String phoneNum, String password) {
        UserDao userDao = new UserDao();
        User user = userDao.findUser(phoneNum, password);
        if (user == null) {
            return null;
        } else {
            JSONObject serviceToClient = new JSONObject();
            serviceToClient.accumulate("status", "success");
            serviceToClient.accumulate("name", user.getPetName());
            serviceToClient.accumulate("pwd", user.getPassword());
            serviceToClient.accumulate("school", user.getSchool());
            return serviceToClient;
        }

    }

    /**
     * 判断该手机号码是否已经注册，注册了返回假，没有注册返回真
     *
     * @param phoneNum
     * @return boolean
     */
    public User findUser(String phoneNum) {
        UserDao userDao = new UserDao();
        User user;
        user = userDao.findUser(phoneNum);
        return user;
    }

    /**
     * 创建新用户
     *
     * @param phoneNum, password
     * @return void
     */
    public void create(String phoneNum, String password) {
        UserDao userDao = new UserDao();
        userDao.addUser(phoneNum, password);
    }

    /**
     * 修改密码
     *
     * @param phoneNum, password
     * @return void
     */
    public void updatePassword(String phoneNum, String password) {
        UserDao userDao = new UserDao();
        userDao.updatePassword(phoneNum, password);
    }

    /**
     * 修改个人中心里的信息
     *
     * @param phoneNum, jsonObject
     * @return void
     */
    public void updateInfo(String phoneNum, JSONObject jsonObject) {
        UserDao userDao = new UserDao();
        userDao.updateInfo(phoneNum, jsonObject);
    }

    /**
     * 获取头像
     *
     * @param phoneNum
     * @return java.lang.String
     */
    public String getImage(String phoneNum) {
        UserDao userDao = new UserDao();
        return new Base64Utils().ImageToBase64(userDao.getImage(phoneNum));

    }
}
package com.sharetime.test;

import com.sharetime.dao.UserDao;
import com.sharetime.domain.User;

/**
 * @Author: yd
 * @Date: 2018/7/26 11:00
 * @Version 1.0
 */
public class Test {

    public static void main(String[] args) {
        UserDao userdao = new UserDao();
        userdao.updatePassword("18838161573","123789");
        User user = userdao.findUser("18838161573");
        System.out.println(user);
    }
}

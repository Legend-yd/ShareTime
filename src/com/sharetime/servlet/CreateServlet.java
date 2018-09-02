package com.sharetime.servlet;

import com.sharetime.service.UserService;
import com.sharetime.util.MyJsonUtil;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 创建新用户
 *
 * @Author: yd
 * @Date: 2018/7/22 18:31
 * @Version 1.0
 */
public class CreateServlet extends MyServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MyJsonUtil myJsonUtil = new MyJsonUtil(request, response);
        JSONObject serviceToClient = sendToClient(myJsonUtil.getInfo());
        myJsonUtil.sendInfo(serviceToClient);
    }

    protected JSONObject sendToClient(JSONObject clientToService) {
        JSONObject serviceToClient = new JSONObject();
        String phoneNum = clientToService.getString("phoneNum");
        String checkNumber = clientToService.getString("checkNum");
        UserService userService = new UserService();
        if (userService.findUser(phoneNum) == null) {
            serviceToClient.accumulate("access", "yes");//允许注册
            if ("123456".equals(checkNumber)) {
                serviceToClient.accumulate("checkNode", "right");
                String password =clientToService.getString("password");
                userService.create(phoneNum,password);
            } else {
                serviceToClient.accumulate("checkNode", "wrong");
            }
        } else {
            serviceToClient.accumulate("access", "no");
        }
        return serviceToClient;
    }
}

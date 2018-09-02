package com.sharetime.servlet;

import com.sharetime.domain.User;
import com.sharetime.service.UserService;
import com.sharetime.util.MyJsonUtil;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 忘记密码
 *
 * @Author: yd
 * @Date: 2018/6/19 17:07
 * @Version 1.0
 */
public class ForgetServlet extends MyServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @param request response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MyJsonUtil myJsonUtil = new MyJsonUtil(request, response);
        JSONObject clientToService = myJsonUtil.getInfo();
        JSONObject serviceToClient = sendToClient(clientToService);
        myJsonUtil.sendInfo(serviceToClient);
    }

    private String phone;

    protected JSONObject sendToClient(JSONObject clientToService) {
        JSONObject serviceToClient = new JSONObject();
        UserService userService = new UserService();


        if (clientToService.containsKey("phoneNum")) {
            phone = clientToService.getString("phoneNum");
            User user = userService.findUser(phone);
            if (user != null) {
                serviceToClient.accumulate("phoneNum", "right");
            } else {
                serviceToClient.accumulate("phoneNum", "wrong");
            }
        }
        if (clientToService.containsKey("checkCode")) {
            String checkCode = clientToService.getString("checkCode");
            if (checkCode.equals("123")) {
                serviceToClient.accumulate("checkCode", "right");
            } else {
                serviceToClient.accumulate("checkCode", "wrong");
            }
        }
        if (clientToService.containsKey("pwd")) {
            String pwd = clientToService.getString("pwd");
            userService.updatePassword(phone, pwd);
            serviceToClient.accumulate("pwd", "right");
        }
        return serviceToClient;
    }
}

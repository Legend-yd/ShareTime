package com.sharetime.servlet;


import com.sharetime.service.UserService;
import com.sharetime.util.MyJsonUtil;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * 登录Servlet
 *
 * @Author: yd
 * @Date: 2018/6/19 11:35
 * @Version 1.0
 */
public class LoginServlet extends MyServlet {
    private static final long serialVersionUID = 1L;
    private HttpSession loginSession;//用于传递登录的验证码

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MyJsonUtil myJsonUtil = new MyJsonUtil(request, response);
        JSONObject clientToService = myJsonUtil.getInfo();
        loginSession = request.getSession();
        myJsonUtil.sendInfo(sendToClient(clientToService));
    }

    /**
     * 判断用户名密码，验证码是否正确，返回用于传送到客户端的json数据
     *
     * @param clientToService
     * @return net.sf.json.JSONObject
     */
    @Override
    protected JSONObject sendToClient(JSONObject clientToService) {
        String phoneNum = clientToService.getString("phoneNum");//获取phoneNum的值
        String pwd = clientToService.getString("pwd");//获取pwd的值
        String getCode = clientToService.getString("identify");//获取到的验证码
        //发送给客户端json对象：serviceToClient
        //创建session

        String realCode = loginSession.getAttribute("identify").toString();//真正的验证码
        JSONObject serviceToClient = new JSONObject();
        //如果验证码为空就说明验证码区域未显示可见
        //如果需要输入验证码，前端页面会拦截为空的情况
        if (!(getCode == null || "".equals(getCode)) && !realCode.equalsIgnoreCase(getCode)) {
            serviceToClient.accumulate("checkNode", "error");
        } else {
            JSONObject login = new UserService().login(phoneNum, pwd);
            if (login == null) {
                //未找到该用户
                serviceToClient.accumulate("status", "error");
            } else {
                serviceToClient.accumulateAll(login);
            }
        }
        return serviceToClient;
    }


}

package com.sharetime.servlet;

import com.sharetime.util.MyJsonUtil;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 首页Servlet
 *
 * @Author: yd
 * @Date: 2018/6/19 11:35
 * @Version 1.0
 */
public class IndexServlet extends MyServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //读取客户端传回来得json对象:clientToService
        MyJsonUtil myJsonUtil = new MyJsonUtil(request, response);
        JSONObject clientToService = myJsonUtil.getInfo();

        //发送给客户端的json对象:serviceToClient
        JSONObject serviceToClient = new JSONObject();
        myJsonUtil.sendInfo(serviceToClient);//将数据发送给客户端
    }

    @Override
    protected JSONObject sendToClient(JSONObject clientToService) {
        return null;
    }
}

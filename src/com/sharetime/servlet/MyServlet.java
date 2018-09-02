package com.sharetime.servlet;

import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: yd
 * @Date: 2018/7/22 18:33
 * @Version 1.0
 */
public abstract class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }

    /**
     * 根据从客户端接受的消息，处理后得出将发往客户端的消息
     *
     * @param clientToService
     * @return net.sf.json.JSONObject
     */
    protected abstract JSONObject sendToClient(JSONObject clientToService);
}

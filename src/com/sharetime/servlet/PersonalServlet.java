package com.sharetime.servlet;

import com.sharetime.domain.User;
import com.sharetime.service.UserService;
import com.sharetime.util.Base64Utils;
import com.sharetime.util.MyJsonUtil;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 个人中心
 *
 * @Author: yd
 * @Date: 2018/7/26 13:48
 * @Version 1.0
 */
public class PersonalServlet extends MyServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("peoplelogo") != null) {
            String phoneNum = request.getParameter("phoneNum");
            String image = new UserService().getImage(phoneNum);
            if (image != null) {
                response.getWriter().print(image);
            } else {
                response.getWriter().print("noimage");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MyJsonUtil myJsonUtil = new MyJsonUtil(request, response);
        JSONObject clientToService = myJsonUtil.getInfo();
        myJsonUtil.sendInfo(sendToClient(clientToService));
    }

    @Override
    protected JSONObject sendToClient(JSONObject clientToService) {
        JSONObject serviceToClient = new JSONObject();
        String phone = clientToService.getString("phoneNum");
        String personalInfo = clientToService.getString("personalInfo");
        if (personalInfo != null) {
            UserService userService = new UserService();
            User user = userService.findUser(phone);
            serviceToClient.accumulate("sex", user.getSex());//性别
            serviceToClient.accumulate("age", user.getAge());//年龄
            serviceToClient.accumulate("level", user.getLevel());//等级
            serviceToClient.accumulate("school", user.getSchool());//学校
            serviceToClient.accumulate("petName", user.getPetName());//昵称
            serviceToClient.accumulate("birthday", user.getBirthday());//生日
            serviceToClient.accumulate("password", user.getPassword());//密码
            serviceToClient.accumulate("phoneNum", user.getPhoneNum());//手机号
            serviceToClient.accumulate("studentId", user.getStudentId());//学号
            serviceToClient.accumulate("department", user.getDepartment());//院系
            serviceToClient.accumulate("creditScore", user.getCreditScore());//信用积分
            serviceToClient.accumulate("personalSignature", user.getPersonalSignature());//个性签名
            String base = new Base64Utils().ImageToBase64(user.getImage());
            serviceToClient.accumulate("image", base);//图片
        }
        return serviceToClient;
    }
}
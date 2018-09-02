package com.sharetime.servlet;

import com.sharetime.util.IdentityCode;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码图片传输Servlet
 *
 * @Author: yd
 * @Date: 2018/6/19 11:39
 * @Version 1.0
 */
public class ImageServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedImage image;//实例化BufferedImage
        IdentityCode identityCode = new IdentityCode();
        image = identityCode.getImage();
        HttpSession mySession = request.getSession();
        mySession.setAttribute("identify", identityCode.getText());//把图片里的text放入session中
        ImageIO.write(image, "jpg", response.getOutputStream());//向页面输出图像
    }
}

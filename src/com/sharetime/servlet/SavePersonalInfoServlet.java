package com.sharetime.servlet;

import com.sharetime.service.UserService;
import com.sharetime.util.Base64Utils;
import com.sharetime.util.MyJsonUtil;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 保存修改的个人信息
 *
 * @Author: yd
 * @Date: 2018/7/27 11:08
 * @Version 1.0
 */
public class SavePersonalInfoServlet extends MyServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MyJsonUtil myJsonUtil = new MyJsonUtil(request, response);
        JSONObject clientToService = myJsonUtil.getInfo();
        String image = clientToService.getString("image");
        String phoneNum = clientToService.getString("phoneNum");
        clientToService.remove("image");
        String imagePath = saveAtDisk(phoneNum, image);
        if (!imagePath.equals("error")) {
            clientToService.accumulate("image", imagePath);
        }
        myJsonUtil.sendInfo(sendToClient(clientToService));
    }

    /**
     * 处理要发送到网页的信息
     *
     * @param clientToService
     * @return net.sf.json.JSONObject
     */
    @Override
    protected JSONObject sendToClient(JSONObject clientToService) {
        JSONObject serviceToClient = new JSONObject();
        UserService userService = new UserService();
        String phoneNum = clientToService.getString("phoneNum");
        userService.updateInfo(phoneNum, clientToService);
        serviceToClient.accumulate("update", "success");
        return serviceToClient;
    }

    /**
     * 将图片保存到本地磁盘，并传回该图片保存路径
     *
     * @param phoneNum, image
     * @return java.lang.String
     */
    public String saveAtDisk(String phoneNum, String image) throws IOException {
        String bootPath = "C:/Users/YD/Pictures/sharetime/picture/personal";
        File file = new File(bootPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String childpath = bootPath + "/" + phoneNum + ".jpg";
        File imageFile = new File(childpath);
        if (!imageFile.exists()) {
            imageFile.createNewFile();
        }
        int i = image.indexOf("base64,") + 7;//获取前缀data:image/gif;base64,的坐标
        String newImage = image.substring(i, image.length());//去除前缀
        //存储到本地
        Base64Utils base64Utils = new Base64Utils();
        if (base64Utils.Base64ToImage(newImage, childpath)) {
            base64Utils.changeSize(300, 300, childpath);
        } else {
            return "error";
        }
        //返回图片的路径
        image = imageFile.getAbsolutePath().replace("\\", "/");
        return image;
    }

}

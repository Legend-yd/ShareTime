package com.sharetime.util;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 获取、发送、处理json数据的工具类
 *
 * @version 1.0
 * @Date 2018-6-18
 */
public class MyJsonUtil {
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * 构造方法，用于给request ，response赋值
     *
     * @param request
     * @param response
     */
    public MyJsonUtil(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 读取客户端传回来得数据
     *
     * @return JSONObject对象
     */
    public JSONObject getInfo() throws IOException {
        request.setCharacterEncoding("UTF-8");
        StringBuffer getjson = new StringBuffer();
        BufferedReader reader = request.getReader();//获取请求中的数据流
        for (String line; (line = reader.readLine()) != null; ) {//用for循环能使line在用完之后就回收
            getjson.append(line);
        }
        /*String show =URLDecoder.decode(myJson.toString(),"UTF-8");//将内容中的汉字有url编码转换*/
        String json = new String(getjson);
        return JSONObject.fromObject(json);
    }

    /**
     * 发送数据给客户端
     *
     * @param serviceToClient
     */
    public void sendInfo(JSONObject serviceToClient) throws IOException {
        response.setContentType("application/json;charset=utf-8");//指定返回的格式为JSON格式
        PrintWriter writerClient = response.getWriter();
        writerClient.print(serviceToClient);
        writerClient.close();
    }

}

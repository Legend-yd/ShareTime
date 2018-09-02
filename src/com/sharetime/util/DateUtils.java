package com.sharetime.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 关于时间
 *
 * @Author: yd
 * @Date: 2018/7/29 10:28
 * @Version 1.0
 */
public class DateUtils {
    public Date getIntenetDate() {
        //生成连接对象
        URLConnection uc;
        try {
            URL url = new URL("https://bjtime.cn");
            uc = url.openConnection();
            uc.connect();
            long time = uc.getDate();
            return new Date(time);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回当前年份
     *
     * @return java.lang.String
     */
    public String getYear() {
        Date date = getIntenetDate();
        return new SimpleDateFormat("yyyy").format(date);
    }

    /**
     * 返回当前年月日
     *
     * @return java.lang.String
     */
    public String getDay() {
        Date date = getIntenetDate();
        return new SimpleDateFormat("yyyy-MM-dd").format(date);

    }

    /**
     * 返回当前时间，精确到秒
     *
     * @return java.lang.String
     */
    public String getDate() {
        Date date = getIntenetDate();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
}

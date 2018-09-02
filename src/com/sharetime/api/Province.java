package com.sharetime.api;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.URL;


/**
 * @Author: yd
 * @Date: 2018/7/26 22:34
 * @Version 1.0
 */
public class Province {

    /**
     * 测试
     */
    public static void main(String[] args) throws IOException {
        JSONArray school = new Province().getProvince();
        System.out.println(school);
    }

    /**
     * 获取全国的省份
     *
     * @return net.sf.json.JSONArray
     */
    public JSONArray getProvince() {
        String province = "[{\"question\":\"province\"},{\"id\":1,\"name\":\"北京\"},{\"id\":2,\"name\":\"天津\"},{\"id\":3,\"name\":\"河北\"},{\"id\":4,\"name\":\"山西\"},{\"id\":5,\"name\":\"内蒙古\"},{\"id\":6,\"name\":\"辽宁\"},{\"id\":7,\"name\":\"吉林\"},{\"id\":8,\"name\":\"黑龙江\"},{\"id\":9,\"name\":\"上海\"},{\"id\":10,\"name\":\"江苏\"},{\"id\":11,\"name\":\"浙江\"},{\"id\":12,\"name\":\"安徽\"},{\"id\":13,\"name\":\"福建\"},{\"id\":14,\"name\":\"江西\"},{\"id\":15,\"name\":\"山东\"},{\"id\":16,\"name\":\"河南\"},{\"id\":17,\"name\":\"湖北\"},{\"id\":18,\"name\":\"湖南\"},{\"id\":19,\"name\":\"广东\"},{\"id\":20,\"name\":\"广西\"},{\"id\":21,\"name\":\"海南\"},{\"id\":22,\"name\":\"重庆\"},{\"id\":23,\"name\":\"四川\"},{\"id\":24,\"name\":\"贵州\"},{\"id\":25,\"name\":\"云南\"},{\"id\":26,\"name\":\"西藏\"},{\"id\":27,\"name\":\"陕西\"},{\"id\":28,\"name\":\"甘肃\"},{\"id\":29,\"name\":\"青海\"},{\"id\":30,\"name\":\"宁夏\"},{\"id\":31,\"name\":\"新疆\"},{\"id\":32,\"name\":\"香港\"},{\"id\":33,\"name\":\"澳门\"},{\"id\":34,\"name\":\"台湾\"}]";
        return JSONArray.fromObject(province);
    }

    public JSONArray getSchool(int index) {
        String school = readTxt(index);
        return JSONArray.fromObject(school);
    }


    private String readTxt(int lineindex) {
        File file = new File("src/com/sharetime/api/myschool.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            int nowindex = 1;
            for (String line; (line = br.readLine()) != null; ) {
                if (nowindex == lineindex) {//需要的这行
                    br.close();
                    return line;
                }
                nowindex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取网络api中的学校信息并处理
     */
    private void testgetSchool() throws IOException {
        int j = 1;
        while (j < 35) {
            String ur = "http://119.29.166.254:9090/api/university/getUniversityByProvinceId?id=";
            ur += j;
            URL url = new URL(ur);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = br.readLine()) != null) {
                JSONArray array = JSONArray.fromObject(line);
                JSONArray array1 = new JSONArray();
                for (int i = 0; i < array.size(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    jsonObject.remove("website");
                    jsonObject.remove("abbreviation");
                    jsonObject.remove("id");
                    jsonObject.remove("level");
                    array1.add(jsonObject);
                }
            }
            br.close();
            j++;
        }
    }
}

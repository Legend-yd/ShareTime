package com.sharetime.servlet;

import java.io.*;

/**
 * @Author: yd
 * @Date: 2018/7/28 14:05
 * @Version 1.0
 */
public class shad {
    public String read() {
        File file = new File("abc.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedReader br;
        StringBuffer sh = new StringBuffer();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            for (String line; (line = br.readLine()) != null; ) {
                sh.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sh);
        return sh.toString();
    }

    public static void main(String[] args) {
        new shad().read();
    }
}

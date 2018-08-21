package com.pigtom.study.introduction_algorithms.utils;

/**
 * @Description
 * @Author tangDunhong@163.com
 * @Date Created in 2018/8/21 8:53
 */
public class View {
    public static void main(String args[]) {
        MapPanel panel = new MapPanel();
        MapFrame app = new MapFrame(panel);
        app.setVisible(true);
    }
}

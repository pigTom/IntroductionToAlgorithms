package com.pigtom.study.introduction_algorithms.utils;

import javax.swing.*;

/**
 * @Description
 * @Author tangDunhong@163.com
 * @Date Created in 2018/8/21 8:53
 */
public class View {
    public static void main(String args[]) {


        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        UIManager.put("MenuItem.font", Fonts.font1);
        UIManager.put("Menu.font", Fonts.font1);


        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                showGUI();
            }
        });

    }
    public static void showGUI() {
        MapPanel panel = new MapPanel();
        MapFrame app = new MapFrame(panel);
        app.setVisible(true);
//        new Thread(panel).start();


    }
}

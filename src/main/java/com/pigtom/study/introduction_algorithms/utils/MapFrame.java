package com.pigtom.study.introduction_algorithms.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Dimension2D;

/**
 * @Description
 * @Author tangDunhong@163.com
 * @Date Created in 2018/8/21 8:46
 */
public class MapFrame extends JFrame{
    public MapFrame(MapPanel mapPanel) {
        this.setTitle("Test");

        // add panel
        add(mapPanel);


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension2D d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(0, 0);
        this.setSize(1200, 1000);
    }
}

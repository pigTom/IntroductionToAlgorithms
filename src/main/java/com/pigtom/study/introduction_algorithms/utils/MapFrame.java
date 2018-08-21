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
        setLocation((int) (d.getWidth() - MapTranslator.screenWidth) / 2,
                (int) (d.getHeight() - MapTranslator.screenHeight) / 2);
        this.setSize(MapTranslator.screenWidth, MapTranslator.screenHeight);
    }
}

package com.pigtom.study.introduction_algorithms.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Description
 * @Author tangDunhong@163.com
 * @Date Created in 2018/8/22 9:56
 */
public class BasePanel extends JPanel{
    public BufferedImage saveImage() {
        BufferedImage paintImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_BGR);
        Graphics g = paintImage.getGraphics();
        paint(g);
        g.dispose();
        return paintImage;
    }
}

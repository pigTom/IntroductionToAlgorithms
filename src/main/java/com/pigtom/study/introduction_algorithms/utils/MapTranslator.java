package com.pigtom.study.introduction_algorithms.utils;

/**
 * @Description
 * @Author tangDunhong@163.com
 * @Date Created in 2018/8/21 8:41
 */
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class MapTranslator {
    public Double minX = null;
    public Double maxX = null;
    public Double minY = null;
    public Double maxY = null;
    public static Integer screenWidth = 1400;
    public static Integer screenHeight = 1000;
    public Double maxDistanceX = null; // 原始数据x坐标的最大值
    public Double maxDistanceY = null; // 原始数据y坐标的最大值
    public Double scaleX = 1d;
    public Double scaleY = 1d;
    public Point2D.Double mapOrigin = new Point2D.Double(0, 0);

    /**
     * 设置地图比例尺
     */
    public void setScale(){
        maxDistanceX = maxX - minX;
        maxDistanceY = maxY - minY;
        scaleX = screenWidth / maxDistanceX /1.2;
        scaleY = -screenHeight / maxDistanceY /1.2;
        System.out.println("scaleX " + scaleX + ", scaleY " + scaleY);
    }

    public void translate(Graphics2D g, JComponent component) {
//        setScale();
        mapOrigin.x = (minX + maxDistanceX / 2) * scaleX;
        mapOrigin.y = (minY + maxDistanceY / 2) * scaleY;
        g.translate(component.getWidth()/2, component.getHeight()/2 ); // 将(0,0)设为屏幕中心点
        g.translate(-mapOrigin.x, -mapOrigin.y); // 将图象中心点平移到(0, 0)屏幕中心点
    }
    public void translatePoint(final Point point, double width, double height) {
        int width1 = (int)(width/2 - mapOrigin.x);
        int height1 = (int)(height/2  - mapOrigin.y);
        // 将事件点转换为地图上的点
        point.translate(-width1,  -height1);
    }

    public Point2D.Double getRealPoint(final Point point, double width, double height) {
        Point point1 = new Point(point);
        translatePoint(point1, width, height);
        Point2D.Double p = new Point2D.Double();
        p.x = (point1.x / scaleX);
        p.y = (point1.y / scaleY);
        return p;
    }
}

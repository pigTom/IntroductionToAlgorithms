package com.pigtom.study.introduction_algorithms.utils;

import com.pigtom.study.introduction_algorithms.chapter12.ColorEnum;
import com.pigtom.study.introduction_algorithms.chapter12.Node;
import com.pigtom.study.introduction_algorithms.chapter12.Tree;
import com.pigtom.study.introduction_algorithms.chapter13.RedBlackTree;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;

/**
 * @Description
 * @Author tangDunhong@163.com
 * @Date Created in 2018/8/21 8:44
 */
@Service
public class MapPanel<T>  extends JPanel {
    private Tree<T> tree;

    private Graphics2D g;
    public MapPanel(Tree<T> tree) {
        this.tree = tree;
//        handlePosition(tree);
    }
    int radius = 15;
    int height = 50;
    @Override
    public void paint(Graphics gp) {
        super.paint(gp);
        g = (Graphics2D) gp;
        handlePosition(tree);
    }

    private void fillCircle(Graphics2D g, int centerX, int centerY, int radius) {
        g.fillOval(centerX - radius , centerY, 2 * radius, 2 * radius);
    }

    private void blackNode(Graphics2D g, int x, int y) {
        g.setColor(Color.black);
        fillCircle(g, x, y, radius);
    }
    private void redNode(Graphics2D g, int x, int y) {
        g.setColor(Color.RED);
        fillCircle(g, x, y, radius);
    }

    private void handlePosition(Tree<T> tree) {
        Node<T> node = tree.getRoot();
        if (node != RedBlackTree.NIL) {
            int width = this.getWidth()-100;
            node.width = width / 2;
            node.x = node.width;
            node.y = height;
            handlePosition(node);
        }
    }

    /**
     * 根据node的宽度和位置计算，node孩子的宽度和位置。
     * 如果node不为NIL,则node一定有左孩子和右孩子
     * @param node
     */
    private void handlePosition(Node<T> node) {


        if (node != RedBlackTree.NIL) {

            if (node.getColor() == ColorEnum.BLACK) {
                blackNode(g, node.x, node.y);
            } else redNode(g, node.x, node.y);


            int width = node.width/2;
            Node<T> left = node.getLeft();
            left.width = width;
            left.x = node.x - width;
            left.y = height + node.y;
            handlePosition(left);

            Node<T> right = node.getRight();
            right.width = width;
            right.x = node.x + width;
            right.y = height+ node.y;
            handlePosition(right);
        }
    }
}

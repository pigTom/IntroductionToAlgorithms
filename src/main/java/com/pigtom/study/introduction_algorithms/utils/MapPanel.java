package com.pigtom.study.introduction_algorithms.utils;

import com.pigtom.study.introduction_algorithms.chapter12.ColorEnum;
import com.pigtom.study.introduction_algorithms.chapter12.Node;
import com.pigtom.study.introduction_algorithms.chapter12.Tree;
import com.pigtom.study.introduction_algorithms.chapter13.AVLTree;
import com.pigtom.study.introduction_algorithms.chapter13.RedBlackTree;

import java.awt.*;

/**
 * @Description
 * @Author tangDunhong@163.com
 * @Date Created in 2018/8/21 8:44
 */
public class MapPanel  extends BasePanel implements Runnable {
    private Tree<Integer> tree;
    private AVLTree treeTool = new AVLTree();
    private Graphics2D g;
    public MapPanel() {
        tree = treeTool.buildSearchTree(20);
    }
    int radius = 15;
    int height = 50;
    @Override
    public void paint(Graphics gp) {
        super.paint(gp);
        g = (Graphics2D) gp;
        handlePosition(tree);
    }

    private void insertNode() {
        Node<Integer> node = new Node<>();
        node.setKey((int) (Math.random() * 1000));
        System.out.println("insert---> " +node.getKey());
        node.setParent(RedBlackTree.NIL);
        node.setLeft(RedBlackTree.NIL);
        node.setRight(RedBlackTree.NIL);
        treeTool.avlInsert(tree, node);
    }
    @Override
    public void run() {
        while (true) {
//            ImageSave.save(saveImage());
//
//            int key = (int) (Math.random() * 30)+1;
//            if (tree.getRoot() == RedBlackTree.NIL) {
//                System.out.println("*_*_*_____delete success_____*_*_*");
//                break;
//            }
//            Node<Integer> node = RedBlackTree.search(key, tree);
//            while (node == RedBlackTree.NIL) {
//                key = (int) (Math.random() * 30)+1;
//                node = RedBlackTree.search(key, tree);
//            }
//            treeTool.delete(tree, node);
//            this.repaint();
//            try {
//                Thread.sleep(1000);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            if(!treeTool.isInvalid(tree))
//                break;
        }
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

    private void handlePosition(Tree<Integer> tree) {
        Node<Integer> node = tree.getRoot();
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
    private void handlePosition(Node<Integer> node) {

        g.setColor(Color.blue);
        String a = "";
        a = node.getKey() == null ? a : node.getKey().toString();
        g.drawString(a, node.x - radius, node.y-5);
        if (node.getColor() == ColorEnum.BLACK) {
            blackNode(g, node.x, node.y);
        } else redNode(g, node.x, node.y);


        if (node != RedBlackTree.NIL) {
            Node<Integer> pa = node.getParent();
            if (pa != RedBlackTree.NIL) {
                g.setColor(Color.black);
                g.drawLine(pa.x, pa.y+2*radius, node.x, node.y);
            }


            int width = node.width / 2;
            Node<Integer> left = node.getLeft();
            left.width = width;
            left.x = node.x - width;
            left.y = height + node.y;
            handlePosition(left);

            Node<Integer> right = node.getRight();
            right.width = width;
            right.x = node.x + width;
            right.y = height + node.y;
            handlePosition(right);
        }
    }
}

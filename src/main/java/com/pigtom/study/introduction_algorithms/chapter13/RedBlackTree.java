package com.pigtom.study.introduction_algorithms.chapter13;

import com.pigtom.study.introduction_algorithms.chapter12.*;
import org.junit.jupiter.api.Test;
import org.omg.PortableInterceptor.INACTIVE;

/**
 * @Description
 * @Author tangDunhong@163.com
 * @Date Created in 2018/8/6 10:25
 */
public class RedBlackTree {

    public void leftRotate(Tree<Integer> tree, Node<Integer> x) {
        Node<Integer> node = x.getRight();
        // y can not be null
        x.setRight(node.getLeft());
        if (node.getLeft() != null) {
            node.getLeft().setParent(x);
        }
        if (x.getParent() == null) {
            tree.setRoot(node);
        } else if (x.getParent().getLeft() == x) {
            x.getParent().setLeft(node);
        }
        else x.getParent().setRight(node);

        node.setParent(x.getParent());
        node.setLeft(x);
        x.setParent(node);
    }

    public void rightRotate(Tree<Integer> tree, Node<Integer> y) {
        Node<Integer> nodeX = y.getLeft();
        y.setRight(nodeX.getRight());
        if (nodeX.getRight() != null) {
            nodeX.getRight().setParent(y);
        }

        if (y.getParent() == null) {
            tree.setRoot(nodeX);
        } else if (y.getParent().getLeft() == y) {
            y.getParent().setLeft(nodeX);
        }
        else y.getParent().setRight(nodeX);
        nodeX.setParent(y.getParent());
        nodeX.setRight(y);
        y.setParent(nodeX);
    }
    @Test
    public void testLeftRotate() {
        Tree<Integer> tree = Node.buildSearchTree(20);
        SearchTree12_1.inorderVisitNoRecurse(tree);

        Node<Integer> node = SearchTree12_3.search(12,tree);
        while (node == null || node.getRight() == null) {
            node = SearchTree12_3.search((int)(Math.random() * 20),tree);
        }
        System.out.println("****" + node.getKey());
        leftRotate(tree, node);
        SearchTree12_1.inorderVisitNoRecurse(tree);
    }

    @Test
    public void testRightRotate() {
        Tree<Integer> tree = Node.buildSearchTree(20);
        SearchTree12_1.inorderVisitNoRecurse(tree);

        Node<Integer> node = SearchTree12_3.search(12, tree);
        while (node == null || node.getLeft() == null) {
            node = SearchTree12_3.search((int)(Math.random() * 20),tree);
        }
        System.out.println("***" + node.getKey());
        rightRotate(tree, node);
        SearchTree12_1.inorderVisitNoRecurse(tree);
    }

    public void insert(Tree<Integer> tree, Node<Integer> node) {
        Node<Integer> y = null;
        Node<Integer> temp = tree.getRoot();
        while (temp != null) {
            y = temp;
            if (node.getKey() < temp.getKey()) {
                temp = temp.getLeft();
            } else temp = temp.getRight();
        }

        if (y == null) {
            tree.setRoot(node);
        } else if (node.getKey() < y.getKey()) {
            y.setLeft(node);
            node.setParent(y);
        } else {
            y.setRight(node);
            node.setParent(y);
        }
        node.setColor(ColorEnum.RED);
    }

    /** 当前结点是红色
     *  1，如果当前结点的父结点是红色，并且如果当前结点的叔叔结点也是红色，
     *      则将当前结点的父结点和叔叔颜色都变成黑色。祖父结点的颜色变成黑色。
     *  2、如果当前结点的叔叔结点是黑色，
     *
     * @param tree
     * @param z
     */
    public void colorFix(Tree tree, Node<Integer> z) {
        // 父结点是红色的
        while (z.getParent() != null && z.getParent().getColor().equals(ColorEnum.RED)) {
            // 因为父结点是红色的，所以祖父一定不为空
            Node<Integer> grandPa = z.getParent().getParent();
            if (grandPa.getLeft() != null ) {

            }
        }
    }

}

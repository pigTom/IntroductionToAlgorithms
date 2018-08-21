package com.pigtom.study.introduction_algorithms.utils;

import com.pigtom.study.introduction_algorithms.chapter12.Tree;
import com.pigtom.study.introduction_algorithms.chapter13.RedBlackTree;

/**
 * @Description
 * @Author tangDunhong@163.com
 * @Date Created in 2018/8/21 8:53
 */
public class View {
    public static void main(String args[]) {
        RedBlackTree tree = new RedBlackTree();
        Tree<Integer> tree1 = tree.buildTree(5);
        MapPanel<Integer> panel = new MapPanel<>(tree1);
        MapFrame app = new MapFrame(panel);
        app.setVisible(true);
    }
}

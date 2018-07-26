/**
 * AUTHOR : Administrator
 * DATE : 2018/7/25
 **/
package com.pigtom.study.introduction_algorithms.chapter12;

public class SearchTree12_3 {
    public void addNode(Node<Integer> node, Node<Integer> head) {
        Node<Integer> pointer = head;
        Node<Integer> parent = null;
        while (pointer != null) {
            parent = pointer;
            // 插入到左子树中
            if (node.key < pointer.key) {
                pointer = pointer.left;
            } else
                pointer = pointer.right;
        }
        node.parent = parent;
        if (parent == null) {
            head = node;// the tree is empty
        } else if (node.key < parent.key) {
            parent.left = node;
        } else
            parent.right = node;
    }
}

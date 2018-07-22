package com.pigtom.study.introduction_algorithms.chapter12;

public class Node<T> {
    Node<T> parent;
    Node<T> left;
    Node<T> right;
    T key;


    /**
     * 建立搜索树：左子树的结点关键字全部不大于父结点，右子树的结点关键字不小于父结点关键字
     *
     * @return 返回根结点
     */
    public static Node<Integer> buildSearchTree(int size) {
        Node<Integer> head = new Node<>();
        head.key = 4;
        for (int i = 0; i < size; i++) {
            Node<Integer> node = new Node<>();
            node.key = (int) (Math.random() * size);
            insertNode(head, node);
        }
        return head;
    }


    /**
     * 插入一个结点，保证原搜索树的特点：左子树的结点关键字全部不大于父结点，右子树的结点关键字不小于父结点关键字
     *
     * @param head
     * @param node
     */
    public static void insertNode(Node<Integer> head, Node<Integer> node) {
        Node<Integer> parent = new Node<>();
        // 将当头结点设为父结点的左结点
        // 让node直接与parent的左结点比较（默认情况下：node.key > parent.key)
        parent.left = head;

        while (true) {
            while (parent.left != null && node.key <= parent.left.key) {
                parent = parent.left;
            }

            if (parent.left == null) {
                parent.left = node;
                node.parent = parent;
                return;
            }

            // 如果父结点的左结点小于要插入的结点的关键字
            // 则让父结点的左结点变成父结点，并尝试将node插入其右子树中
            parent = parent.left;
            // node.key > root.left.key and node.key < root.key
            while (parent.right != null && node.key >= parent.right.key) {
                parent = parent.right;
            }

            // 如果右结点为空
            // 则尝试将要插入的结点放入父结点的右结点中
            if (parent.right == null) {
                parent.right = node;
                node.parent = parent;
                return;
            }

            // 父结点的右结点不为空 且 node.key < parent.right.key
            // 父结点的右结点变成父结点，尝试将node插入新的父结点的左子树中
            parent = parent.right;
        }
    }
}
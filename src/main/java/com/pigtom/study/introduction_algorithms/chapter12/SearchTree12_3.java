/**
 * AUTHOR : Administrator
 * DATE : 2018/7/25
 **/
package com.pigtom.study.introduction_algorithms.chapter12;

import org.junit.jupiter.api.Test;

public class SearchTree12_3 {

    /**
     * 在二叉搜索树中插入一个结点。
     * 不考虑二叉搜索树的平衡问题，要插入的结点为node，
     * 从头结点开始，
     * 1、如果node的关键字小于头结点，插入头结点的左子树中，否则插入到右子树中。
     * 并将左子树或右子树的根结点变成头结点，循环步骤1
     * 2、如果根节点为空，则判断根结点的头结点。如果为空，说明树为空，将node设为头结点。
     * 如果不为空，且node的关键字小于，根结点则将node插入到根结点的左子树中。否则将node插入
     * 到右子树中。
     *
     * @param node
     * @param head
     */
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

    /**
     * 假设树中的关键字各不相同，根据关键字删除结点。
     * 有三种情况：（将要被删除的结点记作y，y的右孩子记作yRight，左孩子记作yLeft,父结点为yP）
     * 1、如果y结点为叶结点，则直接删除。
     * 2、如果y结点只有一个孩子，那么将yRight或yLeft代替y的位置
     * 3、如果y结点有两个孩子，在y的右子树中找到一个最小的结点(这个最小的结点也比y结点大），即子树中最靠左的结点，记作x
     * x一定没有左结点，将x结点的右结点记作xRight,x的父结点记作xP,将xp.left = xRight,
     * yP.child = x, x.left = y.left and x.right = x.right
     *
     * @param key
     * @param head
     */
    public static Node<Integer> deleteNode(int key, Node<Integer> head) {
        // find node
        Node<Integer> node = head;
        while (node != null) {
            if (key < node.key) {
                node = node.left;
            } else if (key > node.key) {
                node = node.right;
            } else {
                break;
            }
        }

        if (node == null) { // no node found
            return head;
        }


        Node<Integer> grandPa = node.parent;
        Node<Integer> son = node;
        if (node.left == null) {
            node = node.right;
        } else if (node.right == null) {
            node = node.left;
        } else {
            // succeed of node, succeed has no left no
            // put the right node of succeed to be the left child of its Pa
            // TODO 有问题的代码
            // succeed may the right son of node
            Node<Integer> succeed = SearchTree12_2.treeDecessor(node);
            Node<Integer> sucPa = succeed.parent;
            sucPa.left = succeed.right;
            // there two ways to replace succeed with node
            // 1 : value exchange
            // 2 : node exchange
            // 3 : here I use node exchange
            // lef succeed to replace node
            succeed.parent = node.parent;
            succeed.right = node.right;
            succeed.left = node.left;
            node = succeed;
        }
        if (node != null) {
            node.parent = grandPa;
        }
        if (grandPa == null) {
            head = node;
        } else {
            if (grandPa.left == son) {
                grandPa.left = node;
            } else {
                grandPa.right = node;
            }
        }

        return head;
    }

    @Test
    public void testRemove() {
        for (int i = 0; i < 6; i++) {
            Node<Integer> head = Node.buildSearchTree(30);
            SearchTree12_1.inorderVisitNoRecurse(head);
            int num = (int) (Math.random() * 30);
            System.out.println("*******" + num + "*******");
            deleteNode(num, head);
            SearchTree12_1.inorderVisitNoRecurse(head);
            System.out.println("\r");
        }
    }

}

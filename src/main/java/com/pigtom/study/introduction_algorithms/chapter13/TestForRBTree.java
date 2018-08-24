package com.pigtom.study.introduction_algorithms.chapter13;

import com.pigtom.study.introduction_algorithms.chapter12.Node;
import com.pigtom.study.introduction_algorithms.chapter12.Tree;
import org.junit.jupiter.api.Test;

public class TestForRBTree {
    /**
     * 13-1(待久动态集合） 有时在算法的执行过程中，我们会发现在更新一个动态集合时，需要维护
     * 其过去的版本。我们称这样的集合是持久的。实现持久集合的一种方法是每当该集合被修改时，
     * 就将其完整的复制下来。但是这样会降低一个程序的执行速度，而且占用过多的空间。有时候，
     * 我们可以做的更好一些。
     * 考虑一个有INSERT, DELETE. SEARCH操作的持久集合S，我们用二叉树来实现，对于每个
     * 集合的版本都维护一个不同的根。
     * 假设树中每个结点都有属性key, left和right，但是没有parent
     */
    // a 对于一般的二叉搜索树，为插入一个关键字k或删除一个结点y，需要改变哪些结点


    Tree<Integer> persistentTreeInsert(Tree<Integer> tree, int key) {
        Tree<Integer> newTree = new Tree<>();
        Node<Integer> node = tree.getRoot();
        Node<Integer> pa = new Node<>();
        newTree.setRoot(pa);
        if (node == null) {
            pa.setKey(key);
            return newTree;
        }
        // copy the root node to new root
        pa.setKey(node.getKey());
        pa.setRight(node.getRight());
        pa.setLeft(node.getLeft());
        if (key < node.getKey()) {
            node = node.getLeft();
        } else {
            node = node.getRight();
        }
        while (node != null) {
            // copy node
            Node<Integer> newNode = new Node<>();
            newNode.setKey(node.getKey());
            newNode.setLeft(node.getLeft());
            newNode.setRight(node.getRight());
            // connect new node and the pa of new node
            if (pa.getLeft() == node) {
                pa.setLeft(newNode);
            } else pa.setRight(newNode);
            pa = newNode;
            if (key < node.getKey()) {
                node = node.getLeft();
            } else node = node.getRight();
        }

        Node<Integer> addNode = new Node<>();
        addNode.setKey(key);
        // node == null
        if (key < pa.getKey()) {
            pa.setLeft(addNode);
        } else pa.setRight(addNode);
        return newTree;
    }

    @Test
    void testPersistentTreeInsert() {
        Tree<Integer> tree = Node.buildSearchTree(10);
        Node.inorderVisitRecurse(tree);
        int key = (int)(Math.random() * 10);
        System.out.println("-- new key --" + key);
        Tree<Integer> newTree = persistentTreeInsert(tree, key);
        System.out.println("----old tree---");
        Node.inorderVisitRecurse(tree);
        System.out.println("----new tree---");
        Node.inorderVisitRecurse(newTree);
    }

    void persistentTreeDelete(Tree<Integer> tree, int key) {

    }
}


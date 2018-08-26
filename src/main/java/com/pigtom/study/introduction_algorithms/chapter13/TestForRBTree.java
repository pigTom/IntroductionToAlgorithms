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
        int size = 20;
        Tree<Integer> tree = Node.buildSearchTree(size);
        System.out.println("-- origin tree --");
        Node.inorderVisitRecurse(tree);
        int key = (int)(Math.random() * size);
        System.out.println("-- new key --" + key);
        Tree<Integer> newTree = persistentTreeInsert(tree, key);
        System.out.println("----origin tree---");
        Node.inorderVisitRecurse(tree);
        System.out.println("----inserted tree---");
        Node.inorderVisitRecurse(newTree);
        System.out.println("----delete " + key + "----");
        Tree<Integer> deleteTree = persistentTreeDelete(tree, key);
        System.out.println("----origin tree---");
        Node.inorderVisitRecurse(tree);
        System.out.println("----deleted tree---");
        Node.inorderVisitRecurse(deleteTree);
    }

    Tree<Integer> persistentTreeDelete(final Tree<Integer> tree, int key) {
        if (tree.getRoot() == null) {
            return tree;
        }
        Tree<Integer> newTree = new Tree<>();
        Node<Integer> oldNode = tree.getRoot();
        Node<Integer> pa = new Node<>();
        newTree.setRoot(pa);

        pa.setKey(oldNode.getKey());
        pa.setLeft(oldNode.getLeft());
        pa.setRight(oldNode.getRight());
        // step 1: find the node
        if (key < oldNode.getKey()) {
            oldNode = oldNode.getLeft();
        } else if (key > oldNode.getKey()) {
            oldNode = oldNode.getRight();
        } else {
            fixDeletedNode(newTree, pa, null);
            return newTree;
        }
        Node<Integer> temp = pa;
        while (oldNode != null) {
            pa = temp;
            temp = new Node<>();
            temp.setKey(oldNode.getKey());
            temp.setLeft(oldNode.getLeft());
            temp.setRight(oldNode.getRight());
            // connect new copied node to its parent
            if (oldNode == pa.getLeft()) {
                pa.setLeft(temp);
            } else pa.setRight(temp);


            if (key < oldNode.getKey()) {
                oldNode = oldNode.getLeft();
            } else if (key > oldNode.getKey()) {
                oldNode = oldNode.getRight();
            } else {
                // find the node to be deleted
                break;
            }
        }
        if (oldNode != null) {
            fixDeletedNode(newTree, temp, pa);
            return newTree;
        } else {
            System.out.println("not found");
            return tree;
        }
    }

    public void fixDeletedNode(final Tree<Integer> newTree, final Node<Integer> deletedNode, Node<Integer> pa) {
        Node<Integer> minimum = null;
        // test if deleteNode has right node
        if (deletedNode.getRight() != null) {
            // find the minimum node in tree of right node of delete node
            Node<Integer> oldNode = deletedNode.getRight();
            Node<Integer> newNode = new Node<>();
            newNode.setKey(oldNode.getKey());
            newNode.setLeft(oldNode.getLeft());
            newNode.setRight(oldNode.getRight());
            deletedNode.setRight(newNode);

            Node<Integer> minimumPa = deletedNode;
            while (oldNode.getLeft() != null) {
                minimumPa = newNode;
                newNode = new Node<>();
                newNode.setKey(oldNode.getLeft().getKey());
                newNode.setLeft(oldNode.getLeft().getLeft());
                newNode.setRight(oldNode.getLeft().getRight());
                minimumPa.setLeft(newNode);
                oldNode = oldNode.getLeft();
            }
            // node minimum is the minimum node of the right tree
            // there will be two cases
            // case 1: the right node of deleteNode is not the minimum node of right tree
            if (minimumPa != deletedNode) {
                minimum = minimumPa.getLeft();
                // minimum node to replace deleted node
                minimumPa.setLeft(null);
            } else {
                minimum = minimumPa.getRight();
                deletedNode.setRight(minimum.getRight());
            }
            minimum.setLeft(deletedNode.getLeft());
            minimum.setRight(deletedNode.getRight());
        }
        // minimum has replaced the deleted node
        if (pa == null) {
            newTree.setRoot(minimum);
        }
        else if (pa.getLeft() == deletedNode) {
           pa.setLeft(minimum);
        } else pa.setRight(minimum);
    }
}


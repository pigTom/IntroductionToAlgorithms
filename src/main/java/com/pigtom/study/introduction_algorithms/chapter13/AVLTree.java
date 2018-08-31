package com.pigtom.study.introduction_algorithms.chapter13;

import com.pigtom.study.introduction_algorithms.chapter12.Node;
import com.pigtom.study.introduction_algorithms.chapter12.Tree;
import org.junit.jupiter.api.Test;

import static com.pigtom.study.introduction_algorithms.chapter13.RedBlackTree.NIL;
import static com.pigtom.study.introduction_algorithms.chapter13.RedBlackTree.insertSearchTree;

/**
 * AVL树是一种高度平衡的二叉搜索树，对每个结点x，x的左子树和右子树的高度来去最多相差1.
 * 要实现一颗AVL树，需要在每个结点中，加入一个额外的属性:x.h为结点x的高度。
 */
public class AVLTree {

    /**
     * 将node插入到AVL树中，node已被赋值
     *
     * @param tree 被插入的树
     * @param node 将要插入的结点
     */
    public void avlInsert(Tree<Integer> tree, Node<Integer> node) {
        // insert node into the tree, keep search tree rule
        insertSearchTree(tree, node);
        Node<Integer> parent = node.getParent();
        // 父结点不为空且子结点高差不超过1
        while (parent != NIL) {
            int h1 = parent.getLeft().h;
            int h2 = parent.getRight().h;
            parent.h = h1 > h2 ? h1+1 : h2+1;
            // 如果高差超过1，则应该修正（进入replace)
            if (Math.abs(h1 - h2) > 1) {
                break;
            }
            parent = parent.getParent();
        }
        if (parent != NIL) {
            replace(tree, parent);
        }
    }

    public void replace(Tree<Integer> tree, Node<Integer> x) {
        Node<Integer> left = x.getLeft();
        Node<Integer> right = x.getRight();
        // 左子树比右子树高2
        if (left.h == right.h+2) {
            // 左子树比右子树高2，所以左子树一定不为NIL（left != NIL)
            // 如果left.left.h < left.right.h左转将结点移到左子树
            if (left.getLeft().h < left.getRight().h) {
                // 处理各个结点的高
                // x的高度不变
                // left.right 的高度应该增加1，即变成为left.h一样
                // left 结点的高度减小1
                left.getRight().h = left.h;
                left.h = left.h - 1;

                RedBlackTree.leftRotate(tree, left);
            }
            // 先处理各个结点的高度，然后进行右转
            x.h = x.h - 2;// x通过右转其高度降低两个
            // x.getLeft 来取代x，其高度不变
            RedBlackTree.rightRotate(tree, x);
        }
        // 当右结点比左结点高2时，与上述相反
        else if (left.h + 2 == right.h) {
            if (right.getLeft().h > right.getRight().h) {
                right.getLeft().h = right.h;
                right.h = right.h - 1;
                RedBlackTree.rightRotate(tree, right);
            }
            x.h = x.h - 2;
            RedBlackTree.leftRotate(tree, x);
        }
    }

    public Tree<Integer> buildSearchTree(int i) {
        Tree<Integer> tree = new Tree<>();
        tree.setRoot(NIL);
        for (int j = 0; j < i; j++) {
            Node<Integer> node = new Node<>();
            int num = (int) (Math.random() * i + 1);
            node.setKey(num);
            node.setParent(NIL);
            node.setRight(NIL);
            node.setLeft(NIL);
            node.h = 1;
            avlInsert(tree, node);
        }
        return tree;
    }
    @Test
    void testBuildTree() {
        Tree<Integer> tree = buildSearchTree(10);
        RedBlackTree.inorderVisit(tree);
    }

}

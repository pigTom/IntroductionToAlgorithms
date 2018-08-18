package com.pigtom.study.introduction_algorithms.chapter13;

import com.pigtom.study.introduction_algorithms.chapter12.ColorEnum;
import com.pigtom.study.introduction_algorithms.chapter12.Node;
import com.pigtom.study.introduction_algorithms.chapter12.Tree;
import org.junit.jupiter.api.Test;

/**
 * @Description
 * @Author tangDunhong@163.com
 * @Date Created in 2018/8/6 10:25
 */
public class RedBlackTree {

    private Node<Integer> NIL = new Node<>();

    public void leftRotate(Tree<Integer> tree, Node<Integer> x) {
        Node<Integer> node = x.getRight();
        // y can not be NIL
        x.setRight(node.getLeft());
        node.getLeft().setParent(x);
        replace(tree, x, node);
        node.setLeft(x);
        x.setParent(node);
    }

    public void rightRotate(Tree<Integer> tree, Node<Integer> x) {
        Node<Integer> nodeX = x.getLeft();
        x.setLeft(nodeX.getRight());
        nodeX.getRight().setParent(x);

        replace(tree, x, nodeX);
        nodeX.setRight(x);
        x.setParent(nodeX);
    }

    private Tree<Integer> buildSearchTree(int i) {
        Tree<Integer> tree = new Tree<>();
        tree.setRoot(NIL);
        for (int j = 0; j < i; j++) {
            Node<Integer> node = new Node<>();
            int num = (int) (Math.random() * i + 1);
            node.setKey(num);
            node.setParent(NIL);
            node.setRight(NIL);
            node.setLeft(NIL);
            insertSearchTree(tree, node);
        }
        return tree;
    }

    public void insertSearchTree(Tree<Integer> tree, Node<Integer> node) {
        Node<Integer> p = NIL;
        Node<Integer> n = tree.getRoot();
        while (n != NIL) {
            p = n;
            if (node.getKey() < n.getKey()) {
                n = n.getLeft();
            } else
                n = n.getRight();
        }

        node.setParent(p);
        if (p == NIL) { // Tree is empty
            tree.setRoot(node);
        } else if (node.getKey() < p.getKey()) {
            p.setLeft(node);
        } else {
            p.setRight(node);
        }
    }

    public Node<Integer> search(int key, Tree<Integer> tree) {
        // find node
        Node<Integer> node = tree.getRoot();
        while (node != NIL) {
            if (key < node.getKey()) {
                node = node.getLeft();
            } else if (key > node.getKey()) {
                node = node.getRight();
            } else {
                break;
            }
        }
        return node;
    }

    @Test
    void testBuildSearchTree() {
        Tree<Integer> tree = buildSearchTree(20);
        inorderVisit(tree);
    }

    @Test
    public void testLeftRotate() {
        Tree<Integer> tree = buildSearchTree(20);
        inorderVisit(tree);

        Node<Integer> node = search(12, tree);
        while (node == NIL || node.getRight() == NIL) {
            node = search((int) (Math.random() * 20), tree);
        }
        System.out.println("****" + node.getKey());
        leftRotate(tree, node);
//        node = search((int) (Math.random() * 20), tree);
//        while (node == NIL || node.getRight() == NIL) {
//            node = search((int) (Math.random() * 20), tree);
//        }
//        System.out.println("****" + node.getKey());
//        leftRotate(tree, node);
        inorderVisit(tree);
    }

    @Test
    public void testRightRotate() {
        Tree<Integer> tree = buildSearchTree(20);
        inorderVisit(tree);

        Node<Integer> node = search(12, tree);
        while (node == NIL || node.getLeft() == NIL) {
            node = search((int) (Math.random() * 20), tree);
        }
        System.out.println("***" + node.getKey());
        rightRotate(tree, node);
        inorderVisit(tree);
    }


    public void insert(Tree<Integer> tree, Node<Integer> node) {
        Node<Integer> y = NIL;
        Node<Integer> temp = tree.getRoot();
        while (temp != NIL) {
            y = temp;
            if (node.getKey() < temp.getKey()) {
                temp = temp.getLeft();
            } else temp = temp.getRight();
        }

        if (y == NIL) {
            tree.setRoot(node);
            node.setColor(ColorEnum.BLACK);
            return;
        } else if (node.getKey() < y.getKey()) {
            y.setLeft(node);
            node.setParent(y);
            node.setColor(ColorEnum.RED);
        } else {
            y.setRight(node);
            node.setParent(y);
            node.setColor(ColorEnum.RED);
        }
        colorFix(tree, node);
    }

    /**
     * 当前结点是红色
     * 1，如果当前结点的父结点是红色，并且如果当前结点的叔叔结点也是红色，
     * 则将当前结点的父结点和叔叔颜色都变成黑色。祖父结点的颜色变成黑色。
     * 2、如果当前结点的叔叔结点是黑色，
     *
     * @param tree
     * @param z
     */
    public void colorFix(Tree<Integer> tree, Node<Integer> z) {
        // 父结点是红色的
        while (z.getParent() != NIL && z.getParent().getColor().equals(ColorEnum.RED)) {
            // 因为父结点是红色的，所以祖父一定不为空
            Node<Integer> grandPa = z.getParent().getParent();

            // 父结点是左结点
            if (grandPa.getLeft() == z.getParent()) {
                // 叔叔是红色的
                if (grandPa.getRight() != NIL && grandPa.getRight().getColor().equals(ColorEnum.RED)) {
                    grandPa.setColor(ColorEnum.RED);
                    grandPa.getRight().setColor(ColorEnum.BLACK);
                    grandPa.getLeft().setColor(ColorEnum.BLACK);
                    z = grandPa;
                    // 叔叔是黑色的且z是左结点
                } else if (z.getParent().getLeft() == z) {
                    z.getParent().setColor(ColorEnum.BLACK);
                    grandPa.setColor(ColorEnum.RED);
                    z = z.getParent();
                    rightRotate(tree, z.getParent());
                } else {
                    // 叔叔是黑色的且z是右结点
                    z = z.getParent();
                    leftRotate(tree, z);
                }
            } else {
                // 父结点是右结点

                // 叔叔结点是红色的
                if (grandPa.getLeft() != NIL && grandPa.getLeft().getColor().equals(ColorEnum.RED)) {
                    grandPa.setColor(ColorEnum.RED);
                    grandPa.getLeft().setColor(ColorEnum.BLACK);
                    grandPa.getRight().setColor(ColorEnum.BLACK);
                    z = grandPa;
                } else if (z.getParent().getRight() == z) {
                    // z是右结点
                    z.getParent().setColor(ColorEnum.BLACK);
                    grandPa.setColor(ColorEnum.RED);
                    z = z.getParent();
                    leftRotate(tree, z.getParent());
                } else {
                    z = z.getParent();
                    rightRotate(tree, z);
                }
            }
        }
        // 有几种情况(z是红色的)
        // 1. z.getParent.color == black
        // 2. z.getParent == NIL // z.is root
        tree.getRoot().setColor(ColorEnum.BLACK);
    }

    Tree<Integer> buildTree(int i) {
        Tree<Integer> tree = new Tree<>();
        tree.setRoot(NIL);
        for (int j = 0; j < i; j++) {
            Node<Integer> node = new Node<>();
            int num = (int) (Math.random() * i + 1);
            node.setKey(num);
            node.setParent(NIL);
            node.setRight(NIL);
            node.setLeft(NIL);
            insert(tree, node);
        }
        return tree;
    }

    /**
     * 用v来代替u，以u为根的树会全部被替换成以v为根的树
     *
     * @param tree 树根
     * @param u    要被替换的结点
     * @param v    要替换的结点
     */
    private void replace(Tree<Integer> tree, Node<Integer> u, Node<Integer> v) {
        // u is root node
        if (u.getParent() == NIL) {
            tree.setRoot(v);
        } else if (u.getParent().getLeft() == u) {
            // u is the left node
            u.getParent().setLeft(v);
        } else u.getParent().setRight(v);
        v.setParent(u.getParent());
    }

    public void delete(Tree<Integer> tree, Node<Integer> z) {
        if (z == NIL) {
            return;
        }
        // 三种情况
        Node<Integer> x;
        Node<Integer> y;
        ColorEnum yOriginColor = z.getColor();
        if (z.getLeft() == NIL) {
            // 如果左结点为空,用右结点代替z,因为z没有左结点所以，不用处理z的左结点
            x = z.getRight();
            replace(tree, z, x);
        } else if (z.getRight() == NIL) {
            // 如果右结点为农药，用左结点代替z，因为z没有右结点，所以，不用处理z的右结点
            x = z.getLeft();
            replace(tree, z, x);
        } else {
            // 如果z的左右结点都不为空，则存在两种情况：
            // 1. z的后继为z的右节点，或者z的后继不是z的右节点。
            // 先用y记录 z的后继
//            y = SearchTree12_2.treeDecessor(z);
            y = findMinimun(z.getRight());
            yOriginColor = y.getColor();
            x = y.getRight();
            if (y.getParent() != z) {
                // 如果y不是z的右结点，则还需要用y的右结点来代替y
                // y是没有左结点的，所以直接替换
                replace(tree, y, x);
                // 将z的右结点赋给y的右结点
                y.setRight(z.getRight());
                y.getRight().setParent(y);
            } else {
                x.setParent(y);
            }
            // 将y代替z
            replace(tree, z, y);
            // 将z的左结点给y
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
            y.setColor(z.getColor());
        }
        if (yOriginColor.equals(ColorEnum.BLACK)) {
            deleteColorFix(tree, x);
        }
    }

    Node<Integer> findMinimun(Node<Integer> node) {
        Node p = node;
        while (node != NIL) {
            p = node;
            node = node.getLeft();
        }
        return p;
    }


    void deleteColorFix(Tree<Integer> tree, Node<Integer> x) {
        while (x.getColor().equals(ColorEnum.BLACK) && x != tree.getRoot()) {
            Node<Integer> brother = x.getParent().getRight();
            if (x.getParent().getLeft() == x) {
                // x为左结点,x为黑色说明x一定为哨兵
                // 且现有的兄弟结点是一定是黑结点
                // x 变成 x的兄弟然后左旋，有两种情况：
                // 1. 如果x的父亲是红色，那么旋转之后树就平衡了，因为一在x是黑色
                // 2. 如果x的父亲是黑色，那么现是不平衡的，需要再往上走
                if (brother.getColor().equals(ColorEnum.BLACK)) {
                    // 兄弟结点的颜色是黑色的
                    // 将父结点的颜色赋给兄弟结点
                    brother.setColor(x.getParent().getColor());
                    x.getParent().setColor(ColorEnum.RED);
                    x = brother;
                    leftRotate(tree, x.getParent());
                } else {
                    // 兄弟结点的颜色是红色的，那么父结点的颜色一定是黑色的
                    // 将父结点的颜色变成红色，兄弟结点的颜色变成黑色
                    // 左旋
                    brother.setColor(ColorEnum.BLACK);
                    brother.getParent().setColor(ColorEnum.BLACK);
                    x = brother;
                    leftRotate(tree, brother.getParent());
                }
            }

            // x是右结点，操作与上面相反
            else {
                if (brother.getColor().equals(ColorEnum.BLACK)) {
                    // 兄弟结点的颜色是黑色的
                    // 将父结点的颜色赋给兄弟结点
                    brother.setColor(x.getParent().getColor());
                    x.getParent().setColor(ColorEnum.RED);
                    x = brother;
                    rightRotate(tree, x.getParent());
                } else {
                    // 兄弟结点的颜色是红色的，那么父结点的颜色一定是黑色的
                    // 将父结点的颜色变成红色，兄弟结点的颜色变成黑色
                    // 右旋
                    brother.setColor(ColorEnum.BLACK);
                    brother.getParent().setColor(ColorEnum.BLACK);
                    x = brother;
                    rightRotate(tree, brother.getParent());
                }

            }
        }
        x.setColor(ColorEnum.BLACK);
    }

    @Test
    public void testBuild() {
        int num = 10;
        Tree<Integer> tree = buildTree(num);
//        i(tree);
    }

    @Test
    public void testDelete() {
        NIL.setColor(ColorEnum.BLACK);
        for (int i = 0; i < 20; i++) {
            int size = 10;
            Tree<Integer> tree = buildTree(size);
            inorderVisit(tree);
            int num = (int) (Math.random() * size);
            System.out.println("*******" + num + "*******");
            Node<Integer> node = search(num, tree);
            delete(tree, node);
            inorderVisit(tree);
            System.out.println("\r");
        }
    }

    int i = 0;

    public void inorderVisit(Tree<Integer> tree) {
        i = 0;
        if (tree.getRoot() != NIL) {
            inorderVisitRecurse(tree.getRoot());
        }
        System.out.println("cont : " + i);
    }

    private void inorderVisitRecurse(Node<Integer> node) {
        if (node.getLeft() != NIL) {
            inorderVisitRecurse(node.getLeft());
        }
        i++;
        System.out.println(node.getKey());
        if (node.getRight() != NIL) {
            inorderVisitRecurse(node.getRight());
        }
    }
}



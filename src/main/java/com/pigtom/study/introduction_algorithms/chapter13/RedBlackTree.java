package com.pigtom.study.introduction_algorithms.chapter13;

import com.pigtom.study.introduction_algorithms.chapter12.ColorEnum;
import com.pigtom.study.introduction_algorithms.chapter12.Node;
import com.pigtom.study.introduction_algorithms.chapter12.Tree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @Description
 * @Author tangDunhong@163.com
 * @Date Created in 2018/8/6 10:25
 */
public class RedBlackTree {

    private static Node<Integer> NIL = new Node<>();
    static {
        NIL.setColor(ColorEnum.RED);
    }
    public void leftRotate(Tree<Integer> tree, Node<Integer> x) {
        Node<Integer> node = x.getRight();
        // y can not be NIL
        x.setRight(node.getLeft());
        Assertions.assertNotEquals(node, NIL, "wrong");
        if (node != NIL) {
            node.getLeft().setParent(x);
        }
        replace(tree, x, node);
        node.setLeft(x);
        x.setParent(node);
    }

    public void rightRotate(Tree<Integer> tree, Node<Integer> x) {
        Node<Integer> nodeX = x.getLeft();
        x.setLeft(nodeX.getRight());
        Assertions.assertNotEquals(nodeX, NIL, "wrong");
        if (nodeX != NIL) {
            nodeX.getRight().setParent(x);
        }
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
     * @param u    要被替换的结点 (u不能为NIL)
     * @param v    要替换的结点 （v可能是NIL)
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
    int delete = 0;
    public void delete(Tree<Integer> tree, Node<Integer> z) {
//        NIL.setParent(null);
        if (z == NIL) {
            return;
        }
        delete++;
        // 三种情况
        Node<Integer> x;
        Node<Integer> y;
        ColorEnum yOriginColor = z.getColor();
        if (z.getLeft() == NIL) {
            // 如果左结点为空,用右结点代替z,因为z没有左结点所以，不用处理z的左结点
            x = z.getRight();
            replace(tree, z, x);
        } else if (z.getRight() == NIL) {
            // 如果右结点为空，用左结点代替z，因为z没有右结点，所以，不用处理z的右结点
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
        while (x != tree.getRoot() && x.getColor().equals(ColorEnum.BLACK)) {
            if (x.getParent().getLeft() == x) {
                Node<Integer> brother = x.getParent().getRight();

                // case 1
                if (brother.getColor().equals(ColorEnum.RED)) {
                    // 兄弟结点的颜色是红色的，那么父结点的颜色一定是黑色的
                    // 将父结点的颜色变成红色，兄弟结点的颜色变成黑色
                    // 左旋
                    brother.setColor(ColorEnum.BLACK);
                    x.getParent().setColor(ColorEnum.RED);
                    leftRotate(tree, brother.getParent());
                    brother = x.getParent().getRight();
                    // goto case 2
                }
                // case 2
                // 1 all black
                if (brother.getRight().getColor().equals(ColorEnum.BLACK) &&
                        brother.getLeft().getColor().equals(ColorEnum.BLACK)) {
                    brother.setColor(ColorEnum.RED);
                    x = x.getParent();
                    continue;
                }
//                // 2 all red
//                else if (brother.getLeft().getColor().equals(ColorEnum.RED) &&
//                        brother.getRight().getColor().equals(ColorEnum.RED)) {
//                    brother.setColor(x.getParent().getColor());
//                    x.getParent().setColor(ColorEnum.BLACK);
//                    brother.getRight().setColor(ColorEnum.BLACK);
//                    x = x.getParent();
//                    break;
//                }
                // 2 left red and right black
                else if (brother.getRight().getColor().equals(ColorEnum.BLACK)) {
                    brother.setColor(ColorEnum.RED);
                    brother.getLeft().setColor(ColorEnum.BLACK);
                    rightRotate(tree, brother);
                    brother = x.getParent().getRight();
                    // goto 4
                }
                // 4 right red
                if (brother == NIL) {
                    Assertions.fail("brother can not be NIL");
                }
                brother.setColor(x.getParent().getColor());
                x.getParent().setColor(ColorEnum.BLACK);
                brother.getRight().setColor(ColorEnum.BLACK);
                leftRotate(tree, x.getParent());
                x = tree.getRoot();
                break;
            }

            // x是右结点，操作与上面相反
            else {
//                Node<Integer> brother = x.getParent().getLeft();
//                // case 3
//                if (brother.getColor().equals(ColorEnum.RED)) {
//                    // 兄弟结点的颜色是红色的，那么父结点的颜色一定是黑色的
//                    // 将父结点的颜色变成红色，兄弟结点的颜色变成黑色
//                    // 右旋
//                    brother.setColor(ColorEnum.BLACK);
//                    x.getParent().setColor(ColorEnum.RED);
//                    rightRotate(tree, x.getParent());
//                    brother = x.getParent().getLeft();
//                    // goto case 4
//                }
//                // case 4
//                // 1 all black
//                if (brother.getRight().getColor().equals(ColorEnum.BLACK) &&
//                        brother.getLeft().getColor().equals(ColorEnum.BLACK)) {
//                    brother.setColor(ColorEnum.RED);
//                    x = x.getParent();
//                    continue;
//                }
//                // 3 right red and left black
//                else if (brother.getLeft().getColor().equals(ColorEnum.BLACK)) {
//                    brother.setColor(ColorEnum.RED);
//                    brother.getRight().setColor(ColorEnum.BLACK);
//                    leftRotate(tree, brother);
//                    brother = x.getParent().getLeft();
//                    // goto 4
//                }
//                // 4 left red
//                brother.setColor(x.getParent().getColor());
//                x.getParent().setColor(ColorEnum.BLACK);
//                brother.getLeft().setColor(ColorEnum.BLACK);
//                rightRotate(tree, x.getParent());
//                x = tree.getRoot();
//                break;
                Node<Integer> brother = x.getParent().getLeft();
                // case 1
                if (brother.getColor().equals(ColorEnum.RED)) {
                    // 兄弟结点的颜色是红色的，那么父结点的颜色一定是黑色的
                    // 将父结点的颜色变成红色，兄弟结点的颜色变成黑色
                    // 左旋
                    brother.setColor(ColorEnum.BLACK);
                    x.getParent().setColor(ColorEnum.RED);
                    rightRotate(tree, brother.getParent());
                    brother = x.getParent().getLeft();
                    // goto case 2
                }
                // case 2
                // 1 all black
                if (brother.getLeft().getColor().equals(ColorEnum.BLACK) &&
                        brother.getRight().getColor().equals(ColorEnum.BLACK)) {
                    brother.setColor(ColorEnum.RED);
                    x = x.getParent();
                    continue;
                }
                // 2 left red and right black
                else if (brother.getLeft().getColor().equals(ColorEnum.BLACK)) {
                    brother.setColor(ColorEnum.RED);
                    brother.getRight().setColor(ColorEnum.BLACK);
                    leftRotate(tree, brother);
                    brother = x.getParent().getLeft();
                    // goto 4
                }
                // 4 right red
                if (brother == NIL) {
                    Assertions.fail("brother can not be NIL");
                }
                brother.setColor(x.getParent().getColor());
                x.getParent().setColor(ColorEnum.BLACK);
                brother.getLeft().setColor(ColorEnum.BLACK);
                rightRotate(tree, x.getParent());
                x = tree.getRoot();
                break;
            }
        }
        x.setColor(ColorEnum.BLACK);
    }

    @Test
    public void testBuild() {
        int num = 10;
        Tree<Integer> tree = buildTree(num);
        inorderVisit(tree);
        testRBTree(tree);
    }

    private String message = "不满足性质5：对每个结点，从该结点到其所有后代的简单路径上，均包含相同数目的黑色结点。";
    void testRBTree(Tree<Integer> tree) {
        Assertions.assertEquals(tree.getRoot().getColor(), ColorEnum.BLACK, "不满足性质2: 根必须是黑色的");
        if (tree.getRoot() != NIL) {
            int i = countBlackNode(tree.getRoot().getLeft());
            int j = countBlackNode(tree.getRoot().getRight());
            Assertions.assertEquals(i,j ,message);
        }
    }
    public void testRBTreeColor(Node<Integer> node) {
        boolean flag = true;
        if (node.getColor() == ColorEnum.RED) {
            flag = node.getRight().getColor() == ColorEnum.BLACK
                    && node.getLeft().getColor() == ColorEnum.BLACK;
        }
        Assertions.assertTrue(flag, "不满足性质4:如果一个结点是红色的，那么它的每个孩子结点都是黑色的");
    }

    int countBlackNode(Node<Integer> node) {
        if (node == NIL) {
            return 1;
        } else {
            int i = countBlackNode(node.getLeft());
            int j = countBlackNode(node.getRight());
            Assertions.assertEquals(i, j, message);
            if (node.getColor() == ColorEnum.BLACK) {
                return i + 1;
            }
            testRBTreeColor(node);
            return i;
        }
    }

    @Test
    public void testDelete() {
        NIL.setColor(ColorEnum.BLACK);
        for (int i = 0; i < 1; i++) {
            int size = 50;
            Tree<Integer> tree = buildTree(size);
            System.out.println("start-->");
            inorderVisit(tree);
            for (int j = 0; j <= size; j++) {

                Node<Integer> node = NIL;
                System.out.println("*******" + j + "*******");
                node = search(j, tree);
                delete(tree, node);
            }
            i = inorderVisit(tree);
            System.out.println("end-->");
            System.out.println("delete: " + delete);
            Assertions.assertEquals(delete, (size - i), "error");
            System.out.println("\r");
        }
    }

    int i = 0;

    public int inorderVisit(Tree<Integer> tree) {
        i = 0;
        if (tree.getRoot() != NIL) {
            inorderVisitRecurse(tree.getRoot());
        }
        System.out.println("cont : " + i);
        return i;
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



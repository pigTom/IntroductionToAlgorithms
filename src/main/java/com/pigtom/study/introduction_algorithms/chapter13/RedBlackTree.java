package com.pigtom.study.introduction_algorithms.chapter13;

import com.pigtom.study.introduction_algorithms.chapter12.ColorEnum;
import com.pigtom.study.introduction_algorithms.chapter12.Node;
import com.pigtom.study.introduction_algorithms.chapter12.Tree;
import com.pigtom.study.introduction_algorithms.utils.ImageSave;
import com.pigtom.study.introduction_algorithms.utils.MapPanel;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;


/**
 * @Description
 * @Author tangDunhong@163.com
 * @Date Created in 2018/8/6 10:25
 */
@Service
public class RedBlackTree {
    Logger log = Logger.getLogger(RedBlackTree.class);
    public static Node<Integer> NIL = new Node<>();
    private MapPanel mapPanel;

    public void setMapPanel(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
    }

    static {
        NIL.setColor(ColorEnum.BLACK);
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
            y = temp;  // y 指向temp的父结点
            if (node.getKey() < temp.getKey()) {
                temp = temp.getLeft();
            } else temp = temp.getRight();
        }

        if (y == NIL) { // 如果y是NIL，说明tree is empty
            tree.setRoot(node);
            node.setColor(ColorEnum.BLACK);
            return; // 不用颜色处理
        } else if (node.getKey() < y.getKey()) { // node的key小于y的key，将node插入到y的左结点，因为node的左结点一定是NIL
            y.setLeft(node);
            node.setParent(y);
            node.setColor(ColorEnum.RED);
        } else { // node的key大于或等于y的key，将node插入到y的右结点，y的右结点肯定是NIL
            y.setRight(node);
            node.setParent(y);
            node.setColor(ColorEnum.RED);
        }
        colorFix(tree, node);
    }
    public static void print(Object o){
        System.out.println(o.toString());
    }
    /**
     * 当前结点是红色
     * 1，如果当前结点的父结点是红色，并且如果当前结点的叔叔结点也是红色，
     * 则将当前结点的父结点和叔叔颜色都变成黑色。祖父结点的颜色变成红色。
     * 2、如果当前结点的叔叔结点是黑色，
     *
     * @param tree
     * @param z
     */
    public void colorFix(Tree<Integer> tree, Node<Integer> z) {
        // 父结点是红色的
        while (z.getParent().getColor().equals(ColorEnum.RED)) {
            // 因为父结点是红色的，所以祖父一定不为空
            Node<Integer> grandPa = z.getParent().getParent();

            // 父结点是左结点
            if (grandPa.getLeft() == z.getParent()) {
                Node y = grandPa.getRight();
                // 叔叔是红色的
                if (y.getColor().equals(ColorEnum.RED)) {
                    grandPa.setColor(ColorEnum.RED);
                    y.setColor(ColorEnum.BLACK);
                    z.getParent().setColor(ColorEnum.BLACK);
                    z = grandPa;
//                    print("case 1");
                    continue;
                    // 叔叔是黑色的且z是右结点
                } else if (z.getParent().getRight() == z) {
                    z = z.getParent();
                    leftRotate(tree, z);
//                    print("case 2");
                }
                // 叔叔是黑色的且z是左结点
                z.getParent().setColor(ColorEnum.BLACK);
                z.getParent().getParent().setColor(ColorEnum.RED);
                rightRotate(tree, z.getParent().getParent());
//                print("case 3");
            } else {
                // 父结点是右结点
                // 叔叔结点是红色的
                Node<Integer> y = grandPa.getLeft();
                if (y.getColor().equals(ColorEnum.RED)) {
                    grandPa.setColor(ColorEnum.RED);
                    y.setColor(ColorEnum.BLACK);
                    z.getParent().setColor(ColorEnum.BLACK);
                    z = grandPa;
//                    print("case 4");
                    continue;
                } else if (z.getParent().getLeft() == z) {
                    z = z.getParent();
                    rightRotate(tree, z);
//                    print("case 5");
                }
                // z是右结点
                z.getParent().setColor(ColorEnum.BLACK);
                z.getParent().getParent().setColor(ColorEnum.RED);
                leftRotate(tree,z.getParent().getParent() );
//                print("case 6");
            }
        }
        // 有几种情况(z是红色的)
        // 1. z.getParent.color == black
        // 2. z.getParent == NIL // z.is root
        tree.getRoot().setColor(ColorEnum.BLACK);
//        print("-----insert end-----");
    }

    public Tree<Integer> buildTree(int i) {
        Tree<Integer> tree = new Tree<>();
        tree.setRoot(NIL);
        for (int j = 0; j < i; j++) {
            Node<Integer> node = new Node<>();
            int num = (int) (Math.random() *i + 1);
//            System.out.println("insert--> " + num);
            node.setKey(num);
            node.setParent(NIL);
            node.setRight(NIL);
            node.setLeft(NIL);
            insert(tree, node);
            isInvalid(tree);
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
        print("delete--> " + z.getKey());
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
            print("x -- " + x.getKey());
            deleteColorFix(tree, x);
        }
        print("----delete over----");
    }

    Node<Integer> findMinimun(Node<Integer> node) {
        Node<Integer> p = NIL;
        while (node != NIL) {
            p = node;
            node = node.getLeft();
        }
        return p;
    }


    void deleteColorFix(Tree<Integer> tree, Node<Integer> x) {
        while (x != tree.getRoot() && x.getColor().equals(ColorEnum.BLACK)) {
            print("parent -- " + x.getParent().getKey() + "  Color-- " + x.getParent().getColor());
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
                    print("Case 1: x是左结点，右兄弟是红色");
                }
                // case 2
                if (brother.getRight().getColor().equals(ColorEnum.BLACK) &&
                        brother.getLeft().getColor().equals(ColorEnum.BLACK)) {
                    brother.setColor(ColorEnum.RED);
                    x = x.getParent();
                    print("Case 2：x是左结点,右兄弟是黑色，且它的孩子全是黑色的");
                    continue;
                }
                // case 3 brother left red and right black
                else if (brother.getRight().getColor().equals(ColorEnum.BLACK)) {
                    brother.setColor(ColorEnum.RED);
                    brother.getLeft().setColor(ColorEnum.BLACK);
                    rightRotate(tree, brother);
                    brother = x.getParent().getRight();
                    print("Case 3：x是左结点，右兄弟是黑色且右兄弟的右孩子也是黑色的，右兄弟的左孩子是红色的");
                    ImageSave.save(mapPanel.saveImage());
                    // goto 4
                } else {
                    // case 4 brother right red
                    brother.setColor(x.getParent().getColor());
                    x.getParent().setColor(ColorEnum.BLACK);
                    brother.getRight().setColor(ColorEnum.BLACK);
                    leftRotate(tree, x.getParent());
                    x = tree.getRoot();
                    print("Case 4：x是左结点，右兄弟是红黑色的，右兄弟的右孩子是红色的");
                    break;
                }
            }

            // x是右结点，操作与上面相反
            else {
                Node<Integer> brother = x.getParent().getLeft();
                // case 5
                if (brother.getColor().equals(ColorEnum.RED)) {
                    // 兄弟结点的颜色是红色的，那么父结点的颜色一定是黑色的
                    // 将父结点的颜色变成红色，兄弟结点的颜色变成黑色
                    // 左旋
                    brother.setColor(ColorEnum.BLACK);
                    x.getParent().setColor(ColorEnum.RED);
                    rightRotate(tree, brother.getParent());
                    brother = x.getParent().getLeft();
                    print("Case 5: x是右结点，左结点是红色的");
                    // goto case 6 7 or 8
                }
                // case 6
                // 1 brother all black
                if (brother.getLeft().getColor().equals(ColorEnum.BLACK) &&
                        brother.getRight().getColor().equals(ColorEnum.BLACK)) {
                    brother.setColor(ColorEnum.RED);
                    x = x.getParent();
                    print("Case 6: x是右结点，左结点是黑色的且它的孩子全是黑色的");
                    continue;
                }
                // Case 7 left black and right red
                else if (brother.getLeft().getColor().equals(ColorEnum.BLACK)) {
                    brother.setColor(ColorEnum.RED);
                    brother.getRight().setColor(ColorEnum.BLACK);
                    leftRotate(tree, brother);
                    brother = x.getParent().getLeft();
                    print("Case 7: x是右结点，左结点是黑色的，左结点的左孩子是黑色的，右孩子是红色的");
                    ImageSave.save(mapPanel.saveImage());
                    // goto 4
                }
                // Case 8 left red
                else {
                    brother.setColor(x.getParent().getColor());
                    x.getParent().setColor(ColorEnum.BLACK);
                    brother.getLeft().setColor(ColorEnum.BLACK);
                    rightRotate(tree, x.getParent());
                    x = tree.getRoot();
                    print("Case 8: x是右结点，左结点是黑色的，左结点的左孩子是红色的");
                    break;
                }
            }
        }
        x.setColor(ColorEnum.BLACK);
    }

    @Test
    public void testBuild() {
        int num = 10;
        Tree<Integer> tree = buildTree(num);
        inorderVisit(tree);
        isInvalid(tree);
    }

    private String message5 = "不满足性质5：对每个结点，从该结点到其所有后代的简单路径上，均包含相同数目的黑色结点。";
    private String message4 = "不满足性质4:如果一个结点是红色的，那么它的每个孩子结点都是黑色的";

    private String message1 = "不满足性质2：根结点必须是黑色。";
    public boolean isInvalid(Tree<Integer> tree) {
        if (tree.getRoot().getColor() != ColorEnum.BLACK) {
            log.info(message1 + "[node.key " + tree.getRoot().getKey() + "]");
            return false;
        };
        if (tree.getRoot() != NIL) {
            int i = countBlackNode(tree.getRoot().getLeft());
            if (i == -1) {
                return false;
            }
            int j = countBlackNode(tree.getRoot().getRight());
            if (j == -1) {
                return false;
            }
            if (j != i) {
                log.info(message5 + "[node.key " + tree.getRoot().getKey()
                + "]");
                return false;
            }
        }
        return true;
    }
    public boolean isInvalidColor(Node<Integer> node) {
        boolean flag = true;
        if (node.getColor() == ColorEnum.RED) {
            flag = node.getRight().getColor() == ColorEnum.BLACK
                    && node.getLeft().getColor() == ColorEnum.BLACK;
        }
        if (!flag) {
            log.info(message4 + "[node.key " + node.getKey() + "]");
            return false;
        }
        return true;
    }

    int countBlackNode(Node<Integer> node) {
        if (node == NIL) {
            return 1;
        } else {
            int i = countBlackNode(node.getLeft());
            if (i == -1) {
                return -1;
            }
            int j = countBlackNode(node.getRight());
            if (j == -1) {
                return -1;
            }
            if (j != i) {
                log.info(message5 + "[node.key " + node.getKey()
                        + "]");
                return -1;
            }
            if (node.getColor() == ColorEnum.BLACK) {
                return i + 1;
            }
            // 如果是黑色结点,判断一下颜色
            boolean flag = isInvalidColor(node);
            if (!flag) {
                return -1;
            }
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



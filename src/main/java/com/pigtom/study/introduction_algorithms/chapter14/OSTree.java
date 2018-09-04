package com.pigtom.study.introduction_algorithms.chapter14;

import com.pigtom.study.introduction_algorithms.chapter12.ColorEnum;
import com.pigtom.study.introduction_algorithms.chapter12.Node;
import com.pigtom.study.introduction_algorithms.chapter12.Tree;
import com.pigtom.study.introduction_algorithms.chapter13.RedBlackTree;
import org.junit.jupiter.api.Test;

import static com.pigtom.study.introduction_algorithms.chapter13.RedBlackTree.NIL;

/**
 * OSTree is abbr of order statistic tree 顺序统计树
 * OSTree 只是简单地在每个结点上增加了一个附加信息x.size
 * x.size用来存储以结点x为根的树的大小，其包括x在内的以x为树根的子树的全部结点数。
 */
public class OSTree {
    //14.1-7 说明如何在O(nlgn)时间内，利用顺序统计树对大小为n的数组中的逆序对进行计数。
    //  *** 逆序对：在给定的集合A中，若i<j, A[i] > a[j]则称（i,j)为一对逆序对。
    // 思考：利用给定的数组构建一颗顺序统计树。在构建的过程中，每插入一个结点便返回由这个结点
    //       构成的逆序对个数。
    // 插入时计算逆序对的方法：逆序对个数 = 树总大小 - 插入树中结点的秩
    @Test
    public void buildTree() {
        int []a = {9,8,7,6,5,4,3,2,1,0};
        Tree<Integer> tree = new Tree<>();
        tree.setRoot(NIL);
        // sum 便是最后的逆序对
        int sum = 0;
        for (int i : a) {
            Node<Integer> node = getNewNode(i);
            insert(tree, node);
            sum += tree.getRoot().size - getRank(tree, node);
            System.out.println(sum);
        }
        System.out.println("inorder couple: " + sum);
    }

    // 14.1-8 现有一个圆上的n条弦，每条弦都由其端点来定义。请给出一个能在O(nlgn)时间内
    // 确定圆内相交弦对数的算法。（例如：如果n条弦都为直径，它们相交于圆心，则正确的答案
    // 为 n * (n - 1).假设任意两条弦不会共享端点。

    // 思考 ：
    // 假设圆的圆心为（0,0),圆的半径为1。规定有这样的顺序，以（1.0）为起点
    // 逆时针绕着圆心在圆周长前行的顺序为正序(order)，最先出现的弦上的点的正序值为1
    // 规定弦是有方向的且由正序值小的点指向正序值大的点。
    // 如何判断一个端点的正序大小:设端点为P(x,y)
    // k = (y-0)/(x-1),k越大正序正越大
    // 1. 按照正序值大小，将每条弦化为标准形式,
    // 定义标准弦 chord(p1, p2); {order(p1) < order(p2)}
    // 2. 按p1的正序值将chord{i}进行排序后构成集合A{chord}。
    // 3. 按A的顺序取出chord{i},并将chord的p1和p2插入到顺序树OSTree中
    //   每当将chord插入到树中时，计算rank(p1) and rank(p2)
    //   cur_intersect = rank(p2) - rank(p1) -1
    //   cur_intersect为chord与之前所有的弦相交次数，即与chord相关的弦的条数。
    //   将A中的chord全部插入到OSTree后时, intersect = accumulate(cur_intersect)(from 1 to n)
    // TODO 实现上述思考


    public  void insert(Tree<Integer> tree, Node<Integer> node) {
        RedBlackTree.insert(tree, node);
        fixParent(node.getParent());
        colorFix(tree, node);
    }

    /**
     * 得到关键字k的秩
     * @param tree tree
     * @param k 关键字
     * @return 插入到树中结点的秩
     */
    int getRank(Tree<Integer> tree, int k) {
        Node<Integer> node = tree.getRoot();
        // 假设插入的结点为x， r为node的秩，
        // 如果x.k < node.k, x的秩为r
        // 否则 x的秩为 r+1
        int r = 0;
        while (k != node.getKey()) {
            if (k < node.getKey()) {
                node = node.getLeft();
            } else {
                r += node.getLeft().size + 1;
                node = node.getRight();
            }
        }
        int treeSize = tree.getRoot().size;
        r = r + node.getLeft().size + 1;
        // fix color
        return treeSize - r;
    }

    int getRank(Tree<Integer> tree, Node<Integer> x1) {
        int r = x1.getLeft().size + 1;
        Node<Integer> a = x1;
        while (a != tree.getRoot()) {
            if (a == a.getParent().getRight()) {
                r += a.getParent().getLeft().size + 1;
            }
            a = a.getParent();
        }

        return r;
    }
    private void leftRotate1(Tree<Integer> tree, Node<Integer> z) {
        RedBlackTree.leftRotate(tree, z);
        // 左转之后处理好z和z.getParent的size
        z.size = z.getLeft().size + z.getRight().size + 1;
        z.getParent().size = z.getParent().getLeft().size + z.getParent().getRight().size + 1;
    }

    private void rightRotate1(Tree<Integer> tree, Node<Integer> z) {
        RedBlackTree.rightRotate(tree, z);
        z.size = z.getRight().size + z.getLeft().size + 1;
        z.getParent().size = z.getParent().getLeft().size + z.getParent().getRight().size + 1;
    }
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
                    continue;
                    // 叔叔是黑色的且z是右结点
                } else if (z.getParent().getRight() == z) {
                    z = z.getParent();
                    // 左转之后处理好z和z.getParent的size
                    leftRotate1(tree, z);
                }
                // 叔叔是黑色的且z是左结点
                z.getParent().setColor(ColorEnum.BLACK);
                z.getParent().getParent().setColor(ColorEnum.RED);
                rightRotate1(tree, z.getParent().getParent());
            } else {
                // 父结点是右结点
                // 叔叔结点是红色的
                Node<Integer> y = grandPa.getLeft();
                if (y.getColor().equals(ColorEnum.RED)) {
                    grandPa.setColor(ColorEnum.RED);
                    y.setColor(ColorEnum.BLACK);
                    z.getParent().setColor(ColorEnum.BLACK);
                    z = grandPa;
                    continue;
                } else if (z.getParent().getLeft() == z) {
                    z = z.getParent();
                    rightRotate1(tree, z);
                }
                // z是右结点
                z.getParent().setColor(ColorEnum.BLACK);
                z.getParent().getParent().setColor(ColorEnum.RED);
                leftRotate1(tree,z.getParent().getParent() );
            }
        }
        // 有几种情况(z是红色的)
        // 1. z.getParent.color == black
        // 2. z.getParent == NIL // z.is root
        tree.getRoot().setColor(ColorEnum.BLACK);
//        print("-----insert end-----");
    }

    /**
     * 将父结点及祖父结点的size加一
     * @param p 父结点
     */
    public void fixParent(Node<Integer> p) {
        while (p != NIL) {
            p.size += 1;
            p = p.getParent();
        }
    }

    /**
     * 从内存中分配一个Node,并将其的key赋为k，color=red
     * parent,left,right都指向NIL，且size为1
     * @param k 关键字
     * @return 新生成的Node
     */
    public Node<Integer> getNewNode(int k) {
        Node<Integer> node = new Node<>();
        node.setKey(k);
        node.setLeft(NIL);
        node.setRight(NIL);
        node.setParent(NIL);
        node.setColor(ColorEnum.RED);
        node.size = 1;
        return node;
    }
}

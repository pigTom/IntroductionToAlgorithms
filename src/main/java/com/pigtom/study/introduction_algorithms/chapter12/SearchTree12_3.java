/**
 * AUTHOR : Administrator
 * DATE : 2018/7/25
 **/
package com.pigtom.study.introduction_algorithms.chapter12;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
     * @param tree
     */
    public void addNode(Node<Integer> node, Tree<Integer> tree) {
        Node<Integer> pointer = tree.getRoot();
        Node<Integer> parent = null;
        while (pointer != null) {
            parent = pointer;
            // 插入到左子树中
            if (node.getKey() < pointer.getKey()) {
                pointer = pointer.getLeft();
            } else
                pointer = pointer.getRight();
        }
        node.setParent(parent);
        if (parent == null) {
            tree.setRoot(node);// the tree is empty
        } else if (node.getKey() < parent.getKey()) {
            parent.setParent(node);
        } else
            parent.setRight(node);
    }

    /**
     * 查询树中包含给定关键字的一个节点，默认先和根匹配
     *
     * @param key
     * @param tree
     * @return
     */
    public static Node<Integer> search(int key, Tree<Integer> tree) {
        // find node
        Node<Integer> node = tree.getRoot();
        while (node != null) {
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
    public void testSearch() {
        Tree<Integer> tree = Node.buildSearchTree(20);
        SearchTree12_1.inorderVisitNoRecurse(tree);
        Node<Integer> note = search(3, tree);
        Node<Integer> note2 = search(4, tree);
        if (note != null) {
            System.out.println("---3---" + note.getKey());
        }
        if (note2 != null) {
            System.out.println("---4---" + note2.getKey());
        }

    }

    /**
     * 假设树中的关键字各不相同，根据关键字删除结点。
     * 有三种情况：（将要被删除的结点记作y，y的右孩子记作yRight，左孩子记作yLeft,父结点为yP）
     * 1、如果y结点为叶结点，则直接删除。
     * 2、如果y结点只有一个孩子，那么将yRight或yLeft代替y的位置
     * 3、如果y结点有两个孩子，在y的右子树中找到一个最小的结点(这个最小的结点也比y结点大)，即子树中最靠左的结点，记作x
     * x一定没有左结点，将x结点的右结点记作xRight,x的父结点记作xP,将xp.left = xRight,
     * yP.child = x, x.left = y.left and x.right = x.right
     *
     * @param key
     * @param tree
     */
    public void deleteNode(int key, Tree<Integer> tree) throws Exception {
        // find node
        Node<Integer> node = search(key, tree);
        if (node == null) return;
        if (node.getLeft() == null) {
            transplant(tree, node, node.getRight());
        } else if (node.getRight() == null) {
            transplant(tree, node, node.getLeft());
        } else {
            Node<Integer> succeed = SearchTree12_2.treeMinimumRecurse(node.getRight());
            if (succeed.getParent() != node) {
                transplant(tree, succeed, succeed.getRight());
                succeed.setRight(node.getRight());
                succeed.getRight().setParent(succeed);
            }
            succeed.setLeft(node.getLeft());
            succeed.getLeft().setParent(succeed);
            transplant(tree, node, succeed);
        }

    }

    @Test
    public void testExchangeNode() throws Exception {
        Tree<Integer> tree = Node.buildSearchTree(0, 10);
        SearchTree12_1.preorderVisitRecurse(tree.getRoot());
        System.out.println("*************");
        Tree<Integer> tree1 = Node.buildSearchTree(10, 20);
        transplant(tree, tree.getRoot(), tree1.getRoot());
        SearchTree12_1.preorderVisitRecurse(tree.getRoot());
    }


//    public static void exchangeNode(Tree<Integer> tree, Node<Integer> x, Node<Integer> l) throws Exception {
//        if (x.getParent() == null) {
//            tree.setRoot(l);
//        } else if (x.getParent().getLeft() == x) {
//            x.getParent().setLeft(l);
//        } else x.getParent().setRight(l);
//        if (l != null) {
//            l.setParent(x.getParent());
//        }
//    }


    @Test
    public void testRemove() throws Exception {
        for (int i = 0; i < 20; i++) {
            int size = 10;
            Tree<Integer> tree = Node.buildSearchTree(size);
            SearchTree12_1.inorderVisitNoRecurse(tree);
            int num = (int) (Math.random() * size);
            System.out.println("*******" + num + "*******");
            deleteNode(num, tree);
            SearchTree12_1.inorderVisitNoRecurse(tree);
            System.out.println("\r");
        }
    }

    /**
     * 将树中的结点X完全被另一个结点y取代;
     * 意味着以结点x为子树将全部被取代。
     * 具体操作：
     * 将结点x的父结点记作p：将y的父结点设为p
     * 1、如果结点x的父结点p存在，
     * 结点x为左孩子 则将结点y作为p的左孩子
     * 结点x为右孩子 则将结点y作为p的右孩子
     * 2、如果p不存在
     * 将树tree的根设为y
     *
     * @param tree
     * @param u    不能为空
     * @param v    要取代的值
     * @throws NullPointerException
     */
    public void transplant(Tree<Integer> tree, Node<Integer> u, Node<Integer> v) {
        if (u.getParent() == null) {
            tree.setRoot(v);
        } else if (u == u.getParent().getLeft()) {
            u.getParent().setLeft(v);
        } else u.getParent().setRight(v);
        if (v != null) {
            v.setParent(u.getParent());
        }
    }

    public void delete(int key, Tree<Integer> tree) {
        Node<Integer> node = search(key, tree);
        if (node != null) {
            delete(tree, node);
        }
    }

    public void delete(Tree<Integer> tree, Node<Integer> z) {
        if (z.getLeft() == null) {
            transplant(tree, z, z.getRight());
        } else if (z.getRight() == null) {
            transplant(tree, z, z.getLeft());
        } else {
            Node<Integer> y = SearchTree12_2.treeMinimumRecurse(z.getRight());
            if (y.getParent() != z) {
                transplant(tree, y, y.getRight());
                y.setRight(z.getRight());
                y.getRight().setParent(y);
            }
            transplant(tree, z, y);
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
        }
    }

    /**
     * 12.3-1 给出TREE-INSERT过程的一个递归版本
     */
    public void treeInsert(Node<Integer> head, Node<Integer> node) {
        if (node.getKey() < head.getKey()) {
            if (head.getLeft() == null) {
                head.setLeft(node);
                node.setParent(head);
            } else treeInsert(head.getLeft(), node);
        } else if (head.getRight() == null) {
            head.setRight(node);
            node.setParent(head);
        } else treeInsert(head.getRight(), node);
    }

    @Test
    public void testInsertWithRecurse() {
        int size = 50;
        Tree<Integer> tree = new Tree<>();
        Node<Integer> node = new Node<>();
        node.setKey((int) (Math.random() * size));
        tree.setRoot(node);
        for (int i = 0; i < size; i++) {
            Node<Integer> n = new Node<>();
            n.setKey((int) (Math.random() * size));
            treeInsert(node, n);
        }

        SearchTree12_1.inorderVisitNoRecurse(tree);
    }

    /***
     * 12.3-5 假设为每个结点换一种设计，属性x.p指向x的双亲，属性x.succ指向x的后继。试给出使用这种表示法的
     * 的二叉搜索树T上SEARCH、INSERT、和DELETE操作的伪代码。这些伪代码就在O（h）时间内执行完，其中h为树的高度。
     */

    public class ANode {
        ANode p;
        ANode succ;
        Integer key;
    }

    public class AT {
        ANode left;
    }
    // insert(T, n)

    /**
     * 1. t = T.left
     * 2. if n.key < t.key :
     * n.p = t // n be the child of t
     * pre = Predecessor(T,t)
     * if pre != null:
     * pre.succ = t
     * else if t.p != null && t.p.key > t.key && t.p.key <= n.key: // t is the left child
     * t = t.p
     * 让T.left 为指针x
     * 1. 如果n比当前指针x小，则将n插入x的左结点（因为如果n比当前指针小，说明某个结点的后继，它的左结点
     * 一定为空。）
     * 3. 如果x的父结点为空，且x的后继为空，则将n作为x的后继，x作为n的父结点。结束。
     * 4. 如果x的父结点为空,且x的后继不为空，将x的后继作为x，进入步骤1.
     * 2. 如果x的父结点不为空且x父结点大于n，且x的后继不为空，将x的后继变成x，进入步骤1.
     * 5. 如果x的父结点不为空，如果x的父结点小于n，将x的父结点作为x进入步骤5.
     */
    public ANode findPre(ANode node) {
        while (node.p != null) {
            if (node.p.key < node.key) {
                break;
            }
            node = node.p;
        }
        return node.p;
    }

    public ANode findSucc(ANode node) {
        while (node.p != null) {
            if (node.p.key > node.key) {
                break;
            }
            node = node.p;
        }
        return node.p;
    }

    public void travel(AT tree) {
        ANode node = tree.left;
        while (node != null) {
            System.out.println(node.key);
            node = node.succ;
        }
    }

    @Test
    public void testTravel() {
        SearchTree12_3 searchTree12_3 = new SearchTree12_3();
        AT tr = searchTree12_3.buidlANode();
        System.out.println("**************start***********");
        travel(tr);
        System.out.println("**************over***********");

    }

    public AT buidlANode() {
        int size = 10;
        Set<Integer> numbs = new HashSet<>(size);
        for (int i = 0; i < size; i++) {
            numbs.add((int) (Math.random() * 1000));
        }
        AT tree = new AT();
        Iterator<Integer> iterator = numbs.iterator();
        while (iterator.hasNext()) {
            ANode n = new ANode();
            n.key = iterator.next();
            System.out.println(n.key);
            ainsert(tree, n);
        }
        return tree;
    }

    public void ainsert(AT t, ANode n) {
        ANode x = t.left;
        if (x == null) {
            t.left = n;
            return;
        }
        while (n.key >= x.key) {
            // x.p == null
            // insert into the right
            if (x.p == null && x.succ == null) {
                // x.right == null
                n.p = x;
                x.succ = n;// x may equal to n
                return;
            } else if (x.p == null) { // the root node and x.succ != null
                x = x.succ;
                continue;
            }
            // 非根结点
            // 结点为左结点 且n大于x的父结点，向根部走
            else if (x.p.key >= x.key && x.p.key < n.key) {
                x = x.p;

                while (x.p != null && x.p.key > x.key && x.p.key < n.key) {
                    x = x.p;
                }
            }
            // 非根结点中有几种情况：
            // 1、如果为右结点,且n.key大于x.key,要将n插入x的右子树中
            // 2、x为左结点，且n的值小于x的父结点的值，要将n插入x的右子树中
            // 3、x为根结点，要将插入x的右子树中。

            // 首先要判断x有没有右子树
            // 如果x没有后继 那么它肯定没有右子树
            // 如果x的后继不为空
            // 则有以下几种情况
            // 1、x的后继为x的右孩子
            // 2、x的后继为x的右子树中的最小结点
            // 3、x的后继为x的祖先
            // 我们将这样进行处理，如果x的后继为祖先，说明x没有右结点。我们将n插入到x的右结点中
            // 如果 x的后继为x的右子树中的最小者，我们将x.succ赋值给x

            // 如何判断x的后继是否是x的祖先呢，我们知道如果A是B的后继，那么B是A的前驱，
            // 找到x的后继（从祖先中找到一个大于x的最小结点，不一定是真正的后继）
            ANode succ = findSucc(x);
            // 如果x的后继的pre的话，说明x没有右结点，则将n插入x右结点
            if (x.succ == null || succ != null && x.succ == succ) {
                n.p = x;
                n.succ = x.succ;
                // n插入后，x的后继将变成n
                x.succ = n;
                return;
            } else //否则说明x有右结点要让n插入x的子树中
            {
                x = x.succ;
                continue;
            }

        }

        // add n to left of x
        n.p = x;
        n.succ = x;
        ANode pre = findPre(n);
        if (pre != null) {
            pre.succ = n;
        } else {
            t.left = n;
        }
    }
}

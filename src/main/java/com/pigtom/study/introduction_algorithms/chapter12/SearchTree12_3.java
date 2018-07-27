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
        SearchTree12_1.inorderVisitNoRecurse(tree.getRoot());
        Node<Integer> note = search(3, tree);
        Node<Integer> note2 = search(4, tree);
        if (note != null) {
            System.out.println("---3---" + note.getKey());
        } if (note2 != null) {
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
    public static void deleteNode(int key, Tree<Integer> tree)throws Exception{
        // find node
        Node<Integer> node = search(key, tree);
        if (node == null) return;
        if (node.getLeft() == null) {
            exchangeNode(tree, node, node.getRight());
        } else if (node.getRight() == null) {
            exchangeNode(tree, node, node.getLeft());
        } else {
            Node<Integer> succeed = SearchTree12_2.treeMinimumRecurse(node.getRight());
            if (succeed.getParent() == node) {
                //TODO wrong place
                succeed.setLeft(node.getLeft());
            } else {
                succeed.getParent().setLeft(succeed.getRight());

                succeed.setLeft(node.getLeft());
                succeed.setRight(node.getRight());
            }
            exchangeNode(tree, node, succeed);
        }

    }

    @Test
    public void testExchangeNode() throws Exception {
        Tree<Integer> tree = Node.buildSearchTree(0, 10);
        SearchTree12_1.preorderVisitRecurse(tree.getRoot());
        System.out.println("*************");
        Tree<Integer> tree1 = Node.buildSearchTree(10, 20);
        exchangeNode(tree, tree.getRoot(), tree1.getRoot());
        SearchTree12_1.preorderVisitRecurse(tree.getRoot());
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
     * @param tree
     * @param x 不能为空
     * @param y
     * @throws NullPointerException
     */
    public static void exchangeNode(final Tree<Integer> tree, Node<Integer> x, Node<Integer> y) throws Exception {
        // x can`t be null and x will be replace by y
        // y can`t be null
        if (x == null) {
            return;
        }
        Node<Integer> p = x.getParent();
        if (y != null) {
            if (y == p) {
                throw new Exception("wrong");
            }
            y.setParent(p);
        }

        if (p != null) {
            if (p != x.getParent()) {
                System.out.println("Error");
            }
            if (p.getLeft() == x) {
                p.setLeft(y);
            } else p.setRight(y);
        } else tree.setRoot(y);
    }


    @Test
    public void testRemove() throws Exception {
        for (int i = 0; i < 10; i++) {
            int size = 3;
            Tree<Integer> tree = Node.buildSearchTree(size);
            SearchTree12_1.inorderVisitNoRecurse(tree.getRoot());
            int num = (int) (Math.random() * size);
            System.out.println("*******" + num + "*******");
            deleteNode(num, tree);
            SearchTree12_1.inorderVisitNoRecurse(tree.getRoot());
            System.out.println("\r");
        }
    }

}

package com.pigtom.study.introduction_algorithms.chapter12;

public class Node<T> {
    private Node<T> parent;
    private Node<T> left;
    private Node<T> right;
    private ColorEnum color;
    private T key;

    public int h;
    /**
     * position in horizontal
     */
    public int x;
    /**
     * position in vertical
     */
    public int y;
    public int width;

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public ColorEnum getColor() {
        return color;
    }

    public void setColor(ColorEnum color) {
        this.color = color;
    }

    /**
     * 建立搜索树：左子树的结点关键字全部不大于父结点，右子树的结点关键字不小于父结点关键字
     *
     * @return 返回根结点
     */
    public static Tree<Integer> buildSearchTree(int size) {
        Tree<Integer> tree = new Tree<>();
        for (int i = 0; i < size; i++) {
            Node<Integer> node = new Node<>();
            node.key = (int) (Math.random() * size);
            insert(tree, node);
        }
        return tree;
    }

    public static Tree<Integer> buildSearchTree(int begin, int end) {
        Tree<Integer> tree = new Tree<>();
        for (int i = begin; i <end; i ++) {
            Node<Integer> node = new Node<>();
            node.key = (int) (Math.random() * (end - begin) + begin);
            insert(tree, node);
        }
        return tree;
    }
    /**
     * 插入一个结点，保证原搜索树的特点：左子树的结点关键字全部不大于父结点，右子树的结点关键字不小于父结点关键字
     *
     * @param tree 树根
     * @param node 将要被插入的结点
     */
    @SuppressWarnings(value = "unused")
    public static void insertNode(Tree<Integer> tree, Node<Integer> node) {
        Node<Integer> parent = new Node<>();
        // 将当头结点设为父结点的左结点
        // 让node直接与parent的左结点比较（默认情况下：node.key > parent.key)
        parent.left = tree.getRoot();

        while (true) {
            while (parent.left != null && node.key <= parent.left.key) {
                parent = parent.left;
            }

            if (parent.left == null) {
                parent.left = node;
                node.parent = parent;
                return;
            }

            // 如果父结点的左结点小于要插入的结点的关键字
            // 则让父结点的左结点变成父结点，并尝试将node插入其右子树中
            parent = parent.left;
            // node.key > root.left.key and node.key < root.key
            while (parent.right != null && node.key >= parent.right.key) {
                parent = parent.right;
            }

            // 如果右结点为空
            // 则尝试将要插入的结点放入父结点的右结点中
            if (parent.right == null) {
                parent.right = node;
                node.parent = parent;
                return;
            }

            // 父结点的右结点不为空 且 node.key < parent.right.key
            // 父结点的右结点变成父结点，尝试将node插入新的父结点的左子树中
            parent = parent.right;
        }
    }


    /**
     * 用更简洁的方式插入一个元素
     *
     * @param tree 树根
     * @param node 将要被插入的结点
     */
    public static void insert(Tree<Integer> tree, Node<Integer> node) {
        Node<Integer> p = null;
        Node<Integer> n = tree.getRoot();
        while (n != null) {
            p = n;
            if (node.key < n.key) {
                n = n.left;
            } else
                n = n.right;
        }

        node.parent = p;
        if (p == null) { // Tree is empty
            tree.setRoot(node);
        } else if (node.key < p.key) {
            p.left = node;
        } else {
            p.right = node;
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
    public static void transplant(Tree<Integer> tree, Node<Integer> u, Node<Integer> v) {
        if (u.getParent() == null) {
            tree.setRoot(v);
        } else if (u == u.getParent().getLeft()) {
            u.getParent().setLeft(v);
        } else u.getParent().setRight(v);
        if (v != null) {
            v.setParent(u.getParent());
        }
    }

    public static void inorderVisitNoRecurse(Tree<Integer> tree) {
        SearchTree12_1.inorderVisitNoRecurse(tree);
    }

    public static void inorderVisitRecurse(Tree<Integer> tree) {
        SearchTree12_1.inorderVisitRecurse(tree.getRoot());
    }
    public static void preorderVisit(Tree<Integer> tree) {
        SearchTree12_1.preorderVisitRecurse(tree.getRoot());
    }

    public static Node<Integer> search(Tree<Integer> tree, int key) {
        return SearchTree12_3.search(key, tree);
    }
}
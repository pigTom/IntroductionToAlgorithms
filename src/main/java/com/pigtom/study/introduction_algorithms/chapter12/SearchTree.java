package com.pigtom.study.introduction_algorithms.chapter12;

import org.junit.jupiter.api.Test;

/**
 * 搜索树（SearchTree):
 * 每个结点就是一个对象。一个结点有父结点对象，左孩子，右孩子。如果某个孩子不存在，则相应的属性为NIL。
 * 根结点是惟一父结点为NIL的结点。
 * 遍历分为三种情况：前序遍历，中序遍历，后序遍历。是以根被访问的顺序来命名的，前序遍历则为先访问根再访问
 * 左结点，最后访问右结点。中序遍历是左根右，后序遍历则是左右根。
 */
public class SearchTree {
    class Node<T> {
        Node<T> parent;
        Node<T> left;
        Node<T> right;
        T key;
    }

    /**
     * 建立搜索树：左子树的结点关键字全部不大于父结点，右子树的结点关键字不小于父结点关键字
     *
     * @return 返回根结点
     */
    Node<Integer> buildSearchTree(int size) {
        Node<Integer> head = new Node<>();
        head.key = 4;
        for (int i = 0; i < size; i++) {
            Node<Integer> node = new Node<>();
            node.key = (int) (Math.random() * size);
            insertNode(head, node);
        }
        return head;
    }

    @Test
    public void testInorderVisit() {
        Node<Integer> head = buildSearchTree(100);
        System.out.println("___________________________");
        inorderVisitRecurse(head);
        System.out.println("_____________***************___________");
        inorderVisitNoRecurse(head);
    }

    @Test
    public void testPreorderVisit() {
        Node<Integer> head = buildSearchTree(20);
        preorderVisitRecurse(head);
        System.out.println("___________________________");
        preorderVisitNoRecurse(head);
    }

    /**
     * 递归中序遍历,采用递归的方法，先输出左子树，再输出根结点，最后输出右子树
     *
     * @param head 树的根结点
     */
    void inorderVisitRecurse(Node<Integer> head) {
        if (head == null) {
            return;
        }
        inorderVisitRecurse(head.left);
        System.out.println(head.key);
        inorderVisitRecurse(head.right);
    }

    void preorderVisitRecurse(Node<Integer> head) {
        if (head == null)
            return;

        System.out.println(head.key);
        inorderVisitNoRecurse(head.left);
        inorderVisitNoRecurse(head.right);
    }

    /**
     * 非递归的中序遍历，先输出左结点，再输出根结点，最后输出右结点。
     * 要点：
     * 1：先判断父结点的左结点为不为空，如果不为空，将左结点变为父结点，继续步骤1.
     * 如果为空，输出父结点。
     * 2. 判断右结点是否为空，如果不为空，父结点变成右结点。继续步骤1.如果为空 步骤3。
     * <p>
     * 3.应该往根部上升，取出父结点的父结点记作祖父结点，如果祖父结点为空，结束。
     * 如果祖父结点不为空，判断父结点是否为祖父结点的左孩子。如果是 输出父结点。继续步骤2，
     * 如果不是。继续步骤3
     *
     * @param head 树的根结点
     */
    void inorderVisitNoRecurse(Node<Integer> head) {
        Node<Integer> parent = head;
        while (parent != null) {
            // step 1
            // 直到左树为null
            while (parent.left != null) {
                parent = parent.left;
            }

            // 输出当前节点（叶节点或只有右节点的有根节点）
            System.out.println(parent.key);

            // step 2
            // judge the right node of parent
            // if the right node of parent is null then go to step3
            while (parent.right == null) {
                // parent.right == null
                // go back to root
                // get the grandPa
                Node<Integer> grandPa = parent.parent;

                // 如果祖父不是空 并且父结点 为祖父结点的右结点，
                // 则一直往上走
                while (grandPa != null && parent == grandPa.right) {
                    parent = grandPa;
                    grandPa = parent.parent;
                }
                // if grandPa == null then parent must be root of all tree
                // and root.right == null
                if (grandPa == null) {
                    return;
                }

                // grandPa not null
                // if parent is the left son of grandPa
                // then print it
                System.out.println(grandPa.key);
                parent = grandPa;
                // parent is the right son of grandPa
                // now go back to root
            }

            // parent.right not null
            // go to step 1
            parent = parent.right;

        }
    }

    /**
     * 1. 先输出父节点，如果左子节点不为空，父节点变成左子节点。重复步骤1
     * 如果左子节为空，进入步骤2
     * 2. 判断右子节点。如果右子节点为空，进入步骤3。如果右子节点不为空，
     * 将右子节点变成父节点，进入步骤1
     * 3. 得到父节点的父节点，定义为祖父节点。如果祖父节点为空，结束
     * 如果祖父结点不为空，判断父节点是否为祖父节点的右节点。如果父节点是祖父节点的右节点
     * 父节点变成祖父节点进入步骤3
     * 如果不是 父节点变成祖父节点进入步骤2.
     *
     * @param head
     */
    void preorderVisitNoRecurse(Node<Integer> head) {
        Node<Integer> parent = head;
        while (parent != null) {
            // step 1
            System.out.println(parent.key);
            if (parent.left != null) {
                parent = parent.left;
                continue;
            }
            // parent.left == null
            // goto step 2
            while (parent.right == null) {
                // parent.right == null
                // goto step 3
                Node<Integer> grandPa = parent.parent;
                if (grandPa == null) {
                    return;
                }
                if (parent == grandPa.right) {
                    parent = grandPa;
                    continue;
                }
                // parent == grandPa.left
                parent = grandPa;
                break;
            }

            // parent.right != null
            // g
            parent = parent.right;
            // goto step 1
        }
    }

    /**
     * 插入一个结点，保证原搜索树的特点：左子树的结点关键字全部不大于父结点，右子树的结点关键字不小于父结点关键字
     *
     * @param head
     * @param node
     */
    void insertNode(Node<Integer> head, Node<Integer> node) {
        Node<Integer> parent = new Node<>();
        // 将当头结点设为父结点的左结点
        // 让node直接与parent的左结点比较（默认情况下：node.key > parent.key)
        parent.left = head;

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
}

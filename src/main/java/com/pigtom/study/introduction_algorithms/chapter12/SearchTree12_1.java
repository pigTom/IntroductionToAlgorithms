package com.pigtom.study.introduction_algorithms.chapter12;

import org.junit.jupiter.api.Test;

import static com.pigtom.study.introduction_algorithms.chapter12.Node.insertNode;

/**
 * 搜索树（SearchTree):
 * 每个结点就是一个对象。一个结点有父结点对象，左孩子，右孩子。如果某个孩子不存在，则相应的属性为NIL。
 * 根结点是惟一父结点为NIL的结点。
 * 遍历分为三种情况：前序遍历，中序遍历，后序遍历。是以根被访问的顺序来命名的，前序遍历则为先访问根再访问
 * 左结点，最后访问右结点。中序遍历是左根右，后序遍历则是左右根。
 */
public class SearchTree12_1 {

    /**
     * 建立搜索树：左子树的结点关键字全部不大于父结点，右子树的结点关键字不小于父结点关键字
     *
     * @return 返回根结点
     */
    static Tree<Integer> buildSearchTree(int size) {
        Tree<Integer> tree = new Tree<>();
        for (int i = 0; i < size; i++) {
            Node<Integer> node = new Node<>();
            node.setKey((int) (Math.random() * size));
            Node.insert(tree, node);
        }
        return tree;
    }

    @Test
    public void testInorderVisit() {
        Tree<Integer> tree = buildSearchTree(100);
        System.out.println("___________________________");
        inorderVisitRecurse(tree.getRoot());
        System.out.println("_____________***************___________");
        inorderVisitNoRecurse(tree);
    }

    @Test
    public void testPreorderVisit() {
        Tree<Integer> tree = buildSearchTree(20);
        preorderVisitRecurse(tree.getRoot());
        System.out.println("___________________________");
        preorderVisitNoRecurse(tree.getRoot());
    }

    /**
     * 递归中序遍历,采用递归的方法，先输出左子树，再输出根结点，最后输出右子树
     *
     * @param head 树的根结点
     */
    public static void inorderVisitRecurse(Node<Integer> head) {
        if (head == null) {
            return;
        }
        inorderVisitRecurse(head.getLeft());
        System.out.println(head.getKey());
        inorderVisitRecurse(head.getRight());
    }

    public static void preorderVisitRecurse(Node<Integer> head) {
        if (head == null)
            return;

        System.out.println(head.getKey());
        preorderVisitRecurse(head.getLeft());
        preorderVisitRecurse(head.getRight());
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
    public static void inorderVisitNoRecurse(Tree<Integer> head) {
        Node<Integer> parent = head.getRoot();
        while (parent != null) {
            // step 1
            // 直到左树为null
            while (parent.getLeft() != null) {
                parent = parent.getLeft();
            }

            // 输出当前节点（叶节点或只有右节点的有根节点）
            System.out.println(parent.getKey());

            // step 2
            // judge the right node of parent
            // if the right node of parent is null then go to step3
            while (parent.getRight() == null) {
                // parent.right == null
                // go back to root
                // get the grandPa
                Node<Integer> grandPa = parent.getParent();

                // 如果祖父不是空 并且父结点 为祖父结点的右结点，
                // 则一直往上走
                while (grandPa != null && parent == grandPa.getRight()) {
                    parent = grandPa;
                    grandPa = parent.getParent();
                }
                // if grandPa == null then parent must be root of all tree
                // and root.right == null
                if (grandPa == null) {
                    return;
                }

                // grandPa not null
                // if parent is the left son of grandPa
                // then print it
                System.out.println(grandPa.getKey());
                parent = grandPa;
                // parent is the right son of grandPa
                // now go back to root
            }

            // parent.right not null
            // go to step 1
            parent = parent.getRight();

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
    public void preorderVisitNoRecurse(Node<Integer> head) {
        Node<Integer> parent = head;
        while (parent != null) {
            // step 1
            System.out.println(parent.getKey());
            if (parent.getLeft() != null) {
                parent = parent.getLeft();
                continue;
            }
            // parent.left == null
            // goto step 2

            while (true) {
                // step 2
                if (parent.getRight() != null) {
                    parent = parent.getRight();
                    break;// goto step 1
                }

                // parent.right == null
                // goto step 3
                while (true) {
                    Node<Integer> grandPa = parent.getParent();
                    if (grandPa == null) {
                        return;
                    }
                    if (parent == grandPa.getRight()) {
                        parent = grandPa;
                        continue;
                    }
                    // parent == grandPa.left
                    // goto step 2
                    parent = grandPa;
                    break;
                }

            }
        }
    }
}

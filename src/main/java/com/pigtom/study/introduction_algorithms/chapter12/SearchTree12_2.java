package com.pigtom.study.introduction_algorithms.chapter12;

import org.junit.jupiter.api.Test;

public class SearchTree12_2 {
    /**
     * 12.2-2 写出TREE-MINIMUM 和 TREE-MAXIMUM的递归版本
     * treeMinimum的递归版本
     * 搜索树的最小节点一定在最左边那个节点上
     * <p>
     * 1. 将head节点当做父节点，如果父节点不为空，且父节点的左节点不为空
     * 则将左节点变成父节点,进入步骤1，如果head为空则返回空（这种情况只有在空树的时候发生）
     * 或者父节点的左节点为空，则父节点为最小节点，将父节点返回。
     *
     * @param head
     * @return
     */
    Node<Integer> treeMinimumRecurse(Node<Integer> head) {
        if (head == null || head.left == null) {
            return head;
        }
        head = head.left;
        return treeMinimumRecurse(head);
    }

    /**
     * 12.2.2 TREE_MAXMUM的递归版本
     * 搜索树的最大节点一定在最右边的那个节点上
     * <p>
     * 1.  将head作为父节点，如果父节点不为空，且父节点的右节点不为空，
     * 刚将父节点的右节点赋值结父节点。进入步骤1，如果父节点为空，则返回空
     * 或者父节点的右节点为空，则返回父节点。（此时父节点是最右边的一个节点）
     *
     * @param head
     * @return
     */
    Node<Integer> treeMaximumRecurse(Node<Integer> head) {
        if (head == null || head.right == null) {
            return head;
        }
        head = head.right;
        return treeMaximumRecurse(head);
    }

    /**
     * 12.2-3 写出TREE-PREDECESSOR的伪代码
     * 本题是涉汲了关于一个节点的前驱和后继
     * 前驱：小于给定节点的最大节点
     * 后继：大于给定节点的最小节点
     * <p>
     * 要求：找出给定节点的前驱（找到小于该节点的最大节点）
     * 如果该节点存在左节点，则该节点一定存在于左子树中，且为左子树中的最大者。
     * 证明：如果该节点存在左节点，则该节点一定存在于左子树中，且为左子树中的最大者（假定树中各元素各不相同）
     * <p>
     * 左子树中的所有节点均小于其父节点（搜索树的性质），
     * 如果祖父节点或祖先节点存在 那么父节点的祖先节点必定要么全部大于以父结点为根的树
     * 要么全部小于以父结点为根的树。（搜索树的性质，根大于所有左子树的节点，且小于所有右子节点。）
     * 如果该节点为父节点的右节点，则该父节点为其前驱。
     * （--最小节点没有前驱--）
     * <p>
     * 算法：
     * 如果给定的节点为空，刚返回空
     * 否则将给定的节点记作父节点
     * 1、如果父节点的左节点不为空，找出左子树中最大的节点。如果父节点的左节点为空。进入步骤2
     * 2、取出父节点的父节点，记作祖父节点。如果祖父为空返回空，父节点为祖父节点的右节点，返回祖父节点。
     * 如果父节点为祖父节点的左节点，说明该节点为最小节点，没有前驱，返回空
     */
    public Node<Integer> treePredecessor(Node<Integer> head) {
        // 给定的结点为空
        if (head == null) {
            return null;
        }
        // 左子树不为空，找到左子树中最大的结点
        if (head.left != null) {
            return treeMaximumRecurse(head.left);
        }
        // 将父节点的父节点赋值给祖父节点
        Node<Integer> grandPa = head.parent;

        // 祖父节点为空或者祖节点的右节点为父节点，则返回祖节点
        if (grandPa == null || grandPa.right == head) {
            return grandPa;
        }
        // 父节点为祖父节点的左节点，父节点为最小的节点，没有前驱，返回空
        return null;
    }

    // 12.2=5
    /**
     * 12.2-5 证明：如果一棵二叉搜索树中的一个结点有两个孩子，那么它的后继没有左孩子，它的前驱没有右孩子。
     *
     * 1、在之前我们证明过如果一个结点有左孩子，那么它的那的前驱一定为左子树中的最大者。
     *    而一棵树中的最大者必定没有右孩子(用反证法证明，如果它有右孩子，它就是不是最大的，自相矛盾）
     *    所以它的前驱没有右孩子
     * 2、如有一个结点有右孩子，那么它的后继一定为右子树中最小的那个节点。
     *    而一棵树中的最小者必定没有左孩子。（同样反证法：如果它有左孩子那么它就不是最小的，自相矛盾。）
     */

    // 12.2-6
    /**
     * 12.2-6 考虑一棵二叉搜索树T，其关键字互不相同。证明：如果T中一个结点x的右子树为空，且x有一个后继y，
     *        那么y一定是最底层祖先，并且其左孩子也是x的祖先。（注意到，每个结点都是它自己的祖先。）
     *
     * 结点x的右子树为空，说明其后继存在于其祖先。因为其左子树必定小于结点x。
     * 1、将结点x记作node
     *
     * 2、如果结点node是其父结点的左结点，因为父结点的右结点必定大于父结点，则父结点为结点node的后继。父结点是大于结点x的最小结点。
     *    如果结点node是其父结点的右结点，则父结点小于结点node，则应该继续向上查找，将父结点记作node进入步骤2.
     *    直到node
     *   将node父结点记作父结点
     *   由于以node为根的子树为父结点的左子树，所以父结点大于结点x。
     *   又由于以父结点为根的子树中，父结点是大于结点x的最小结点。
     *   所以 y一定是最底层祖先，并且其左孩子也是x的祖先。
     */

    // 12.2-7
    /**
     * 12.2-7 对于一棵有n个结点的二叉搜索树，有另一个方法来实现中序遍历，先调用TREE-MINIMUM找到这棵树中的最小元素
     *        ，然后再调用n-1次的TREE-SUCCESSOR。证明：该算法的运行时间为Θ(n)。
     *
     * 证明：TREE-MINIMUM的时间复杂度为Θ(lgn)，得到最小元素之后，调用TREE-SUCCESSOR需要Θ（1）的时间，n-1次调用
     *       时间为Θ（n-1），所有整个算法的运行时间为Θ（n）
     *
     */

    // 12.2-8
    /**
     * 12.2.8 证明在一棵高度为h的二叉搜索树中，不论从哪个结点开始，k次连续的TREE-SUCCESSOR调用时间为O（k+h）。
     *
     * 证明：TODO
     */

    // 12.2-9
    /**
     * 12.2-9 设T是一棵二叉搜索树，其关键字互不相同；设x是一个叶结点，y为其父结点。证明：y.key或者是T树中大于x.key的最小关键字
     *        或者是小于x.key的最大关键字。
     *
     * 证明: 假设x为y的左结点，则y的关键字是大于x关键字的最小关键字（y的祖先结点要么大于全部以y为根的子树的结点，要么全部小于），
     * 只有y才是大于x的最小结点。
     *      或者x为y的右结点，则y的关键字是小于y关键字的最大关键字（同理 y的祖先结点要么全部大于以y为根的子树的结点，要么全部小于）
     * 只有y才是小于x的最大结点。
     */


    /**
     * 测试前序遍历的正确性
     */
    @Test
    public void testPredecessor() {
        Node<Integer> head = Node.buildSearchTree(50);
        SearchTree12_1 searchTree12_1 = new SearchTree12_1();
        searchTree12_1.inorderVisitNoRecurse(head);
        System.out.println("******************");
        System.out.println("head.key = " + head.key);
        System.out.println(treePredecessor(head).key);
    }
}

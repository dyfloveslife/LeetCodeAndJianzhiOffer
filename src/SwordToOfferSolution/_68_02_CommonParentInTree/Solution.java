package SwordToOfferSolution._68_02_CommonParentInTree;

/*
 * 二叉树的最近公共祖先
 *
 * 题目描述：
 * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
 *
 * 思路 0：（重要）
 * 0. 设节点 root 为节点 p、q 的某公共祖先，若其左子节点 root.left 和右子节点 root.right 都不是 p、q 的公共祖先，
 *    则称 root 是 “最近的公共祖先”；
 * 1. 如果 node 是 p 和 q 的最近公共祖先，则只可能是以下情况之一：
 *    1.1) p 和 q 在 node 的子树中，且分别位于 node 的两侧；
 *    1.2) p = node，且 q 在 node 的左子树或右子树中；
 *    1.3) q = node，且 p 在 node 的左子树或右子树中；
 * 2. 结合“最近的公共祖先”和后序遍历的概念，即可解决该题。
 *
 * 思路 1：
 *  1. 从根节点开始一直找到输入的两个节点，这需要经过两条路径；
 *  2. 可以将这两条路径中对应的值进行比较，如果相等，则后移指针再次进行比较；
 *  3. 假如在比较的过程中发现值不相同，则前一个相同的值就是这俩输入节点的最低公共祖先。
 *  4. 缺点：需要额外的辅助空间。
 *
 * 思路 2：
 *  1. 如果每个节点都有指向父节点的指针的话，则该问题就可以转化为求两个链表的第一个公共节点；
 *  2. 假设输入的两个节点分别为 F 和 H，则 F 所在的链表为 F->D->B->A，H 所在的链表为 H->E->B->A;
 *  3. 则这两个节点的第一个公共节点就是它们的最低公共祖先。
 *
 * 思路 3：（下面 3 个例子都是围绕这种方法进行说明的）
 *  1. 从根节点开始遍历，在访问到当前节点的时候，需要将当前节点的值返回给父节点；
 *  2. 在遍历的时候，如果某个节点得到了来自左子树的非空值以及来自右子树的非空值的话，则该节点就是最低公共祖先；
 *  3. 然后再将节点的值返回给父节点。
 *  4. O(n)。
 *
 * 例子 1：
 *  1. 如图所示：https://i.loli.net/2020/01/10/Xxd4jMc2Pai1krL.png
 *  2. 假设要求 2 和 5 的最低公共节点；
 *  3. 首先判断根节点 3 是不是 2 或者 5，由于不是，所以我们扩展到 3 的左子树继续判断；
 *  4. 来到 6，6 也不是 2 或 5，所以再来到左边的 2；
 *  5. 此时，我们找到了 2，因此节点 2 向 6 返回一个节点值，即 2，由于节点 2 没有孩子，所以来到 6 的右子树；
 *  6. 来到 11，11 不是 2 或 5，然后来到 9，9 也不符合，由于 9 没有孩子，所以节点 9 返回给 11 一个 null；
 *  7. 来到 11 的右子树 5，5 返回给 11 一个节点值，即 5。此时对于 11 来说，不是 2 和 5 的最低公共祖先，因为 11 的左边返回的是 null，而不是 2；
 *  8. 所以将不是 null 值的节点值 5 返回给节点 6；
 *  9. 此时 6 就得到了来自左侧非空值 2，以及右侧非空值 5，则 6 就是 2 和 5 的最低公共祖先，然后节点 6 再将自身的节点值返回给 3；
 * 10. 对于 3 来说，虽然得到了来自左侧的 6，但是它现在还不知道我们发现了最低公共祖先，所以还需要继续访问 3 的右子树上的节点；
 * 11. 最终，节点 3 的右侧会得到一个 null。此时 3 左侧得到了一个非空值，右侧得到了一个空值，则 3 就知道了最低公共祖先一定在 3 的左侧；
 * 12. 这里是可以优化的，因为在节点 6 将值返回给节点 3 的时候，由于返回的值既不是 2 也不是 5，此时就可以终止查找，不用再判断节点 3 的右子树了，
 *     反而可以直接得到最终的最低公共祖先，即节点 6.
 *
 * 例子 2：
 *  1. 如图所示：https://i.loli.net/2020/01/10/3jo74CtFcKZ1H8i.png
 *  2. 假设要找到 8 和 11 的最低公共祖先；
 *  3. 和上面的例子一样，从节点 3 开始，到节点 2 的时候会返回给节点 6 一个 null；
 *  4. 然后到节点 11 的时候，节点 11 将值返回给 6，此时节点 6 得到了左侧的 null 以及右侧的 11，此时 6 不是最低公共祖先，然后将非空值 11 返回给节点 3；
 *  5. 来到节点 8，此时节点 8 是 8 和 11 中其中一个，也就是说，节点 8 与输入的节点 8 和 11 中的 8 相等，所以 8 返回给节点 3 一个值，即 8；
 *  6. 此时节点 3 得到了来自左侧的非空值 11，以及来自右侧的非空值 8，因此节点 3 就是 8 和 11 的最低公共祖先。
 *
 * 例子 3：
 *  1. 如图所示：https://i.loli.net/2020/01/10/XJPGmQEb17hy8eB.png
 *  2. 假设要找到 8 和 7 的最低公共祖先；
 *  3. 首先还是从节点 3 开始，由于左侧没有 8 和 7，所以节点 3 的左侧最终会得到一个 null；
 *  4. 然后来到节点 3 的右孩子 8，由于 8 是输入节点 8 和 7 当中的 8，所以节点 3 的右侧会得到一个值，即 8；
 *  5. 此时节点 3 得到了来自左侧的 null，以及右侧的非空值 8，所以这就意味着节点 8 是 8 和 7 的最低公共祖先。
 *
 * 总结：
 * 1. 通过递归对二叉树进行后序遍历，当遇到节点 p 和 q 时返回。从底至顶回溯，当节点 p 和 q 在 root 的异侧时，
 *    节点 root 即为最近公共祖先，则向上返回 root。
 * 2. 具体流程：https://i.loli.net/2020/05/10/hVgcGXwqMBojNRE.png
 */
public class Solution {
    static class TreeNode {
        private int val;
        private TreeNode left;
        private TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        // 如果当前节点 root 为 p 或 q 中的一个
        // 则当前节点 root 就是最低公共祖先
        if (root == q || root == p) {
            return root;
        }

        // left 和 right 将最终的结果给返回
        TreeNode left = lowestCommonAncestor1(root.left, p, q);
        TreeNode right = lowestCommonAncestor1(root.right, p, q);
        // 当前节点得到了来自左侧非空节点的值以及右侧非空节点的值
        // 此时当前节点就是最低公共祖先
        if (left != null && right != null) {
            return root;
        }
        // 检查左子树或右子树是否是最低公共祖先
        // 如果左子树不为空，则最低的公共祖先就来自左子树
        // 如果右子树不为空，则最低的公共祖先就来自右子树
        return left != null ? left : right;
    }

    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }

        if (root == p || root == q) {
            return root;
        }

        TreeNode left = lowestCommonAncestor2(root.left, p, q);
        TreeNode right = lowestCommonAncestor2(root.right, p, q);
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        return root;
    }
}

package SwordToOfferSolution._27_MirrorOfBinaryTree;

import java.util.LinkedList;
import java.util.Queue;

/*
 * 求二叉树的镜像
 *
 * 题目描述：
 * 请完成一个函数，输入一个二叉树，该函数输出它的镜像。
 *
 * 思路：
 * 0. 使用递归的方式；
 * 1. 前序遍历树中的每个节点，如果遍历到的节点有子节点，则交换两个子节点；
 * 2. 当交换完所有非叶节点的左右子节点的时候，就可以得到二叉树的镜像。
 * 3. 使用队列的方式，套一个 BFS 的模板，然后在当前节点的左右孩子入队之前，交换当前出队节点的左右孩子。
 */
public class Solution {
    static class TreeNode {
        private int val;
        private TreeNode left;
        private TreeNode right;

        private TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * 递归
     *
     * @param root TreeNode
     * @return TreeNode
     */
    public TreeNode mirrorTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        // 将当前节点的左右子树交换
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        mirrorTree(root.left);
        mirrorTree(root.right);
        return root;
    }

    /**
     * 迭代（队列）
     *
     * @param root TreeNode
     * @return TreeNode
     */
    public TreeNode mirrorTree2(TreeNode root) {
        if (root == null) {
            return null;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            // 每次从队列中拿出一个节点，并交换这个节点的左右孩子
            TreeNode tmp = queue.poll();
            TreeNode left = tmp.left;
            tmp.left = tmp.right;
            tmp.right = left;

            if (tmp.left != null) {
                queue.offer(tmp.left);
            }
            if (tmp.right != null) {
                queue.offer(tmp.right);
            }
        }

        return root;
    }
}

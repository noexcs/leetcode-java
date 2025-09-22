package org.noexcs.basic.algo.tree;

import java.util.ArrayDeque;
import java.util.HashMap;


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

public class AVLTreeChecker {

    // 检查是否为AVL树：BST且平衡
    public boolean isAVL(TreeNode root) {
        if (root == null) return true;

        // 1. 检查BST性质：迭代中序遍历
        if (!isBST(root)) {
            return false;
        }

        // 2. 检查平衡：迭代后序遍历计算高度并检查平衡因子
        return isBalanced(root);
    }

    // 迭代中序遍历检查BST
    private boolean isBST(TreeNode root) {
        if (root == null)
            return true;
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;
        TreeNode pre = null;

        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            if (pre != null && pre.val >= cur.val) {
                return false;
            }
            pre = cur;
            cur = cur.right;
        }
        return true;
    }


    // 迭代后序遍历计算高度并检查平衡
    private boolean isBalanced(TreeNode root) {
        if (root == null) return true;

        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        ArrayDeque<Integer> statusStack = new ArrayDeque<>();
        HashMap<Integer, Integer> height = new HashMap<>();

        stack.push(root);
        statusStack.push(0);

        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            Integer status = statusStack.peek();

            if (status == 0) {
                statusStack.pop();
                statusStack.push(1);
                if (node.left != null) {
                    stack.push(node.left);
                    statusStack.push(0);
                }
            } else if (status == 1) {
                statusStack.pop();
                statusStack.push(2);
                if (node.right != null) {
                    stack.push(node.right);
                    statusStack.push(0);
                }
            } else {
                // status == 2
                stack.pop();
                statusStack.pop();

                int leftHeight = node.left == null ? 0 : height.get(node.left.val);
                int rightHeight = node.right == null ? 0 : height.get(node.right.val);

                if (Math.abs(leftHeight - rightHeight) > 1) {
                    return false;
                }

                height.put(node.val, Math.max(leftHeight, rightHeight) + 1);
            }
        }

        return true;
    }
}
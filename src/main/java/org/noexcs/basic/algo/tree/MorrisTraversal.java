package org.noexcs.basic.algo.tree;


public class MorrisTraversal {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void morris(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode cur = root;
        TreeNode mostRight;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }
            cur = cur.right;
        }
    }

    public boolean preorder(TreeNode root) {
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left != null) {
                TreeNode mostRight = cur.left;
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    System.out.println(cur.val);
                    mostRight.right = cur;
                    cur = cur.left;
                }
                if (mostRight.right == cur) {
                    mostRight.right = null;
                    cur = cur.right;
                }
            } else {
                System.out.println(cur.val);
                cur = cur.right;
            }
        }
        return true;
    }

    public boolean inorder(TreeNode root) {
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left != null) {
                TreeNode mostRight = cur.left;
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                }
                if (mostRight.right == cur) {
                    System.out.println(cur.val);
                    mostRight.right = null;
                    cur = cur.right;
                }
            } else {
                System.out.println(cur.val);
                cur = cur.right;
            }
        }
        return true;
    }

    public boolean postorder(TreeNode root) {
        TreeNode cur = root;
        TreeNode re = new TreeNode(0);
        while (cur != null) {
            TreeNode mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != re) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    re.left = cur.left;
                    re.right = cur;
                    mostRight.right = re;
                    cur = cur.left;
                }
                if (mostRight.right == re) {
                    System.out.println(cur.val);
                    mostRight.right = null;
                    cur = cur.right;
                }
            } else {
                System.out.println(cur.val);
                if (cur.right == re) {
                    cur = cur.right.right;
                } else {
                    cur = cur.right;
                }
            }
        }
        return true;
    }


    public static void main(String[] args) {
        // 构建测试二叉树
        //       4
        //      / \
        //     2   5
        //    / \
        //   1   3
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);

        System.out.println("Morris preorder traversal:");
        MorrisTraversal morris = new MorrisTraversal();
        morris.preorder(root);
        System.out.println("===");
        morris.postorder(root);
    }
}

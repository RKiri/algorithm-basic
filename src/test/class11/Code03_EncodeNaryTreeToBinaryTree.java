package test.class11;

import java.util.LinkedList;
import java.util.List;

public class Code03_EncodeNaryTreeToBinaryTree {
    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    ;

    // 提交时不要提交这个类
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    class Codec {
        //将每个节点的孩子们节点放到左子树的右分支上
        TreeNode encode(Node node) {
            if (node == null) {
                return null;
            }
            TreeNode head = new TreeNode(node.val);
            head.left = en(node.children);
            return head;
        }
        //头节点都是同一个

        //将树排好返回
        TreeNode en(List<Node> children) {
            TreeNode head = null;
            TreeNode cur = null;
            for (Node child : children) {//将孩子节点们循环遍历处理右边界 分配左节点
                TreeNode node = new TreeNode(child.val);
                if (head == null) {//第一个节点
                    head = node;//头固定死
                } else {
                    cur.right = node;//一直向右移的当前节点
                }
                cur = node;
                cur.left = en(child.children);//递归将每次遍历的孩子节点的孩子节点们处理好
            }
            return head;
        }

        //头节点值不变 只需将孩子们的队列建出来
        Node decode(TreeNode node) {
            if (node == null) {
                return null;
            }
            return new Node(node.val, de(node.left));
        }

        List<Node> de(TreeNode node) {
            List<Node> children = new LinkedList<>();
            while (node != null) {//如果不为空 递归建立新的多节点树
                Node cur = new Node(node.val, de(node.left));
                children.add(cur);//建完后将其添加到孩子节点队列
                node = node.right;//一直向右节点遍历 把右子树全遍历完
            }
            return children;
        }

    }

}

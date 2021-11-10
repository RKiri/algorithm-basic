package class11;

import java.util.ArrayList;
import java.util.List;

// 本题测试链接：https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree
public class Code03_EncodeNaryTreeToBinaryTree {

	// 提交时不要提交这个类
	public static class Node {//多叉树 孩子是一个列表
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
	};

	// 提交时不要提交这个类
	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	// 只提交这个类即可
	//将每个节点的孩子们节点放到左子树的右分支上
	class Codec {
		// Encodes an n-ary tree to a binary tree.
		public TreeNode encode(Node root) {
			if (root == null) {
				return null;
			}
			TreeNode head = new TreeNode(root.val);//头节点都是同一个
			head.left = en(root.children);
			return head;
		}

		private TreeNode en(List<Node> children) {//将树排好返回
			TreeNode head = null;
			TreeNode cur = null;
			for (Node child : children) {//将孩子节点们循环遍历处理右边界 分配左节点
				TreeNode tNode = new TreeNode(child.val);
				if (head == null) {//第一个节点
					head = tNode;//头固定死
				} else {
					cur.right = tNode;//一直向右移的当前节点
				}
				cur = tNode;
				cur.left = en(child.children);//递归将每次遍历的孩子节点的孩子节点们处理好
			}
			return head;
		}

		// Decodes your binary tree to an n-ary tree.
		public Node decode(TreeNode root) {
			if (root == null) {
				return null;
			}
			return new Node(root.val, de(root.left));//头节点值不变 只需将孩子们的队列建出来
		}

		public List<Node> de(TreeNode root) {
			List<Node> children = new ArrayList<>();
            //如果不为空 递归建立新的多节点树
            //建完后将其添加到孩子节点队列
            //一直向右节点遍历 把右子树全遍历完
			while (root != null) {
				Node cur = new Node(root.val, de(root.left));
				children.add(cur);
				root = root.right;
			}
			return children;
		}

	}

}

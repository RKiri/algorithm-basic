package test.class12;

import java.util.LinkedList;
import java.util.Queue;

public class Code01_IsCBT {//是否是完全二叉树

    class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    boolean isCBT1(Node head) {
        if (head == null) {
            return true;
        }
        // 是否遇到过左右两个孩子不双全的节点
        boolean leaf = false;
        //当遇到一个不双全的节点后应全是叶节点
        Queue<Node> queue = new LinkedList<>();
        //按层遍历
        queue.add(head);
        Node l = null;
        Node r = null;
        Node cur = null;
        while (!queue.isEmpty()) {
            cur = queue.poll();
            l = cur.left;
            r = cur.right;
            // 如果遇到了不双全的节点之后，又发现当前节点不是叶节点
            //左边为空 右边不为空
            if (leaf && ((l != null) || (r != null)) || (l == null && r != null)) {
                return false;
            }
            if (l != null) {
                queue.add(l);
            }
            if (r != null) {
                queue.add(r);
            }
            if (l == null && r == null) {
                leaf = true;
            }
        }
        return true;
    }

}

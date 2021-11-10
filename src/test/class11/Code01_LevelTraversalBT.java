package test.class11;

import java.util.LinkedList;
import java.util.Queue;

public class Code01_LevelTraversalBT {
    static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    static void level(Node head) {
        if (head == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        Node cur = null;
        while (!queue.isEmpty()) {
            cur = queue.poll();
            System.out.print(cur.value + " ");
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node node = new Node(1);
        node.left = new Node(2);
        node.right = new Node(3);
        node.left.left = new Node(4);
        node.left.right = new Node(5);
        node.right.left = new Node(6);
        node.right.right = new Node(7);
        level(node);
    }
}

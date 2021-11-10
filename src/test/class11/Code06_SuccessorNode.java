package test.class11;

public class Code06_SuccessorNode {
    static class Node {
        int value;
        Node left;
        Node right;
        Node parent;

        public Node(int value) {
            this.value = value;
        }
    }

    static Node getSuccessorNode(Node node) {
        if (node == null) {
            return null;
        }
        // 分两种情况
        if (node.right != null) {//如果他有右子树 那么是他右子树最左侧的点
            return getLeftMost(node.right);
        } else {
            // 无右子树 如果他是他父节点的左孩子 就是这个父节点
            while (node.parent != null && node.parent.right == node) {
                // 当前节点是其父亲节点右孩子
                //且不为空 还没有到头节点 继续向上遍历
                node = node.parent;
            }
            return node.parent;
        }


    }

    static Node getLeftMost(Node node) {
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }


    public static void main(String[] args) {
        Node head = new Node(6);
        head.parent = null;
        head.left = new Node(3);
        head.left.parent = head;
        head.left.left = new Node(1);
        head.left.left.parent = head.left;
        head.left.left.right = new Node(2);
        head.left.left.right.parent = head.left.left;
        head.left.right = new Node(4);
        head.left.right.parent = head.left;
        head.left.right.right = new Node(7);
        head.left.right.right.parent = head.left.right;
        head.right = new Node(9);
        head.right.parent = head;
        head.right.left = new Node(8);
        head.right.left.parent = head.right;
        head.right.left.left = new Node(7);
        head.right.left.left.parent = head.right.left;
        head.right.right = new Node(10);
        head.right.right.parent = head.right;

        Node test = head.left.left;//
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left.left.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left.right.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right.left.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test));//null
    }
}

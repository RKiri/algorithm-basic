package test.class10;

import java.util.Stack;

public class Code03_UnRecursiveTraversalIBT {
    static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    static void pre(Node head) {
        if (head != null) {
            System.out.print("pre_order:");
            //利用栈先进后出
            Stack<Node> stack = new Stack<>();
            stack.add(head);
            while (!stack.isEmpty()) {//栈添加弹出完
                //弹出后需要先看是否有子树 才能弹他的兄弟节点
                head = stack.pop();
                System.out.print(head.value + " ");
                if (head.right != null) {//想要先弹左侧的 需要先将同级别右侧的加入
                    stack.add(head.right);
                }
                if (head.left != null) {
                    stack.add(head.left);
                }
            }
        }
        System.out.println();
    }

    static void in(Node cur) {
        //1、当前节点cur 整条左边界进栈，直到空
        //2、栈中弹出节点打印，节点右子树为cur
        //3、栈为空停止
        //栈全弹完并且树也全添加完
        //因为整颗树可以只由左边界分解掉
        //先把左边界压倒栈里，一直到空，此时左边没有子树，将节点弹出，
        // 此时已经把左中弹完了，此时再往上就是父节点的父节点，
        // 但要先将此时父节点的右树弹完才能再往上，弹出一个节点时，
        // 每个节点可能有右树，将右树按照左边界从顶到底压栈，不搞完右树不会弹上面的，
        // 此时相对顺序时正确的
        Stack<Node> stack = new Stack<>();
        System.out.print("in-order:");
        while (!stack.isEmpty() || cur != null) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
                System.out.print(cur.value + " ");
                cur = cur.right;
            }
        }
        System.out.println();
    }

    static void pos1(Node head) {
        System.out.print("pos-order:");
        if (head != null) {
            Stack<Node> s1 = new Stack<>();
            Stack<Node> s2 = new Stack<>();
            s1.push(head);
            while (!s1.isEmpty()) {
                head = s1.pop();
                s2.push(head);
                if (head.left != null) {
                    s1.push(head.left);
                }
                if (head.right != null) {
                    s1.push(head.right);
                }
            }
            while (!s2.isEmpty()) {
                head = s2.pop();
                System.out.print(head.value + " ");
            }
        }
        System.out.println();
    }

    static void pos2(Node h) {
        System.out.print("pos-order:");
        //查看当前栈顶元素
        Stack<Node> stack = new Stack<>();
        stack.push(h);
        while (!stack.isEmpty()) {
            Node cur = stack.peek();
            //如果左子树不为空并且没有弹出过
            // （只要h在左右任一说明左右子树都被弹出过,可视为空）
            // 将其添加进去
            if (cur.left != null && cur.left != h && cur.right != h) {
                stack.push(cur.left);
            } else if (cur.right != null && cur.right != h) {
                //查看右子树 只要右子树不为h说明没有弹出过 可添加
                stack.push(cur.right);
            } else {
                //当左右子树都判断完 都已经弹出过或为空后 可将头节点弹出 并记录当前弹出节点
                System.out.print(stack.pop().value + " ");
                h = cur;//h表示该元素已经弹出过
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
        pre(node);
        System.out.println("===============");
        in(node);
        System.out.println("===============");
        pos1(node);
        System.out.println("===============");
        pos2(node);
    }
}

package class10;

import java.util.Stack;

public class Code03_UnRecursiveTraversalBT {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static void pre(Node head) {
        System.out.print("pre-order: ");
        if (head != null) {
            Stack<Node> stack = new Stack<Node>();//栈先进后出
            stack.add(head);
            while (!stack.isEmpty()) {
                head = stack.pop();//弹出后需要先看是否有子树 才能弹他的兄弟节点
                System.out.print(head.value + " ");//先序遍历 先弹出头节点 然后看是否有左右子树
                if (head.right != null) {//想要先弹左侧的 需要先将同级别右侧的加入
                    stack.push(head.right);
                }
                if (head.left != null) {//然后将左子树加入
                    stack.push(head.left);
                }
            }
        }
        System.out.println();
    }

    public static void in(Node cur) {
        //1、当前节点cur 整条左边界进栈，直到空
        //2、栈中弹出节点打印，节点右子树为cur
        //3、栈为空停止
        System.out.print("in-order: ");
        if (cur != null) {
            Stack<Node> stack = new Stack<Node>();
            while (!stack.isEmpty() || cur != null) {//栈全弹完并且树也全添加完
                if (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                } else {
                    cur = stack.pop();
                    System.out.print(cur.value + " ");
                    cur = cur.right;
                }
            }
        }
        System.out.println();
    }

    public static void pos1(Node head) {
        System.out.print("pos-order: ");
        if (head != null) {
            Stack<Node> s1 = new Stack<Node>();
            Stack<Node> s2 = new Stack<Node>();
            s1.push(head);
            while (!s1.isEmpty()) {
                head = s1.pop(); // 头 右 左
                s2.push(head);
                if (head.left != null) {
                    s1.push(head.left);
                }
                if (head.right != null) {
                    s1.push(head.right);
                }
            }
            // 左 右 头
            while (!s2.isEmpty()) {
                System.out.print(s2.pop().value + " ");
            }
        }
        System.out.println();
    }

    public static void pos2(Node h) {
        System.out.print("pos-order: ");
        if (h != null) {
            Stack<Node> stack = new Stack<Node>();
            stack.push(h);
            Node c = null;
            while (!stack.isEmpty()) {
                c = stack.peek();//查看当前栈顶元素
                if (c.left != null && h != c.left && h != c.right) {
                    //如果左子树不为空并且没有弹出过（只要h在左右任一说明左右子树都被弹出过）
                    // 将其添加进去
                    stack.push(c.left);
                } else if (c.right != null && h != c.right) {
                    //查看右子树 只要右子树不为h说明没有弹出过 可添加
                    stack.push(c.right);
                } else {//当左右子树都判断完 都已经弹出过或为空后 可将头节点弹出 并记录当前弹出节点
                    System.out.print(stack.pop().value + " ");
                    h = c;//h表示该元素已经弹出过
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        pre(head);
        System.out.println("========");
        in(head);
        System.out.println("========");
        pos1(head);
        System.out.println("========");
        pos2(head);
        System.out.println("========");
    }

}

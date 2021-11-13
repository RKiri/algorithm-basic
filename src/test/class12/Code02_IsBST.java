package test.class12;

import java.util.ArrayList;

public class Code02_IsBST {
    //*假设一X位头节点 假设可以向X左树和X右树要任何信息
    //*在这个假设下 讨论一X为头结点的树 （考虑其中是否有和X相不相关）得到答案的可能性
    //X比左树最大值要大，比右树最小值要小
    //左右子树都是平衡的
    //必须和X有关
    //*列出所有可能性后，确定需要想左树和右树要什么样的信息Info
    //*写递归函数 返回Info
    //最大值 最小值 是否平衡
    //*考虑当前节点为空的情况 base case 是否能直接写出 不能的话返回null 下面做对null的判断
    //无法直接写出 null
    //*写代码 考虑将左树右树信息整合出整棵树的信息 然后返回
    static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static boolean isBST1(Node head) {
        if (head == null) {
            return true;
        }
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);//将二叉树按中序遍历生成数组集合
        for (int i = 1; i < arr.size(); i++) {//循环查看是否有后一个比前一个小
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return false;//有的话就不是搜索二叉树
            }
        }
        return true;
    }

    public static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    static boolean isBST2(Node head) {
        if (head == null) {
            return true;
        }
        return process(head).isBST;
    }

    static class Info {
        int max;
        int min;
        boolean isBST;

        public Info(int max, int min, boolean isBST) {
            this.max = max;
            this.min = min;
            this.isBST = isBST;
        }
    }

    static Info process(Node head) {
        if (head == null) {
            return null;
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int max = head.value;
        int min = head.value;
        if (leftInfo != null) {
            max = Math.max(leftInfo.max, max);
            min = Math.min(leftInfo.min, min);
        }
        if (rightInfo != null) {
            max = Math.max(rightInfo.max, max);
            min = Math.min(rightInfo.min, min);
        }
        boolean isBST = true;
        if (leftInfo != null && leftInfo.max >= head.value) {//等于也不可以
            isBST = false;
        }
        if (rightInfo != null && rightInfo.min <= head.value) {
            isBST = false;
        }
        if (leftInfo != null && !leftInfo.isBST) {
            isBST = false;
        }
        if (rightInfo != null && !rightInfo.isBST) {
            isBST = false;
        }
        return new Info(max, min, isBST);
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isBST1(head) != isBST2(head)) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("finish!");
    }
}

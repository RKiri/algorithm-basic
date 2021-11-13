package test.class12;

public class Code03_IsBalanced {
    //是否是平衡二叉树
    //*假设一X位头节点 假设可以向X左树和X右树要任何信息
    //*在这个假设下 讨论一X为头结点的树 （考虑其中是否有和X相不相关）得到答案的可能性
    //每一棵子树左树最大高度和右树最大高度相差的绝对值不超过1
    //左右子树是不是平衡的
    //必须和X有关
    //*列出所有可能性后，确定需要想左树和右树要什么样的信息Info
    //*写递归函数 返回Info
    //是否平衡 树的高度
    //*考虑当前节点为空的情况 base case 是否能直接写出 不能的话返回null 下面做对null的判断
    //true 0
    //*写代码 考虑将左树右树信息整合出整棵树的信息 然后返回
    static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static boolean isBalanced1(Node head) {
        boolean[] ans = new boolean[1];
        ans[0] = true;
        process1(head, ans);
        return ans[0];
    }

    public static int process1(Node head, boolean[] ans) {
        if (!ans[0] || head == null) {//false 如果已经有不符合的情况 直接返回
            return -1;
        }
        int leftHeight = process1(head.left, ans);//递归调用
        int rightHeight = process1(head.right, ans);
        if (Math.abs(leftHeight - rightHeight) > 1) {//比较高度绝对值是否大于1
            ans[0] = false;
        }
        return Math.max(leftHeight, rightHeight) + 1;//返回左右子树长的高度
    }

    static boolean isBalanced2(Node head) {
        if (head == null) {
            return true;
        }
        return process(head).isBalanced;
    }

    static class Info {
        boolean isBalanced;
        int hight;

        public Info(boolean isBalanced, int hight) {
            this.isBalanced = isBalanced;
            this.hight = hight;
        }
    }

    static Info process(Node head) {
        if (head == null) {
            return new Info(true, 0);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        boolean isBalanced = true;
        if (!leftInfo.isBalanced) {
            isBalanced = false;
        }
        if (!rightInfo.isBalanced) {
            isBalanced = false;
        }
        if (Math.abs(leftInfo.hight - rightInfo.hight) > 1) {
            isBalanced = false;
        }
        int hight = Math.max(leftInfo.hight, rightInfo.hight) + 1;
        return new Info(isBalanced, hight);
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
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isBalanced1(head) != isBalanced2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}

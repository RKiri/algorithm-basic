package test.class12;

public class Code04_IsFull {//是否是满二叉树
    //*假设一X位头节点 假设可以向X左树和X右树要任何信息
    //*在这个假设下 讨论一X为头结点的树 （考虑其中是否有和X相不相关）得到答案的可能性

    //查看节点的总数量是否是2的高度次方-1
    //*列出所有可能性后，确定需要想左树和右树要什么样的信息Info
    //*写递归函数 返回Info
    //节点数量 高度
    //*考虑当前节点为空的情况 base case 是否能直接写出 不能的话返回null 下面做对null的判断
    //info(0,0)
    //*写代码 考虑将左树右树信息整合出整棵树的信息 然后返回
    static class Node {
        int val;
        Node left;
        Node right;

        public Node(int val) {
            this.val = val;
        }
    }

    public static boolean isFull1(Node head) {
        if (head == null) {
            return true;
        }
        int height = h(head);
        int nodes = n(head);
        return (1 << height) - 1 == nodes;
    }

    public static int h(Node head) {
        if (head == null) {
            return 0;
        }
        return Math.max(h(head.left), h(head.right)) + 1;//递归调用返回大的高度
    }

    public static int n(Node head) {
        if (head == null) {
            return 0;
        }
        return n(head.left) + n(head.right) + 1;//递归调用 将每个左右节点都加在一起 再加上头节点为整棵树的节点数
    }

    static boolean isFull2(Node head) {
        if (head == null) {
            return true;
        }
        return (1 << process(head).height) - 1 == process(head).nodes;
    }

    static class Info {
        int nodes;
        int height;

        public Info(int nodes, int height) {
            this.nodes = nodes;
            this.height = height;
        }
    }

    static Info process(Node head) {
        if (head == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int nodes = leftInfo.nodes + rightInfo.nodes + 1;
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        return new Info(nodes, height);
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
            if (isFull1(head) != isFull2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}

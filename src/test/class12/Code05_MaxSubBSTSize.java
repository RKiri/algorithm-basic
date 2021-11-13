package test.class12;

import java.util.ArrayList;

public class Code05_MaxSubBSTSize {
    //*假设一X位头节点 假设可以向X左树和X右树要任何信息
    //*在这个假设下 讨论一X为头结点的树 （考虑其中是否有和X相不相关）得到答案的可能性

    //找到整棵树中最大的搜索二叉子树节点数量
    //分和X是否有关
    //无关 比较左右子树搜索二叉子树节点数量
    //有关 整体就是搜索二叉树
    //判断需要左侧是否是搜索二叉树
    //左子树最大值
    //右子树最小值
    //返回整个树的节点数量

    //*列出所有可能性后，确定需要想左树和右树要什么样的信息Info
    //*写递归函数 返回Info
    //搜索二叉子树节点数量 是否是搜索二叉树 最大值 最小值 整个树的节点数量
    //*考虑当前节点为空的情况 base case 是否能直接写出 不能的话返回null 下面做对null的判断
    //null
    //*写代码 考虑将左树右树信息整合出整棵树的信息 然后返回
    static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static int getBSTSize(Node head) {//判断二叉树是否是搜索二叉树 是的话返回(数组链表大小)节点数量 不是的话返回0
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return 0;
            }
        }
        return arr.size();
    }

    public static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    public static int maxSubBSTSize1(Node head) {
        if (head == null) {
            return 0;
        }
        int h = getBSTSize(head);
        if (h != 0) {//等于零说明当前节点并不是搜索二叉树 继续递归向下看
            //如果是的话 直接返回当前大小
            return h;
        }
        return Math.max(maxSubBSTSize1(head.left), maxSubBSTSize1(head.right));//看左右子树是否存在 取大的
    }

    static int maxSubBSTSize2(Node head) {
        if (head == null) {
            return 0;
        }
        return process(head).maxSubBSTSize;
    }

    static class Info {
        int maxSubBSTSize;
        //boolean isBST; 可用最大搜索二叉树节点数量和整个数节点数量比较得出
        int max;
        int min;
        int allSize;

        public Info(int maxSubBSTSize, int max, int min, int allSize) {
            this.maxSubBSTSize = maxSubBSTSize;
            this.max = max;
            this.min = min;
            this.allSize = allSize;
        }
    }

    static Info process(Node head) {
        if (head == null) {
            return null;
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int allSize = 1;//
        int max = head.value;
        int min = head.value;
        if (leftInfo != null) {
            max = Math.max(leftInfo.max, max);
            min = Math.min(leftInfo.min, min);
            allSize += leftInfo.allSize;//
        }
        if (rightInfo != null) {
            max = Math.max(rightInfo.max, max);
            min = Math.min(rightInfo.min, min);
            allSize += rightInfo.allSize;
        }
        int p1 = -1;
        if (leftInfo != null) {
            p1 = Math.max(p1, leftInfo.maxSubBSTSize);
        }
        int p2 = -1;
        if (rightInfo != null) {
            p2 = Math.max(p2, rightInfo.maxSubBSTSize);
        }
        int p3 = -1;
        //先判断左右子树是否是搜索二叉树 注意 为空也要算进去 所以不能用if单独的判断
        boolean leftBST = leftInfo == null ? true : leftInfo.maxSubBSTSize == leftInfo.allSize;
        boolean rightBST = rightInfo == null ? true : rightInfo.maxSubBSTSize == rightInfo.allSize;
        if (leftBST && rightBST) {
            //注意可能会为空 值为0
            boolean leftMaxLess = leftInfo == null ? true : leftInfo.max < head.value;
            boolean rightMinMore = rightInfo == null ? true : rightInfo.min > head.value;
            if (leftMaxLess && rightMinMore) {
                int leftSize = leftInfo == null ? 0 : leftInfo.allSize;
                int rightSize = rightInfo == null ? 0 : rightInfo.allSize;
                p3 = leftSize + rightSize + 1;
            }
        }
        int maxSubBSTSize = Math.max(Math.max(p1, p2), p3);
        return new Info(maxSubBSTSize, max, min, allSize);
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
            if (maxSubBSTSize1(head) != maxSubBSTSize2(head)) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("finish!");
    }

}

package test.class12;

import java.util.LinkedList;
import java.util.Queue;

public class Code01_IsCBT {//是否是完全二叉树

    static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    static boolean isCBT1(Node head) {
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
        while (!queue.isEmpty()) {
            head = queue.poll();
            l = head.left;
            r = head.right;
            // 如果遇到了不双全的节点之后，又发现当前节点不是叶节点
            //左边为空 右边不为空
            if ((leaf && (l != null || r != null)) || (l == null && r != null)) {
                return false;
            }
            if (l != null) {
                queue.add(l);
            }
            if (r != null) {
                queue.add(r);
            }
            if (l == null || r == null) {//当其中一个为空时 后面就是叶子节点 不需要&&  也可只由右节点来判断r == null
                leaf = true;
            }
        }
        return true;
    }

    //*假设一X位头节点 假设可以向X左树和X右树要任何信息
    //*在这个假设下 讨论一X为头结点的树 （考虑其中是否有和X相不相关）得到答案的可能性
    //四种情况 左右子树满的 高度一致
    //左子树满 右子树完全 高度一致
    //左子树满 右子树满 左高度为右高度+1
    //左完全 右满 左高度大一
    //必须和X有关
    //*列出所有可能性后，确定需要想左树和右树要什么样的信息Info
    //*写递归函数 返回Info
    //是否满（可看左右子树都是满的且高度一致来判断） 是否完全 高度
    //*考虑当前节点为空的情况 base case 是否能直接写出 不能的话返回null 下面做对null的判断
    //info(true,true,0)
    //*写代码 考虑将左树右树信息整合出整棵树的信息 然后返回
    static boolean isCBT2(Node head) {
        return process(head).isCBT;
    }

    static class Info {
        boolean isFull;
        boolean isCBT;
        int height;

        public Info(boolean isFull, boolean isCBT, int height) {
            this.isFull = isFull;
            this.isCBT = isCBT;
            this.height = height;
        }
    }

    static Info process(Node head) {
        if (head == null) {
            return new Info(true, true, 0);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        boolean isFull = (leftInfo.isFull && rightInfo.isFull) && (leftInfo.height == rightInfo.height);
        boolean isCBT = false;
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        if (isFull) {
            isCBT = true;
        } else if (leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
            isCBT = true;
        } else if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isCBT = true;
        } else if (leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isCBT = true;
        }
        return new Info(isFull, isCBT, height);
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
            if (isCBT1(head) != isCBT2(head)) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("finish!");
    }
}

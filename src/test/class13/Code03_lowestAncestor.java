package test.class13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Code03_lowestAncestor {
    //*假设一X位头节点 假设可以向X左树和X右树要任何信息
    //*在这个假设下 讨论一X为头结点的树 （考虑其中是否有和X相不相关）得到答案的可能性
    //头节点公共祖先 现在要找最低
    //分两种情况和X是否有关
    //和X无关 在左子树上已经找到A 找到B
    //在右子树找到A和B
    //和X有关 X节点是其中一个 另一个在子树上
    //或一个在左子树 一个在右子树
    //*列出所有可能性后，确定需要想左树和右树要什么样的信息Info
    //*写递归函数 返回Info
    //是否有a 是否有b 最低公共祖先
    //*考虑当前节点为空的情况 base case 是否能直接写出 不能的话返回null 下面做对null的判断
    //info(false,false,null)
    //*写代码 考虑将左树右树信息整合出整棵树的信息 然后返回
    static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node lowestAncestor1(Node head, Node o1, Node o2) {
        if (head == null) {
            return null;
        }
        // key的父节点是value
        HashMap<Node, Node> parentMap = new HashMap<>();
        parentMap.put(head, null);
        fillParentMap(head, parentMap);
        HashSet<Node> o1Set = new HashSet<>();//简历o1的set
        Node cur = o1;
        o1Set.add(cur);
        while (parentMap.get(cur) != null) {//从o1开始一直向上将其祖先节点添加进set 一直到头节点
            cur = parentMap.get(cur);
            o1Set.add(cur);
        }
        cur = o2;
        while (!o1Set.contains(cur)) {//在set里 从o2开始一直向上找 第一个就是最低祖先节点
            cur = parentMap.get(cur);
        }
        return cur;
    }

    public static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {//递归将头节点开始整棵树和他的父节点添加到map里
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }

    static Node lowestAncestor2(Node head, Node a, Node b) {
        return process(head, a, b).ans;
    }

    static class Info {
        boolean findA;
        boolean findB;
        Node ans;

        public Info(boolean findA, boolean findB, Node ans) {
            this.findA = findA;
            this.findB = findB;
            this.ans = ans;
        }
    }

    static Info process(Node head, Node a, Node b) {
        if (head == null) {
            return new Info(false, false, null);
        }
        Info leftInfo = process(head.left, a, b);
        Info rightInfo = process(head.right, a, b);
        boolean findA = head == a || leftInfo.findA || rightInfo.findA;//同时要看当前节点是否是
        boolean findB = head == b || leftInfo.findB || rightInfo.findB;
        Node ans = null;
        if (leftInfo.ans != null) {//无关
            ans = leftInfo.ans;
        } else if (rightInfo.ans != null) {//else if 存在的话只会存在一种
            ans = rightInfo.ans;
        } else if (findA && findB) {//相关 因为在左右子树有最低公共祖先时 一定存在a和b 所以和x相关的最后做判断
            ans = head;
        }
        return new Info(findA, findB, ans);
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

    // for test
    public static Node pickRandomOne(Node head) {//获取随机节点
        if (head == null) {//*边界条件
            return null;
        }
        //定义数组
        ArrayList<Node> list = new ArrayList<>();
        //用先序遍历将其装满
        fillPrelist(head, list);
        //随机下标
        int randomIndex = (int) (list.size() * Math.random());
        //返回节点
        return list.get(randomIndex);
    }

    // for test
    public static void fillPrelist(Node head, ArrayList<Node> arr) {//先序遍历
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPrelist(head.left, arr);
        fillPrelist(head.right, arr);
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Node o1 = pickRandomOne(head);
            Node o2 = pickRandomOne(head);
            if (lowestAncestor1(head, o1, o2) != lowestAncestor2(head, o1, o2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}

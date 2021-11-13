package test.class12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Code06_MaxDistance {
    //*假设一X位头节点 假设可以向X左树和X右树要任何信息
    //*在这个假设下 讨论一X为头结点的树 （考虑其中是否有和X相不相关）得到答案的可能性

    //分两种情况
    //和X无关 比较左树和右树存在的最大距离
    //和X有关 获取左树、右树的高度 加在一起再加1
    //*列出所有可能性后，确定需要想左树和右树要什么样的信息Info
    //*写递归函数 返回Info
    //最大距离 高度
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

    public static int maxDistance1(Node head) {
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = getPrelist(head);//头节点先序遍历数组队列
        HashMap<Node, Node> parentMap = getParentMap(head);
        int max = 0;
        for (int i = 0; i < arr.size(); i++) {//所有节点两两比较 找到最大的
            for (int j = i; j < arr.size(); j++) {
                max = Math.max(max, distance(parentMap, arr.get(i), arr.get(j)));
            }
        }
        return max;
    }

    public static ArrayList<Node> getPrelist(Node head) {
        ArrayList<Node> arr = new ArrayList<>();
        fillPrelist(head, arr);
        return arr;
    }

    public static void fillPrelist(Node head, ArrayList<Node> arr) {//先序遍历
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPrelist(head.left, arr);
        fillPrelist(head.right, arr);
    }

    public static HashMap<Node, Node> getParentMap(Node head) {//将头节点和父节点添加到表里
        HashMap<Node, Node> map = new HashMap<>();
        map.put(head, null);
        fillParentMap(head, map);//递归将所有节点和父节点添加进map里
        return map;
    }

    public static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }

    public static int distance(HashMap<Node, Node> parentMap, Node o1, Node o2) {
        HashSet<Node> o1Set = new HashSet<>();
        Node cur = o1;
        o1Set.add(cur);
        while (parentMap.get(cur) != null) {//循环通过map获取他的全部父节点
            cur = parentMap.get(cur);
            o1Set.add(cur);
        }
        cur = o2;
        while (!o1Set.contains(cur)) {//循环判断set里是否有o2和他的祖先节点
            cur = parentMap.get(cur);//最晚也会在头节点相遇
        }
        Node lowestAncestor = cur;//最低的祖先
        cur = o1;
        int distance1 = 1;
        while (cur != lowestAncestor) {//从o1开始循环向上找最低的祖先节点 没有就向上跳 并且距离+1
            cur = parentMap.get(cur);
            distance1++;
        }
        cur = o2;
        int distance2 = 1;
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            distance2++;
        }
        return distance1 + distance2 - 1;//返回总距离（将加了两边的头节点去掉一个
    }

    static int maxDistance2(Node head) {
        if (head == null) {
            return 0;
        }
        return process(head).maxDistance;
    }

    static class Info {
        int maxDistance;
        int height;

        public Info(int maxDistance, int height) {
            this.maxDistance = maxDistance;
            this.height = height;
        }
    }

    static Info process(Node head) {
        if (head == null) {
            return new Info(0, 0);
            //因为知道空返回的值 所以下方不需要判断 可直接用
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int p1 = leftInfo.maxDistance;
        int p2 = rightInfo.maxDistance;
        int p3 = leftInfo.height + rightInfo.height + 1;
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int maxDistance = Math.max(Math.max(p1, p2), p3);
        return new Info(maxDistance, height);
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
            if (maxDistance1(head) != maxDistance2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}

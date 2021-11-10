package test.class11;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Code05_TreeMaxWidth {
    static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static int maxWidthUseMap(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        // key 在 哪一层，value
        HashMap<Node, Integer> levelMap = new HashMap<>();
        levelMap.put(head, 1);
        int curLevel = 1; // 当前你正在统计哪一层的宽度
        int curLevelNodes = 0; // 当前层curLevel层，宽度目前是多少
        int max = 0;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            int curNodeLevel = levelMap.get(cur);//查询当前弹出数的层数
            if (cur.left != null) {
                levelMap.put(cur.left, curNodeLevel + 1);
                queue.add(cur.left);
            }
            if (cur.right != null) {
                levelMap.put(cur.right, curNodeLevel + 1);
                queue.add(cur.right);
            }
            if (curNodeLevel == curLevel) {//弹出的数在当前层 宽度++
                curLevelNodes++;
            } else {//到下一层 层数++ 宽度为1
                max = Math.max(max, curLevelNodes);//比较记录下上一层的宽度
                curLevel++;
                curLevelNodes = 1;
            }
        }
        max = Math.max(max, curLevelNodes);//记录比较最后一层的宽度
        return max;
    }

    static int maxWidthNoMap(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        int max = 0;
        // 当前层，最右节点是谁
        Node curEnd = head;//
        // 下一层，最右节点是谁
        Node nextEnd = null;
        Node cur = null;
        int curLevelNode = 0;// 当前层的节点数
        while (!queue.isEmpty()) {
            cur = queue.poll();
            if (cur.left != null) {
                queue.add(cur.left);
                nextEnd = cur.left;//记录下当前下一层的最右节点
            }
            if (cur.right != null) {
                queue.add(cur.right);
                nextEnd = cur.right;
            }
            curLevelNode++;//当前层节点数++
            if (cur == curEnd) {//如果到达当前层最右节点
                //更新下max
                max = Math.max(max, curLevelNode);
                //当前层节点数归零
                curLevelNode = 0;
                //将下一层最右节点给当前层
                curEnd = nextEnd;
            }
        }
        return max;
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
        Node head = new Node((int) (Math.random() * (maxValue + 1)));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 10;
        int maxValue = 100;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxWidthNoMap(head) != maxWidthUseMap(head)) {
                System.out.println("错误");
                break;
            }
        }
        System.out.println("测试结束");
    }
}

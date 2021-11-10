package test.class11;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Code02_SerializeAndReconstructTree {
    static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    static Queue<String> preSerial(Node head) {
        if (head == null) {
            return null;
        }
        Queue<String> ans = new LinkedList<>();
        pre(head, ans);
        return ans;
    }

    static void pre(Node node, Queue<String> ans) {
        //空也要加进去
        if (node == null) {
            ans.add(null);
        } else {
            ans.add(String.valueOf(node.value));
            pre(node.left, ans);
            pre(node.right, ans);
        }
    }

    static Node buildByPreQueue(Queue<String> queue) {
        if (queue == null || queue.size() == 0) {
            return null;
        }
        return preb(queue);
    }

    static Node preb(Queue<String> queue) {
        String value = queue.poll();
        if (value == null) {
            return null;
        }
        Node head = new Node(Integer.valueOf(value));//Integer
        head.left = preb(queue);
        head.right = preb(queue);
        return head;
    }

    static Queue<String> posSerial(Node head) {
        if (head == null) {
            return null;
        }
        Queue<String> ans = new LinkedList<>();
        poss(head, ans);
        return ans;
    }

    static void poss(Node node, Queue<String> ans) {
        if (node == null) {
            ans.add(null);
        } else {
            poss(node.left, ans);
            poss(node.right, ans);
            ans.add(String.valueOf(node.value));
        }
    }

    static Node buildByPosQueue(Queue<String> queue) {
        if (queue == null || queue.size() == 0) {
            return null;
        }
        // 左右中  ->  stack(中右左)
        // 因为正常顺序先连左右子树 但还没有中 行不通
        Stack<String> stack = new Stack<>();
        while (!queue.isEmpty()) {
            stack.push(queue.poll());
        }
        return posb(stack);
    }

    static Node posb(Stack<String> stack) {
        String value = stack.pop();
        if (value == null) {
            return null;
        }
        Node head = new Node(Integer.valueOf(value));
        head.right = posb(stack);
        head.left = posb(stack);
        return head;
    }

    //在按层遍历的基础上添加序列化
    static Queue<String> levelSerial(Node head) {
        if (head == null) {
            return null;
        }
        Queue<String> ans = new LinkedList<>();
        ans.add(String.valueOf(head.value));
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            head = queue.poll();
            if (head.left != null) {
                ans.add(String.valueOf(head.left.value));//先往答案里添加
                queue.add(head.left);//然后往队列里添加 为下一次弹出做准备
            } else {
                ans.add(null); //空需要将null添加进ans
            }
            if (head.right != null) {
                ans.add(String.valueOf(head.right.value));//
                queue.add(head.right);
            } else {
                ans.add(null);
            }
        }
        return ans;
    }

    static Node buildByLevelQueue(Queue<String> levelList) {
        if (levelList == null || levelList.size() == 0) {
            return null;
        }
        Node head = generateNode(levelList.poll());
        //因为序列化先添加头节点 反序列化也现将头节点拿出
        Queue<Node> queue = new LinkedList<>();
        if (head != null) {//当head为空时 下面没有子节点 直接结束
            queue.add(head);
        }
        Node cur = null;
        while (!queue.isEmpty()) {
            cur = queue.poll();//弹出一个 父节点对子节点进行反序列化
            cur.left = generateNode(levelList.poll());
            cur.right = generateNode(levelList.poll());
            if (cur.left != null) {//不空添加到队列里 为下一轮的子节点反序列化
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
        return head;
    }

    static Node generateNode(String str) {
        if (str == null) {
            return null;
        }
        return new Node(Integer.valueOf(str));
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        //最大层数
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {//当前所在层数 建立随机树
        //当前层数超过最大层数 或随机
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node node = new Node((int) (Math.random() * (maxValue + 1)));
        //层数加一递归生成
        node.left = generate(level + 1, maxLevel, maxValue);
        node.right = generate(level + 1, maxLevel, maxValue);
        return node;
    }

    // for test
    public static boolean isSameValueStructure(Node head1, Node head2) {//是否是相同值和结构
        if (head1 == null ^ head2 == null) {
            return false;
        }
        if (head1 == null && head2 == null) {
            return true;
        }
        //比较当前值
        if (head1.value != head2.value) {
            return false;
        }
        //递归比较左右子树
        return isSameValueStructure(head1.left, head2.left) && isSameValueStructure(head1.right, head2.right);
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Queue<String> pre = preSerial(head);
            Queue<String> pos = posSerial(head);
            Queue<String> level = levelSerial(head);
            Node preBuild = buildByPreQueue(pre);
            Node posBuild = buildByPosQueue(pos);
            Node levelBuild = buildByLevelQueue(level);
            if (!isSameValueStructure(preBuild, posBuild) || !isSameValueStructure(posBuild, levelBuild)) {
                System.out.println("错误");
                break;
            }
        }
        System.out.println("测试结束");
    }
}

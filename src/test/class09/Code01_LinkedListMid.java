package test.class09;

import java.util.ArrayList;
import java.util.List;

public class Code01_LinkedListMid {
    static class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    static Node midOrUpMidNode(Node head) {
        //当节点小于三个时 按照要求可直接返回head
        if (head == null) {
            return head;
        }
        Node slow = head;
        Node fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    static Node midOrDownMidNode(Node head) {
        //小于两个
        if (head == null || head.next == null) {
            return head;
        }
        Node slow = head.next;//slow需要向后移一个，但奇数就过了 所以fast也往后移一个
        Node fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    static Node midPreOrUpmidPre(Node head) {//返回中点或上中点前一个
        //小于三个返回空
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node slow = head;//相当于slow往前挪一个节点 少跳一次
        Node fast = head.next.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    static Node midPreOrDownMidPreNode(Node head) {
        //小于两个
        if (head == null || head.next == null) {
            return null;
        }
        Node slow = head;
        Node fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static Node right1(Node head) {
        if (head == null) {
            return head;
        }
        Node cur = head;
        List<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 1) / 2);
    }

    public static Node right2(Node head) {
        if (head == null) {
            return head;
        }
        Node cur = head;
        List<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get(arr.size() / 2);
    }

    public static Node right3(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node cur = head;
        List<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 3) / 2);
    }

    public static Node right4(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        Node cur = head;
        List<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() / 2) - 1);
    }

    public static void main(String[] args) {
        Node node = null;
        node = new Node(1);
        node.next = new Node(2);
        node.next.next = new Node(3);
        node.next.next.next = new Node(4);
        node.next.next.next.next = new Node(5);
        node.next.next.next.next.next = new Node(6);
        node.next.next.next.next.next.next = new Node(7);
        node.next.next.next.next.next.next.next = new Node(8);

        System.out.println("测试开始");
        Node ans1 = null;
        Node ans2 = null;
        ans1 = midOrUpMidNode(node);
        ans2 = right1(node);
        if (ans1.value != ans2.value) {
            System.out.println("错误");
        }
        ans1 = midOrDownMidNode(node);
        ans2 = right2(node);
        if (ans1.value != ans2.value) {
            System.out.println("错误");
        }
        ans1 = midPreOrUpmidPre(node);
        ans2 = right3(node);
        if (ans1.value != ans2.value) {
            System.out.println("错误");
        }
        ans1 = midPreOrDownMidPreNode(node);
        ans2 = right4(node);
        if (ans1.value != ans2.value) {
            System.out.println("错误");
        }
        System.out.println("测试结束");
    }
}

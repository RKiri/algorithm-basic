package class09;

import java.util.HashMap;

// 测试链接 : https://leetcode.com/problems/copy-list-with-random-pointer/
public class Code04_CopyListWithRandom {

    public static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public static Node copyRandomList1(Node head) {
        // key 老节点
        // value 新节点
        HashMap<Node, Node> map = new HashMap<Node, Node>();//定义HashMap记录原节点和对应新节点
        Node cur = head;
        while (cur != null) {//循环添加
            map.put(cur, new Node(cur.val));
            cur = cur.next;
        }
        cur = head;
        while (cur != null) {//从头循环将新节点指向原节点指向对应的新节点
            // cur 老
            // map.get(cur) 新
            // 新.next ->  cur.next克隆节点找到
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);
            cur = cur.next;
        }
        return map.get(head);//返回新节点
    }

    public static Node copyRandomList2(Node head) {
        //建立HashMap是为了原节点和新节点的对应关系
        if (head == null) {
            return null;
        }
        //可以在原节点后插入新节点 同样能通过next找到
        Node cur = head;
        Node next = null;//定义一个next存储原节点的next
        // 1 -> 2 -> 3 -> null
        // 1 -> 1' -> 2 -> 2' -> 3 -> 3'
        while (cur != null) {//循环添加
            next = cur.next;
            cur.next = new Node(cur.val);
            cur.next.next = next;
            cur = next;
        }
        cur = head;
        Node copy = null;
        // 1 1' 2 2' 3 3'
        // 依次设置 1' 2' 3' random指针
        while (cur != null) {
            next = cur.next.next;
            copy = cur.next;
            copy.random = cur.random != null ? cur.random.next : null;
            cur = next;
        }
        Node res = head.next;
        cur = head;
        // 老 新 混在一起，next方向上，random正确
        // next方向上，把新老链表分离
        while (cur != null) {
            //先记录原节点的next 和原节点的copy
            // 原节点调整 copy调整
            //下移
            next = cur.next.next;
            copy = cur.next;
            cur.next = next;
            copy.next = next != null ? next.next : null;
            cur = next;
        }
        return res;
    }

}

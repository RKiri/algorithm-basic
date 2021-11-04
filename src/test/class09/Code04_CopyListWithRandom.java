package test.class09;

import java.util.HashMap;

public class Code04_CopyListWithRandom {
    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    class Solution {
        public Node copyRandomList(Node head) {
            //定义HashMap记录原节点和对应新节点
            HashMap<Node, Node> map = new HashMap<>();
            Node cur = head;
            while (cur != null) {
                map.put(cur, new Node(cur.val));
                cur = cur.next;
            }
            //从头循环将新节点指向原节点指向对应的新节点
            cur = head;
            while (cur != null) {
                map.get(cur).next = map.get(cur.next);
                map.get(cur).random = map.get(cur.random);
                cur = cur.next;
            }
            //返回新节点
            return map.get(head);
        }

        public Node copyRandomList1(Node head) {
            if (head == null) {//边界条件 防止空指针
                return null;
            }
            //建立HashMap是为了原节点和新节点的对应关系
            //可以在原节点后插入新节点 同样能通过.next找到
            //定义一个next存储原节点的next
            Node cur = head;
            Node next = null;
            // 1 -> 2 -> 3 -> null
            // 1 -> 1' -> 2 -> 2' -> 3 -> 3'
            //循环添加
            while (cur != null) {
                next = cur.next;//加到前面 防止空指针
                cur.next = new Node(cur.val);
                cur.next.next = next;
                cur = next;
            }
            // 1 1' 2 2' 3 3'
            // 依次设置 1' 2' 3' random指针
            cur = head;
            Node copy = null;
            while (cur != null) {
                copy = cur.next;
                copy.random = cur.random != null ? cur.random.next : null;
                cur = cur.next.next;
            }
            // 老 新 混在一起，next方向上，random正确
            // next方向上，把新老链表分离
            //记录最终返回的复制头节点
            Node ans = head.next;
            cur = head;
            //先记录原节点的next 和原节点的copy
            while (cur != null) {
                copy = cur.next;
                cur.next = copy.next;
                copy.next = cur.next != null ? copy.next.next : null;
                cur = cur.next;
            }
            // 原节点调整 copy调整
            //下移
            return ans;
        }
    }
}

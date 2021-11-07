package test.class10;

public class Code01_FindFirstIntersectNode {
    static class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    static Node getIntersectNode(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        if (loop1 == null && loop2 == null) {
            return noLoop(head1, head2);
        }
        if (loop1 != null && loop2 != null) {
            return bothLoop(head1, head2, loop1, loop2);
        }
        return null;
    }

    //如果有环, 找到链表第一个入环节点返回，如果无环，返回null
    //佛洛依德判环
    static Node getLoopNode(Node head) {
        //代码习惯 为了防止下方空指针
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        //用快慢指针 循环当快慢指针相同时停下
        //如果快指针指向空 说明已经到尾 没有环
        Node slow = head.next;
        Node fast = head.next.next;
        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        //快指针移动到头节点 每次走一步 和慢指针一起走
        //相等碰到时 为第一个入环节点
        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    //分情况讨论
    //1、如果两个链表均无环 相交问题
    //2、其中一个有换相交问题 不可能相交
    //3、都有环相交问题

    // 如果两个链表都无环，返回第一个相交节点，如果不相交，返回null
    static Node noLoop(Node head1, Node head2) {
        //防止空指针
        if (head1 == null || head2 == null) {
            return null;
        }
        //记录两个链表长度差值
        int n = 0;
        Node cur1 = head1;
        Node cur2 = head2;
        //循环找到两个链表最后节点
        while (cur1.next != null) {
            n++;
            cur1 = cur1.next;
        }
        while (cur2.next != null) {
            n--;
            cur2 = cur2.next;
        }
        //最后一个节点内存地址不一样 不可能相交
        if (cur1 != cur1) {
            return null;
        }
        //重定向1为长的 2为短的
        cur1 = n > 0 ? head1 : head2;
        cur2 = cur1 != head1 ? head1 : head2;
        //绝对值
        n = Math.abs(n);//
        //让长的先往下走完差值
        while (n != 0) {
            n--;
            cur1 = cur1.next;
        }
        //一起走 会在第一个相交节点相遇 返回
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    //讨论两个有环链表的相交问题 如果相交一定是共用同一个环 分三种情况
    //1、两个各自独立的有环链表
    //2、两个有环链表的入环节点是同一个节点
    //3、两个有环链表的入环节点不是同一个节点
    //第二种情况最好区分 当loop1==loop2时 就是第二种情况
    //但要找的时第一个相交节点 此时和环没有关系 但一定在入环节点前
    // 可将入环节点设为终止位置 变为求无环链表的相交问题
    //接下来只剩情况一和三 让loop往下转一圈回到自己
    // 如果遇到loop2 就是情况三 返回loop1或loop2均可 都是相对的第一个相交节点
    //没有遇到就是情况三
    static Node bothLoop(Node head1, Node head2, Node loop1, Node loop2) {
        Node cur1 = head1;
        Node cur2 = head2;
        if (loop1 == loop2) {
            int n = 0;
            while (cur1 != loop1) {
                n++;
                cur1 = cur1.next;
            }
            while (cur2 != loop2) {
                n--;
                cur2 = cur2.next;
            }
            cur1 = n > 0 ? head1 : head2;
            cur2 = cur1 != head1 ? head1 : head2;
            n = Math.abs(n);
            while (n > 0) {
                n--;
                cur1 = cur1.next;
            }
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        } else {
            cur1 = loop1.next;
            while (cur1 != loop1) {
                if (cur1 == loop2) {
                    return loop1;
                }
                cur1 = cur1.next;
            }
            return null;
        }
    }

    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next;
        System.out.println(getIntersectNode(head1, head2).value);//返回值

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next.next = head1.next.next.next;

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next;
        System.out.println(getIntersectNode(head1, head2).value);

        // 0->9->8->6->7->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = new Node(6);
        head2.next.next.next.next = new Node(4);
        head2.next.next.next.next.next = new Node(5);
        head2.next.next.next.next.next.next = head1.next.next.next.next.next;
        System.out.println(getIntersectNode(head1, head2).value);

    }
}

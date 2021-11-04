package test.class09;

import java.util.Stack;

public class Code02_IsPalindromeList {
    static class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    //将整个链表放到栈里 栈先进后出 将栈内元素依次弹出 和原链表比较判断
    static boolean isPalindrome1(Node head) {
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        while (head != null) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    //获取中点后一个 或者 后中点
    //用快慢指针 中点和前中点 再整体后移一位
    static boolean isPalindrome2(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        Node slow = head;
        Node fast = head;
        if (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        slow = slow.next;
        Stack<Node> stack = new Stack<>();
        while (slow != null) {
            stack.push(slow);
            slow = slow.next;
        }
        while (!stack.isEmpty()) {//将栈内所有元素弹出
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    static boolean isPalindrome3(Node head) {
        //边界条件
        if (head == null || head.next == null) {
            return true;
        }
        //先用快慢指针找到中点或前中点
        Node slow = head;
        Node fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Node pre = slow;
        Node cur = pre.next;
        //将这个点指向空 从这个点后开始逆序
        //逆序先调整第一个位置的数 然后循环调整后续
        //先存储第一个节点的next为cur 然后第一个节点指向空
        pre.next = null;
        Node next = null;
        //判断当前节点cur是否为空 不为空 记录next 当前节点cur指向上一个last
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        //next到pre 记录下 之后逆序回来用 cur到head
        next = pre;
        cur = head;
        boolean ans = true;
        //从两头依次遍历比较值是否相同 直到有一个为空
        while (pre != null && cur != null) {
            if (pre.value != cur.value) {
                ans = false;
                break;
            }
            pre = pre.next;
            cur = cur.next;
        }
        //从后重新逆序
        pre = next;
        cur = pre.next;
        pre.next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return ans;
    }

    public static void printLinkedList(Node node) {
        System.out.print("Linked List:");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = null;
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + "|");
        System.out.print(isPalindrome2(head) + "|");
        System.out.println(isPalindrome3(head));
        printLinkedList(head);
        System.out.println("=====================");

        head = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + "|");
        System.out.print(isPalindrome2(head) + "|");
        System.out.println(isPalindrome3(head));
        printLinkedList(head);
        System.out.println("=====================");

        head = new Node(1);
        head.next = new Node(2);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + "|");
        System.out.print(isPalindrome2(head) + "|");
        System.out.println(isPalindrome3(head));
        printLinkedList(head);
        System.out.println("=====================");

        head = new Node(1);
        head.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + "|");
        System.out.print(isPalindrome2(head) + "|");
        System.out.println(isPalindrome3(head));
        printLinkedList(head);
        System.out.println("=====================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + "|");
        System.out.print(isPalindrome2(head) + "|");
        System.out.println(isPalindrome3(head));
        printLinkedList(head);
        System.out.println("=====================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + "|");
        System.out.print(isPalindrome2(head) + "|");
        System.out.println(isPalindrome3(head));
        printLinkedList(head);
        System.out.println("=====================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + "|");
        System.out.print(isPalindrome2(head) + "|");
        System.out.println(isPalindrome3(head));
        printLinkedList(head);
        System.out.println("=====================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(2);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + "|");
        System.out.print(isPalindrome2(head) + "|");
        System.out.println(isPalindrome3(head));
        printLinkedList(head);
        System.out.println("=====================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(2);
        head.next.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + "|");
        System.out.print(isPalindrome2(head) + "|");
        System.out.println(isPalindrome3(head));
        printLinkedList(head);
        System.out.println("=====================");
    }
}

package test.class09;

public class Code03_SmallerEqualBigger {
    static class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    static Node listPartition1(Node head, int pivot) {
        if (head == null) {
            return head;
        }
        //获取链表长度
        Node cur = head;
        int i = 0;
        while (cur != null) {
            i++;
            cur = cur.next;
        }
        //建立节点数组
        Node[] nodes = new Node[i];
        //将链表节点添加到数组中
        cur = head;
        for (i = 0; i < nodes.length; i++) {
            nodes[i] = cur;
            cur = cur.next;
        }
        //按数组partition
        arrPartition(nodes, pivot);
        //将数组内节点全部连在一起
        for (i = 0; i < nodes.length - 1; i++) {
            nodes[i].next = nodes[i + 1];
        }
        //最后节点指向空
        nodes[i].next = null;
        //返回第一个节点
        return nodes[0];
    }


    //荷兰国旗 按值划分区域
    static void arrPartition(Node[] arr, int pivot) {
        int small = -1;
        int index = 0;
        int big = arr.length;
        while (index < big) {//index移动到big停止
            if (arr[index].value < pivot) {
                swap(arr, ++small, index++);
            } else if (arr[index].value == pivot) {
                index++;
            } else {
                swap(arr, --big, index);
            }
        }
    }

    static void swap(Node[] arr, int i, int j) {
        Node temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static Node listPartition2(Node head, int pivot) {
        //六个指针
        Node sH = null;
        Node sT = null;
        Node eH = null;
        Node eT = null;
        Node bH = null;
        Node bT = null;
        //直到将所有节点添加完
        while (head != null) {
            //先将next记录下
            Node next = head.next;
            head.next = null;
            if (head.value < pivot) {
                if (sH == null) {
                    sH = head;
                    sT = head;
                } else {
                    //原尾部指向新来的
                    sT.next = head;
                    //尾部向下移
                    sT = head;
                }
            } else if (head.value == pivot) {
                if (eH == null) {
                    eH = head;
                    eT = head;
                } else {
                    eT.next = head;
                    eT = head;
                }
            } else {
                if (bH == null) {
                    bH = head;
                    bT = head;
                } else {
                    bT.next = head;
                    bT = head;
                }
            }
            //head移动到记录的下一个节点
            head = next;
        }

        // 小于区域的尾巴，连等于区域的头，等于区域的尾巴连大于区域的头
        // 如果有小于区域
        if (sT != null) {
            sT.next = eH;//不用管头节点 指向空无所谓
            eT = eT == null ? sT : eT;// 下一步，谁去连大于区域的头，谁就变成eT
        }
        // 下一步，一定是需要用eT 去接 大于区域的头
        // 有等于区域，eT -> 等于区域的尾结点
        // 无等于区域，eT -> 小于区域的尾结点
        // eT 尽量不为空的尾巴节点 因为当等于区域为空时
        //已经将小于区域尾巴赋给等于区域尾巴
        //除非小于 等于区域均为空
        // 如果小于区域和等于区域，不是都没有
        if (eT != null) {
            eT.next = bH;
        }
        return sH != null ? sH : (eH != null ? eH : bH);
    }

    public static void printLinkedList(Node node) {
        System.out.print("Linked list:");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node node = new Node(7);
        node.next = new Node(9);
        node.next.next = new Node(1);
        node.next.next.next = new Node(8);
        node.next.next.next.next = new Node(5);
        node.next.next.next.next.next = new Node(2);
        node.next.next.next.next.next.next = new Node(5);

        printLinkedList(node);
        node = listPartition1(node, 4);
        //node = listPartition2(node, 5);
        printLinkedList(node);

    }
}

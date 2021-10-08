package test.class03;

public class Code02_DeleteGivenValue {
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    class Solution {
        public ListNode removeElements(ListNode head, int val) {
            while (head != null) {//防止全为指定数导致空指针异常
                if (head.val != val) {
                    break;
                }
                head = head.next;
            }
            ListNode pre = head;
            ListNode cur = head;
            while (cur != null) {
                if (cur.val != val) {
                    if (pre != cur) {//当cur、pre不为head时进入
                        if (pre.next != cur) {//如果中间有跳过的需要将指针修改
                            pre.next = cur;
                        }
                        pre = cur;
                    }
                }
                cur = cur.next;//如果是则直接指向下一个 不能先指 防止null
            }
            if (pre != null) {
                pre.next = null;
            }
            return head;
        }
    }
}

package test.class03;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Code03_DoubleEndsQueueToStackAndQueue {
    //定义双链表
    static class Node<T> {
        Node next;
        Node last;
        T value;

        public Node(T value) {
            this.value = value;
        }
    }

    //定义双向链表方法
    static class DoubleEndsQueue<T> {
        Node<T> head;
        Node<T> tail;

        //从头加
        void addFromHead(T value) {//3->2->1 往里加的是值
            Node<T> cur = new Node<T>(value);
            if (head == null) {
                head = cur;
                tail = cur;
            } else {
                head.last = cur;
                cur.next = head;
                head = cur;
            }
        }

        //从尾加
        void addFromBottom(T value) {//1->2->3
            Node<T> cur = new Node<T>(value);
            if (head == null) {
                head = cur;
                tail = cur;
            } else {
                tail.next = cur;
                cur.last = tail;
                tail = cur;
            }
        }

        //从头拿
        T popFromHead() {
            if (head == null) {
                return null;
            }
            Node<T> cur = head;//怎么都是拿这个
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                head = head.next;
                head.last = null;
                cur.next = null;
            }
            return cur.value;
        }

        //从尾拿
        T popFromTail() {
            if (head == null) {
                return null;
            }
            Node<T> cur = tail;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                tail = tail.last;
                tail.next = null;
                cur.last = null;
            }
            return cur.value;
        }

        //是否为空
        boolean isEmpty() {
            return head == null;
        }
    }

    //定义栈
    static class myStack<T> {
        DoubleEndsQueue<T> stack;

        public myStack() {
            this.stack = new DoubleEndsQueue<T>();
        }

        void push(T value) {
            stack.addFromHead(value);
        }

        T pop() {
            return stack.popFromHead();
        }

        boolean isEmpty() {
            return stack.isEmpty();
        }
    }

    //定义队列
    static class myQueue<T> {
        DoubleEndsQueue<T> queue;

        public myQueue() {
            this.queue = new DoubleEndsQueue<T>();
        }

        void push(T value) {
            queue.addFromHead(value);
        }

        T poll() {
            return queue.popFromTail();
        }

        boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    //是否相同
    static boolean isEqual(Integer o1, Integer o2) {
        if (o1 == null ^ o2 == null) {
            return false;
        }
        if (o1 == null && o2 == null) {
            return true;
        }
        return o1.equals(o2);
    }

    //测试
    public static void main(String[] args) {
        //定义往堆栈添加的数量
        int oneTestDataNum = 100;
        int value = 10000;
        int testTimes = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            //定义堆栈
            myStack<Integer> myStack = new myStack<>();
            myQueue<Integer> myQueue = new myQueue<>();
            Stack<Integer> stack = new Stack<>();
            Queue<Integer> queue = new LinkedList<>();
            for (int j = 0; j < oneTestDataNum; j++) {
                //定义添加的随机数
                int numS = (int) (Math.random() * value);
                if (stack.isEmpty()) {//判断是否为空
                    stack.push(numS);
                    myStack.push(numS);
                }
                if (Math.random() > 0.5) {//根据随机数概率往里添加元素
                    myStack.push(numS);
                    stack.push(numS);
                } else {
                    if (!isEqual(myStack.pop(), stack.pop())) {
                        System.out.println("错误S");
                        break;
                    }
                }
                int numQ = (int) (Math.random() * value);
                if (queue.isEmpty()) {
                    queue.offer(numQ);
                    myQueue.push(numQ);
                }
                if (Math.random() > 0.5) {
                    myQueue.push(numQ);
                    queue.offer(numQ);
                } else {//弹出 判断是否相同
                    if (!isEqual(myQueue.poll(), queue.poll())) {
                        System.out.println("错误Q");
                        break;
                    }
                }
            }
        }
        System.out.println("测试结束");
    }
}

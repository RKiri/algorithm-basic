package test.class03;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Code07_TwoQueueImplementStack {
    //两个队列构成一个栈
    static class twoQueueStack<T> {
        //定义class对象 成员变量Queue 辅助队列 构造函数初始化
        Queue<T> queue;
        Queue<T> help;

        public twoQueueStack() {
            this.queue = new LinkedList<>();
            this.help = new LinkedList<>();
        }

        //添加方法 直接向Queue里push
        void push(T value) {
            queue.offer(value);
        }

        //弹出方法 先将Queue里除最后一个添加进来的元素外 依次弹出添加进help队列
        T poll() {
            while (queue.size() > 1) {
                help.offer(queue.poll());
            }
            //从Queue最后弹出的即是所要的
            T ans = queue.poll();
            //然后将两个队列里的元素交换 这样下次可以继续重复执行上述方法
            Queue<T> temp = queue;
            queue = help;
            help = temp;
            return ans;
        }

        //peek
        T peek() {
            while (queue.size() > 1) {
                help.offer(queue.poll());
            }
            T ans = queue.poll();
            help.offer(ans);
            Queue<T> temp = queue;
            queue = help;
            help = temp;
            return ans;
        }

        //是否为空
        boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    public static void main(String[] args) {
        twoQueueStack<Integer> myStack = new twoQueueStack<>();
        Stack<Integer> stack = new Stack<>();
        int maxValue = 100;
        int testTimes = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            if (myStack.isEmpty()) {
                int num = (int) (Math.random() * maxValue);
                if (!stack.isEmpty()) {
                    System.out.println("isEmpty");
                    break;
                }
                myStack.push(num);
                stack.push(num);
            }
            if (Math.random() < 0.25) {
                int num = (int) (Math.random() * maxValue);
                myStack.push(num);
                stack.push(num);
            } else if (Math.random() < 0.5) {
                if (!myStack.peek().equals(stack.peek())) {
                    System.out.println("peek");
                    break;
                }
            } else if (Math.random() < 0.75) {
                if (!myStack.poll().equals(stack.pop())) {
                    System.out.println("pop");
                    break;
                }
            } else {
                if (myStack.isEmpty() != stack.isEmpty()) {
                    System.out.println("isEmpty2");
                }
            }
        }
        System.out.println("测试结束");
    }
}

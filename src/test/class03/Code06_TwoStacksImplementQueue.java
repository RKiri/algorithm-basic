package test.class03;

import java.util.Stack;

public class Code06_TwoStacksImplementQueue {
    //两个栈组成队列
    //定义类
    static class twoStacksQueue {//成员变量 一个添加栈 一个弹出栈
        Stack<Integer> stackPush;
        Stack<Integer> stackPop;

        public twoStacksQueue() {
            this.stackPush = new Stack<>();
            this.stackPop = new Stack<>();
        }

        //将添加栈里的元素弹出添加到弹出栈
        void pushToPop() {
            if (stackPop.isEmpty()) {//弹出栈为空时才能往里添加
                //当添加栈不为空时，循环将添加栈里元素依次弹出添加到弹出栈
                while (!stackPush.isEmpty()) {
                    stackPop.push(stackPush.pop());
                }
            }
        }

        //队列添加方法
        void push(int pushInt) {
            //将元素添加到添加栈
            stackPush.push(pushInt);
            //调倒数据方法
            pushToPop();
        }

        //弹出方法
        int pop() {
            //添加栈和弹出栈均为空 抛异常信息
            if (stackPush.isEmpty() && stackPop.isEmpty()) {
                throw new RuntimeException("Queue is Empty!");
            }
            pushToPop();//调倒数据方法
            return stackPop.pop();//将弹出栈元素弹出
        }

        //peek方法
        int peek() {
            if (stackPush.isEmpty() && stackPop.isEmpty()) {
                throw new RuntimeException("Queue is Empty");
            }
            pushToPop();
            return stackPop.peek();
        }
    }

    public static void main(String[] args) {
        twoStacksQueue queue = new twoStacksQueue();
        queue.push(1);
        queue.push(2);
        queue.push(3);
        System.out.println(queue.peek());
        System.out.println(queue.pop());
        System.out.println(queue.peek());
        System.out.println(queue.pop());
        System.out.println(queue.peek());
        System.out.println(queue.pop());
    }
}

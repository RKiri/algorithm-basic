package test.class03;

import java.util.Stack;

public class Code05_GetMinStack {
    //定义一个栈类
    static class myStack {
        //里面包含数据栈 和 最小栈
        Stack<Integer> dataStack;
        Stack<Integer> minStack;

        //构造函数初始化
        public myStack() {
            this.dataStack = new Stack<>();
            this.minStack = new Stack<>();
        }

        //添加方法
        void push(int newNum) {
            if (dataStack.isEmpty()) {//  如果数据栈为空 则直接向最小栈添加
                minStack.push(newNum);
            } else {//否则进行判断 如果小于等于最小方法获得的数 则添加
                if (newNum <= getMin()) {
                    minStack.push(newNum);
                }
            }
            dataStack.push(newNum);//最后向数据栈添加
        }

        //弹出方法
        int pop() {
            if (dataStack.isEmpty()) {
                throw new RuntimeException("Stack is Empty!");
            } else {//将数据栈弹出
                int value = dataStack.pop();
                if (value == getMin()) {//如果数据栈弹出的值等于最小方法获得的值
                    minStack.pop();//将最小栈也弹出
                }
                return value;
            }
        }

        //获取最小方法
        int getMin() {
            if (minStack.isEmpty()) { //  如果最小栈返回空 返回异常信息
                throw new RuntimeException("栈为空");
            } else {//否则peek最小栈
                return minStack.peek();
            }
        }
    }

    public static void main(String[] args) {
        myStack stack = new myStack();
        stack.push(3);
        System.out.println(stack.getMin());
        stack.push(4);
        System.out.println(stack.getMin());
        stack.push(1);
        System.out.println(stack.getMin());
        stack.pop();
        System.out.println(stack.getMin());
        stack.pop();
        System.out.println(stack.getMin());
//        stack.pop();
//        System.out.println(stack.getMin());
    }
}

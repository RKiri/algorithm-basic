package test.class06;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Code02_Heap {
    static class MyMaxHeap {//定义堆类 包含成员变量 heap int数组;limit限制 数组的大小;heapSize 堆实际的大小
        int[] heap;
        final int limit;
        int heapSize;

        public MyMaxHeap(int limit) {
            heap = new int[limit];
            this.limit = limit;
            heapSize = 0;
        }

        boolean isEmpty() {
            return heapSize == 0;
        }

        boolean isFull() {
            return heapSize == limit;
        }

        //将元素添加进来 并调整成大跟堆
        void push(int value) {
            if (heapSize == limit) {
                throw new RuntimeException("heap is full!");
            }
            heap[heapSize] = value;
            heapInsert(heap, heapSize++);
        }

        //弹出最大值 头节点 调整大跟堆 size--
        int pop() {
            int ans = heap[0];
            swap(heap, 0, --heapSize);
            heapify(heap, 0, heapSize);
            return ans;
        }

        //堆末尾添加一个元素 依次和自己的父节点比较 如果大则交换值、位置
        //一直到不比父节点大或者已经到0头节点
        void heapInsert(int[] arr, int index) {
            while (arr[index] > arr[(index - 1) / 2]) {//包含0时候的判断
                swap(arr, index, (index - 1) / 2);
                index = (index - 1) / 2;//
            }
        }

        //将元素下沉 与左右子树中较大的比较 如果没有父节点大结束 如果比父节点大交换 直到左右子树越界
        void heapify(int[] arr, int index, int heapSize) {
            int left = index * 2 + 1;
            while (left < heapSize) {
                int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
                if (arr[largest] < arr[index]) {
                    break;
                }
                swap(arr, largest, index);
                index = largest;
                left = index * 2 + 1;
            }
        }

        void swap(int[] arr, int a, int b) {
            int temp = arr[a];
            arr[a] = arr[b];
            arr[b] = temp;
        }
    }

    static class RightMaxHeap {
        int[] arr;
        int limit;
        int size;

        public RightMaxHeap(int limit) {
            arr = new int[limit];
            this.limit = limit;
            this.size = 0;
        }

        boolean isEmpty() {
            return size == 0;
        }

        boolean isFull() {
            return size == limit;
        }

        void push(int value) {
            if (size == limit) {
                throw new RuntimeException("heap is full!");
            }
            arr[size++] = value;
        }

        int pop() {
            int maxIndex = 0;
            for (int i = 1; i < size; i++) {
                if (arr[i] > arr[maxIndex]) {
                    maxIndex = i;
                }
            }
            int ans = arr[maxIndex];
            arr[maxIndex] = arr[--size];
            return ans;
        }
    }

    static class DCom implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }

    public static void main(String[] args) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(new DCom());
        heap.add(5);
        heap.add(3);
        System.out.println(heap.peek());
        heap.add(0);
        heap.add(7);
        System.out.println(heap.peek());
        System.out.println("*********");
        int value = 100;
        int limit = 100;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int curLimit = (int) (Math.random() * limit) + 1;
            MyMaxHeap heap1 = new MyMaxHeap(curLimit);
            RightMaxHeap heap2 = new RightMaxHeap(curLimit);
            int curOpTime = (int) (Math.random() * limit);
            for (int j = 0; j < curOpTime; j++) {
                if (heap1.isEmpty() != heap2.isEmpty()) {
                    System.out.println("isEmpty");
                    break;
                }
                if (heap1.isFull() != heap2.isFull()) {
                    System.out.println("isFull");
                }
                if (heap1.isEmpty()) {
                    int curValue = (int) (Math.random() * value);
                    heap1.push(curValue);
                    heap2.push(curValue);
                } else if (heap1.isFull()) {
                    if (heap1.pop() != heap2.pop()) {
                        System.out.println("pop");
                        break;
                    }
                } else {
                    if (Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * value);
                        heap1.push(curValue);
                        heap2.push(curValue);
                    } else {
                        if (heap1.pop() != heap2.pop()) {
                            System.out.println("pop2");
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("测试结束");
    }
}

package test.class14;

import java.util.PriorityQueue;

public class Code02_LessMoneySplitGold {
    // 纯暴力！
    public static int lessMoney1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, 0);
    }

    // 等待合并的数都在arr里，pre之前的合并行为产生了多少总代价
    // arr中只剩一个数字的时候，停止合并，返回最小的总代价
    public static int process(int[] arr, int pre) {
        if (arr.length == 1) {//basecase
            return pre;
        }
        int min = Integer.MAX_VALUE;
        //将数字两两组合 然后递归调用 每次返回的值就是当前情况下的最小值 然后循环看下一对组合
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                min = Math.min(min, process(copyAndMergeTwo(arr, i, j), pre + arr[i] + arr[j]));
            }
        }
        return min;
    }

    public static int[] copyAndMergeTwo(int[] arr, int i, int j) {//拷贝数组 将i和j两位置数合并添加进去 去除原来的元素
        int[] ans = new int[arr.length - 1];
        int ansi = 0;
        for (int arri = 0; arri < arr.length; arri++) {
            if (arri != i && arri != j) {
                ans[ansi++] = arr[arri];
            }
        }
        ans[ansi] = arr[i] + arr[j];
        return ans;
    }

    static int lessMoney2(int[] arr) {
        //优先级队列 小根堆
        PriorityQueue<Integer> queue = new PriorityQueue<>();//Integer
        //循环将数组添加进堆
        for (int i = 0; i < arr.length; i++) {
            queue.add(arr[i]);
        }
        int ans = 0;
        int cur = 0;
        while (queue.size() > 1) {
            cur = queue.poll() + queue.poll();
            ans += cur;
            queue.add(cur);
        }
        return ans;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] ans = new int[(int) (Math.random() * (maxSize + 1))];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * (maxValue + 1));
        }
        return ans;
    }

    public static void main(String[] args) {
        int maxSize = 6;
        int maxValue = 1000;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            if (lessMoney1(arr) != lessMoney2(arr)) {
                System.out.println("错误");
                break;
            }
        }
        System.out.println("测试结束");

    }
}

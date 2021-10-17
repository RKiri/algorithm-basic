package test.class05;

import java.util.Stack;

public class Code03_QuickSortRecursiveAndUnrecursive {
    static void quickSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    static void process(int[] arr, int L, int R) {
        //从L开始加上随机值下标和R交换
        if (L >= R) {
            return;
        }
        swap(arr, (int) (L + Math.random() * (R - L + 1)), R);
        int[] equalArea = netherLandsFlag(arr, L, R);
        process(arr, L, equalArea[0] - 1);
        process(arr, equalArea[1] + 1, R);
    }

    static int[] netherLandsFlag(int[] arr, int L, int R) {
        if (L > R) {
            return new int[]{-1, -1};
        }
        if (L == R) {
            return new int[]{L, R};
        }
        int less = L - 1;
        int more = R;
        int index = L;
        while (index < more) {
            if (arr[index] < arr[R]) {
                swap(arr, index++, ++less);
            } else if (arr[index] == arr[R]) {
                index++;
            } else {
                swap(arr, index, --more);
            }
        }
        swap(arr, more, R);
        return new int[]{less + 1, more};
    }

    static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    //非递归版本 设置一个任务类 包含L R
    //定义一个栈 最开始将初始任务添加进去 循环直到栈为空
    //栈不为空 弹出 执行任务 然后得到返回的区间 判断是否有小于和大于区间
    //有的话将其添加进栈 继续循环
    static class Job {
        int L;
        int R;

        public Job(int l, int r) {
            L = l;
            R = r;
        }
    }

    static void quickSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        Stack<Job> stack = new Stack<>();
        int N = arr.length;
        swap(arr, (int) (Math.random() * N), N - 1);
        stack.push(new Job(0, N - 1));
        while (!stack.isEmpty()) {
            Job job = stack.pop();
            swap(arr, job.L + (int) (Math.random() * (job.R - job.L + 1)), job.R);
            int[] equalArea = netherLandsFlag(arr, job.L, job.R);
            if (equalArea[0] > job.L) {
                stack.push(new Job(job.L, equalArea[0] - 1));
            }
            if (equalArea[1] < job.R) {
                stack.push(new Job(equalArea[1] + 1, job.R));
            }
        }
    }

    static int[] generateRandomArray(int maxValue, int maxSize) {
        int[] arr = new int[(int) (Math.random() * (maxSize + 1))];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1) - Math.random() * (maxValue + 1));
        }
        return arr;
    }

    static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    static boolean isEqual(int[] arr1, int[] arr2) {
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1 == null ^ arr2 == null) {
            return false;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int maxValue = 100;
        int maxSize = 100;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxValue, maxSize);
            int[] arr2 = copyArray(arr1);
            quickSort1(arr1);
            quickSort2(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("错误");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}

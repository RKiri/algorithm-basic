package test.class04;

public class Code04_BiggerThanRightTwice {
    //在一个数组中，对于每个数num，求有多少个后面的数 * 2 依然<num，求总个数
    //大两倍数对 因为有序
    //定义一个下标 从mid+1开始 如果他的二倍小于第一个数 向后移动 直到不小于或者超出边界r
    //这样从mid+1到下标前一个数都能组成数对 下标减(m+1) 累加到总数里
    static int biggerTwice(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    static int process(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }
        int mid = l + ((r - l) >> 1);
        return process(arr, l, mid) + process(arr, mid + 1, r) + merge(arr, l, mid, r);
    }

    static int merge(int[] arr, int l, int m, int r) {
        int windowR = m + 1;
        int ans = 0;
        for (int i = l; i <= m; i++) {//将左组循环一遍
            while (windowR <= r && arr[i] > (arr[windowR] * 2)) {
                windowR++;
            }
            ans += windowR - m - 1;//每次循环完要累加进去
        }


        int p1 = l;
        int p2 = m + 1;
        int index = 0;
        int[] help = new int[r - l + 1];
        while (p1 <= m && p2 <= r) {
            help[index++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= m) {
            help[index++] = arr[p1++];
        }
        while (p2 <= r) {
            help[index++] = arr[p2++];
        }
        for (int i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
        return ans;
    }

    static int comparator(int[] arr) {
        if (arr == null) {
            return 0;
        }
        int ans = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > (arr[j] << 1)) {
                    ans++;
                }
            }
        }
        return ans;
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
        int maxValue = 5;
        int maxSize = 5;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxValue, maxSize);
            int[] arr2 = copyArray(arr1);
            if (biggerTwice(arr1) != comparator(arr2)) {
                System.out.println("错误");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}

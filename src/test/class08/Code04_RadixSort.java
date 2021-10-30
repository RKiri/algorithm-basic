package test.class08;

import java.util.Arrays;

public class Code04_RadixSort {
    static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        redixSort(arr, 0, arr.length - 1, maxBits(arr));
    }

    //获取当前数组最大数的位数
    static int maxBits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        int ans = 0;
        while (max != 0) {
            ans++;
            max /= 10;
        }
        return ans;
    }

    // arr[L..R]排序  ,  最大值的十进制位数digit 辅助数组生成需要末尾下标 所以传L、R
    static void redixSort(int[] arr, int L, int R, int digit) {
        // 有多少个数准备多少个辅助空间
        int[] help = new int[R - L + 1];
        int j = 0;
        // 有多少位就进出几次
        for (int d = 1; d <= digit; d++) {
            // 准备一个固定长度数组 循环数组 储存当前位 每种数字出现的次数count[0..9]
            int[] count = new int[10];
            for (int i = L; i <= R; i++) {
                j = getDigit(arr[i], d);
                count[j]++;
            }
            for (int i = 1; i < 10; i++) {//转换成前缀和数组
                count[i] = count[i] + count[i - 1];
            }
            //从最右端开始
            //因为知道区间 就能确定最右端的数就在区间最后一个
            for (int i = R; i >= L; i--) {
                //获取第i为第d位置的数
                //将arr[i] 放在查看在count中数量 -1的位置
                //count该位置--
                j = getDigit(arr[i], d);
                help[--count[j]] = arr[i];
            }
            for (int i = 0; i < arr.length; i++) {
                arr[i] = help[i];
            }
            //将此轮排好序的辅助数组放回arr
        }

    }


    //返回x的第d位的值
    static int getDigit(int x, int d) {
        //pow()返回 x 的 d 次幂的值。 double
        return (x / ((int) Math.pow(10, d - 1))) % 10;
    }

    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
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

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int maxSize = 100;
        int maxValue = 100;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            radixSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("错误");
                break;
            }
        }
        System.out.println("测试结束");
    }
}

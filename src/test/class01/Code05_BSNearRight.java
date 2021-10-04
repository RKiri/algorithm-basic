package test.class01;

import java.util.Arrays;

public class Code05_BSNearRight {
    static int nearestIndex(int[] arr, int value) {
        //定义L R
        int L = 0;
        int R = arr.length - 1;
        //定义返回位置
        int index = -1;
        //while L<R
        while (L <= R) {//=
            //定义mid
            int mid = L + ((R - L) >> 1);
            //如果mid<=value L = mid+1
            if (arr[mid] <= value) {//arr[]
                //t = mid
                index = mid;
                L = mid + 1;
            } else {//mid>value R = mid-1
                R = mid - 1;
            }
        }
        //返回位置
        return index;
    }

    public static void main(String[] args) {
        int maxSize = 100;
        int maxValue = 10000;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int num = (int) ((maxValue + 1) * Math.random()) - (int) ((maxValue + 1) * Math.random());
            if (nearestIndex(arr, num) != test(arr, num)) {
                System.out.println("num " + num);
                printArray(arr);
                System.out.println(nearestIndex(arr, num));
                System.out.println(test(arr, num));
                break;
            }
        }
        System.out.println("测试结束");

    }

    static int[] generateRandomArray(int maxSize, int maxValue) {
        int length = (int) (Math.random() * (maxSize + 1));
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            //-maxValue到maxValue
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    static int test(int[] arr, int value) {
        int index = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] <= value) {
                index = i;
            }
        }
        return index;
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
}

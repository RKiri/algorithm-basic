package test.class06;


import java.util.Arrays;
import java.util.PriorityQueue;

public class Code04_SortArrayDistanceLessK {
    static void sortArrayDistanceLessK(int[] arr, int K) {
        if (K == 0) {
            return;
        }
        // 默认小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        // 0...K-1 从0开始将K范围内的添加到堆里 取K和最后一个下标的最小值
        int index = 0;
        for (; index <= Math.min(arr.length - 1, K); index++) {
            heap.add(arr[index]);
        }
        int i = 0;
        for (; index < arr.length; index++, i++) {
            //将堆弹出 最小的放到数组最开始位置 剩余元素继续添加进堆 依次循环 直到剩余元素加完
            arr[i] = heap.poll();
            heap.add(arr[index]);
        }
        //依次将剩余堆弹出
        while (i < arr.length) {
            arr[i++] = heap.poll();//i++ 上面i加后还没有添加元素
        }
    }

    // for test
    public static void comparator(int[] arr, int k) {
        Arrays.sort(arr);
    }

    static int[] randomArrayNoMoveMoreK(int maxValue, int maxSize, int K) {
        int[] arr = new int[(int) (Math.random() * maxSize)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1) - Math.random() * (maxValue + 1));
        }
        // 先排个序
        Arrays.sort(arr);
        // 然后开始随意交换，但是保证每个数距离不超过K
        // swap[i] == true, 表示i位置已经参与过交换
        // swap[i] == false, 表示i位置没有参与过交换
        boolean[] isSwap = new boolean[arr.length];
        for (int i = 0; i < isSwap.length; i++) {
            int j = Math.min(i + (int) (Math.random() * (K + 1)), arr.length - 1);//和后面的交换 也相当于和前面范围内的做交换
            if (!isSwap[i] && !isSwap[j]) {//默认值为false 两个均为false 进入 有一个为true跳过 如果当前i已经换过 再和后面交换可能会超过范围K
                isSwap[i] = true;
                isSwap[j] = true;
                int temp = arr[i];
                arr[j] = arr[i];
                arr[i] = temp;
            }
        }
        return arr;
    }


    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
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
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int k = (int) (Math.random() * maxSize);
            int[] arr1 = randomArrayNoMoveMoreK(maxValue, maxSize, k);
            int[] arr2 = copyArray(arr1);
            sortArrayDistanceLessK(arr1, k);
            comparator(arr2, k);
            if (!isEqual(arr1, arr2)) {
                System.out.println("错误");
                break;
            }
        }
        System.out.println("测试结束");
    }
}

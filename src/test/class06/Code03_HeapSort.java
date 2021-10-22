package test.class06;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Code03_HeapSort {
    static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        //先生成大根堆
        //两种方法
        //O(logN)从上往下一个一个往里添加
        /*for (int i = 0; i < arr.length; i++) {
            heapInsert(arr, i);
        }*/
        //O(N)从下往上  需要将数组先全部给好
        int heapSize = arr.length;
        for (int i = arr.length - 1; i >= 0; i--) {//>=
            heapify(arr, i, heapSize);
        }
        //循环将头节点最大数 位置移到末尾 最大数位置就应该在这 堆大小减一 arr.length-1
        swap(arr, 0, --heapSize);
        while (heapSize > 0) {//直到堆大小不大于0
            heapify(arr, 0, heapSize);
            swap(arr, 0, --heapSize);
        }
    }

    //看父节点是否比自己大 大的话一直交换 直到不大或者已经到头节点
    void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    //看当前节点是否有子节点比自己大 和最大的子节点交换 直到当前节点是最大或者，没有子节点（子节点越界）
    static void heapify(int[] arr, int index, int heapSize) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            if (arr[largest] < arr[index]) {
                break;
            }
            swap(arr, index, largest);
            index = largest;
            left = index * 2 + 1;
        }
    }

    static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * (maxSize + 1))];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1) - Math.random() * (maxValue + 1));
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
        if (arr1 == null && arr1 == null) {
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
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            heapSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("错误");
                break;
            }
        }
        System.out.println("测试结束");
    }

}

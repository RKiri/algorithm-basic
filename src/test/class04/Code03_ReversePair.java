package test.class04;

public class Code03_ReversePair {
    static int reverPairNumber(int[] arr) {
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

    //逆序对 后面的数比前面的小 因为有序
    //所以从后往前看 当这个数小于第一个数时 这组左边的数全都比第一个数小 都能组成逆序对
    //指针从后往前遍历 当找到小于他时的指针一直算到m+1的位置的个数 添加到总个数里
    static int merge(int[] arr, int l, int m, int r) {
        int p1 = m;
        int p2 = r;
        int index = 0;
        int res = 0;
        int[] help = new int[r - l + 1];
        while (p1 >= l && p2 >= m + 1) {
            res += arr[p1] > arr[p2] ? (p2 - (m + 1) + 1) : 0;
            help[index++] = arr[p1] > arr[p2] ? arr[p1--] : arr[p2--];
        }
        while (p1 >= l) {
            help[index++] = arr[p1--];
        }
        while (p2 >= m + 1) {
            help[index++] = arr[p2--];
        }
        for (int i = 0; i < help.length; i++) {
            arr[l + i] = help[help.length - 1 - i];
        }
        return res;
    }

    static int comparator(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int ans = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
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
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
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
            if (reverPairNumber(arr1) != comparator(arr2)) {
                System.out.println("错误");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}

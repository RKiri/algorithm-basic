package test.class04;

public class Code02_SmallSum {
    //求数组小和 一个数左边的比他小的所有数的总和 将所有小和累加到一起
    //可以理解成 一个数右侧有多少个比他大的 数*个数 累加起来
    //用归并排序 使数组有序来优化减少复杂度

    //smallSum方法 传一个数组 返回累加和
    static int smallSum(int[] arr) {
        //边界条件 为空 长度小于2
        if (arr == null || arr.length < 2) {
            return 0;
        }
        //左侧递归函数 加上右侧递归函数 加上合并时产生的累加和
        return process(arr, 0, arr.length - 1);
    }

    //递归函数 process 数组 开始 结束下标 返回累加和
    static int process(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }
        int mid = l + ((r - l) >> 1);
        return process(arr, l, mid) + process(arr, mid + 1, r) + merge(arr, l, mid, r);
    }

    //在归并排序过程中 将和求出
    //右组右边是没有数的 只看左组 右侧有几个数比他大 同组之间也不看 因为上一轮已经看过了
    //在归并排序中 从左组第一个数开始看 右组第一个数比较 是否比他大
    //如果比他大 则右组所有剩余数均比他大 将他*右组此时指针到末尾的个数 累加到和里
    //如果相同或小于 累加和不管
    //先排序 相同先将右组的数放到新数组中
    //然后指针移动 无论左组还是右组指针溢出 说明此时没有再需要累加的 此轮左右组merge完毕 返回res
    static int merge(int[] arr, int l, int m, int r) {
        int p1 = l;
        int p2 = m + 1;
        int index = 0;
        int[] help = new int[r - l + 1];
        int res = 0;
        while (p1 <= m && p2 <= r) {
            res += arr[p1] < arr[p2] ? arr[p1] * (r - p2 + 1) : 0;
            help[index++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= m) {
            help[index++] = arr[p1++];
        }
        while (p2 <= r) {
            help[index++] = arr[p2++];
        }
        for (int i = 0; i < help.length; i++) {//将帮助数组里的数返还arr
            arr[l + i] = help[i];
        }
        return res;
    }

    static int comparator(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int res = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                res += arr[i] < arr[j] ? arr[i] : 0;
            }
        }
        return res;
    }

    static int[] generateRandomArray(int maxValue, int maxSize) {
        int[] arr = new int[(int) (Math.random() * maxSize)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * maxValue);
        }
        return arr;
    }

    static int[] copyArray(int[] arr) {
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
        int maxSize = 100;
        int maxValue = 100;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = {4, 1, 2, 1};//generateRandomArray(maxValue, maxSize);
            int[] arr2 = copyArray(arr1);
            if (smallSum(arr1) != comparator(arr2)) {
                System.out.println("错误");
                printArray(arr1);
                printArray(arr2);
                System.out.println(smallSum(arr1) + " " + comparator(arr2));
                break;
            }
        }
        System.out.println("测试结束");
    }
}

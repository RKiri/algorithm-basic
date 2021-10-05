package test.class02;

import java.util.HashMap;
import java.util.HashSet;

public class Code03_KM {
    //test
    static int test(int[] arr, int k, int m) {
        //定义一个hashmap 用来存值和出现次数
        HashMap<Integer, Integer> map = new HashMap<>();
        //for循环数组 看map里是否有 没有的话存值和1
        for (int num : arr) {
            if (!map.containsKey(num)) {
                map.put(num, 1);
            } else {//有的话将出现次数查出并+1
                map.put(num, map.get(num) + 1);
            }
        }
        //for循环keySet 查询每一个值出现的次数
        for (int num : map.keySet()) {
            if (map.get(num) == k) {//如果次数等于k 返回值
                return num;
            }
        }
        //没有则返回-1
        return -1;
    }

    //onlyKTimes
    static int onlyKTimes(int[] arr, int k, int m) {
        //定义一个32位的数组
        int[] t = new int[32];
        //循环数组内的数 将每一个数从0到31依次右移和1& 添加到数组对应位置
        for (int num : arr) {
            for (int i = 0; i < 32; i++) {
                t[i] += (num >> i) & 1;
            }
        }
        //循环数组 判断每一位是否是m的倍数 如果是 继续 如果不是 则判断%m是否等于k
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            if (t[i] % m == 0) {
                continue;
            }
            //如果是 则根据位数将其添加到最终答案
            if (t[i] % m == k) {
                ans += (1 << i);
            } else {
                //如果不是则返回-1
                return -1;
            }
        }
        //但注意 如果这个数是0 将不会改变每一位数组的值 所以需要另加判断
        int count = 0;
        if (ans == 0) {
            //遍历整个数组 将0的次数记录 如果不为k 返回-1
            for (int num : arr) {
                if (num == 0) {
                    count++;
                }
            }
            if (count != k) {
                return -1;
            }
        }
        //此外正常返回
        return ans;
    }

    //randomArray 生成符合条件的随机数组 传入k m 几种 数的范围
    static int[] randomArray(int maxKinds, int range, int k, int m) {
        //生成k种的随机数
        int kTimeNum = random(range);
        //生成随机数 一半概率为k 另一半为小于m的随机数
        int kTime = Math.random() > 0.5 ? k : (int) (Math.random() * m);
        //定义种类 +2 至少两种
        int numKinds = (int) (Math.random() * maxKinds) + 2;
        //根据种类和k m生成对应长度数组
        int[] arr = new int[kTime + (numKinds - 1) * m];
        //循环将k种的数插入数组
        int index = 0;
        for (; index < kTime; index++) {
            arr[index] = kTimeNum;
        }
        //种类--
        numKinds--;
        //定义hashset 将k种的数添加进去
        HashSet<Integer> set = new HashSet<>();
        set.add(kTimeNum);
        //循环直到种类为0
        while (numKinds != 0) {
            //do 生成随机数 while 判断set里是否有 有的话继续生成
            int count = 0;
            do {
                count = random(range);
            } while (set.contains(count));
            //没有的话 将其添加到set里 并将种类-- 循环将这个数插入数组m次
            set.add(count);
            numKinds--;
            for (int i = 0; i < m; i++) {//
                arr[index++] = count;
            }
        }
        //循环数组 将第i位 与随机位做交换
        for (int i = 0; i < arr.length; i++) {
            int j = (int) (Math.random() * arr.length);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        //返回数组
        return arr;
    }


    //生成正负范围内随机数
    //random()
    static int random(int range) {
        return (int) ((Math.random() * (range + 1)) - Math.random() * (range + 1));
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
        int kinds = 5;
        int range = 30;
        int testTime = 100000;
        int max = 9;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int a = (int) (Math.random() * max) + 1;
            int b = (int) (Math.random() * max) + 1;
            int m = Math.max(a, b);
            int k = Math.min(a, b);
            if (k == m) {
                m++;
            }
            int[] arr = randomArray(kinds, range, k, m);
            int arr1 = test(arr, k, m);
            int arr2 = onlyKTimes(arr, k, m);
            if (arr1 != arr2) {
                System.out.println("出错了");
                printArray(arr);
                System.out.println("k:" + k);
                System.out.println("m:" + m);
                System.out.println("arr1:" + arr1);
                System.out.println("arr2:" + arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}

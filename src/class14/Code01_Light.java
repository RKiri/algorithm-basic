package class14;

import java.util.HashSet;

public class Code01_Light {

    public static int minLight1(String road) {
        if (road == null || road.length() == 0) {
            return 0;
        }
        return process(road.toCharArray(), 0, new HashSet<>());
    }

    // str[index....]位置，自由选择放灯还是不放灯
    // str[0..index-1]位置呢？已经做完决定了，那些放了灯的位置，存在lights里
    // 要求选出能照亮所有.的方案，并且在这些 有效 的方案中，返回最少需要几个灯
    public static int process(char[] str, int index, HashSet<Integer> light) {
        if (index == str.length) {
            // 结束的时候
            // 当前位置是点的话
            //包含自己前后三个都没有灯 无效
            for (int i = 0; i < str.length; i++) {
                if (str[i] == '.') {
                    if (!light.contains(i - 1) && !light.contains(i) && !light.contains(i + 1)) {//遍历一遍过后发现都有效 返回当前灯的数量
                        return Integer.MAX_VALUE;
                    }
                }
            }
            return light.size();
        } else {
            // str还没结束 每当走到一个位置 都包含是否有灯两种情况
            int no = process(str, index + 1, light);
            int yes = Integer.MAX_VALUE;
            // i X .
            //当前不点亮
            //点亮
            //如果是点 递归后续
            if (str[index] == '.') {
                light.add(index);
                yes = process(str, index + 1, light);
                light.remove(index);
            }
            //去除位之前递归添加的map
            return Math.min(no, yes);
        }
    }

    public static int minLight2(String road) {
        char[] str = road.toCharArray();
        int i = 0;
        int light = 0;
        while (i < str.length) {
            if (str[i] == 'X') {
                i++;
            } else {
                light++;//剩下的情况都需要点亮 所以直接light++
                if (i + 1 == str.length) {
                    break;
                } else { // 有i位置  i+ 1   X  .
                    if (str[i + 1] == 'X') {
                        i = i + 2;
                    } else {
                        i = i + 3;
                    }
                }
            }
        }
        return light;
    }

    // for test
    public static String randomString(int len) {
        char[] res = new char[(int) (Math.random() * len) + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = Math.random() < 0.5 ? 'X' : '.';
        }
        return String.valueOf(res);
    }

    public static void main(String[] args) {
        int len = 20;
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            String test = randomString(len);
            int ans1 = minLight1(test);
            int ans2 = minLight2(test);
            if (ans1 != ans2) {
                System.out.println("oops!");
                break;
            }
        }
        System.out.println("finish!");
    }
}

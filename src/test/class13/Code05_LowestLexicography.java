package test.class13;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public class Code05_LowestLexicography {
    //拿第一个最小的 或者没有的话直接拿空
    static String lowestString1(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        TreeSet<String> ans = process(strs);//过程返回TreeSet
        return ans.size() == 0 ? "" : ans.first();
    }

    // strs中所有字符串全排列，返回所有可能的结果

    static TreeSet<String> process(String[] strs) {
        TreeSet<String> ans = new TreeSet<>();//用treeset直接排好序
        if (strs.length == 0) {//因为TreeSet需要排序而HashSet不需要,空的无法排序
            ans.add("");//如果不给"" 会认为是空的 下方无法拼接
            return ans;
        }
        for (int i = 0; i < strs.length; i++) {
            String first = strs[i];
            String[] next = removeIndexString(strs, i);
            TreeSet<String> nexts = process(next);
            for (String str : nexts) {
                ans.add(first + str);//将当前节点和剩余节点可能性加到TreeSet里
            }
        }
        return ans;
    }

    //拿出第一个元素后
    //更新下字符串数组
    //递归获取剩余元素的所有可能的结果
    //每个都和第一个拼接到一起
    //然后循环第二个元素、第三个元素等等

    // {"abc", "cks", "bct"}
    // 0 1 2
    // removeIndexString(arr , 1) -> {"abc", "bct"}
    static String[] removeIndexString(String[] strs, int index) {
        int length = strs.length;
        String[] ans = new String[length - 1];
        int ansIndex = 0;
        for (int i = 0; i < length; i++) {
            if (i != index) {
                ans[ansIndex++] = strs[i];
            }
        }
        return ans;
    }

    static class MyComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {//按字典顺序比较两个字符串。
            return (o1 + o2).compareTo(o2 + o1);//用前面的减去后面的
        }
    }

    static String lowestString2(String[] strs) {
        if (strs == null || strs.length == 0) {//
            return "";
        }
        Arrays.sort(strs, new MyComparator());
        String ans = "";//null会被直接拼接进去
        for (int i = 0; i < strs.length; i++) {
            ans += strs[i];
        }
        return ans;
    }

    // for test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];//大小1到strLen 字符串长度
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 5);
            ans[i] = (Math.random() <= 0.5) ? (char) (65 + value) : (char) (97 + value);//大小写abcde
        }
        return String.valueOf(ans);
    }

    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];//数组长度1到arrLen
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    // for test
    public static String[] copyStringArray(String[] arr) {
        String[] ans = new String[arr.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = String.valueOf(arr[i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int arrLen = 6;
        int strLen = 5;
        int testTimes = 10000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String[] arr1 = generateRandomStringArray(arrLen, strLen);
            String[] arr2 = copyStringArray(arr1);
            if (!lowestString1(arr1).equals(lowestString2(arr2))) {
                for (String str : arr1) {
                    System.out.print(str + ",");
                }
                System.out.println();
                for (String str : arr2) {
                    System.out.print(str + ",");
                }
                System.out.println();
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("finish!");
    }
}

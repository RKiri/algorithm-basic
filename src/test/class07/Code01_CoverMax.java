package test.class07;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Code01_CoverMax {
    //暴力解法
    //传入所有的线段
    static int maxCover1(int[][] lines) {
        //循环遍历所有线段 取最小、最大位置 重合一定在这两个位置之间
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < lines.length; i++) {
            min = Math.min(min, lines[i][0]);
            max = Math.max(max, lines[i][1]);
        }
        int ans = 0;
        //当一个线段重合时 一定覆盖整数中间的值 将所有中间值进行遍历 查看覆盖次数最多的
        for (double p = min + 0.5; p < max; p++) {
            int cover = 0;
            for (int i = 0; i < lines.length; i++) {
                if (p > lines[i][0] && p < lines[i][1]) {
                    cover++;
                }
            }
            ans = Math.max(ans, cover);
        }
        return ans;
    }

    static int maxCover2(int[][] m) {
        //将线段以开始位置从小到大排序（需要将所有线段转换成线段数组 进行排序（定义一个Line类））
        //定义一个Line的数组 长度为线段数
        Line[] lines = new Line[m.length];
        //将线段转换添加到Line里
        for (int i = 0; i < lines.length; i++) {
            lines[i] = new Line(m[i][0], m[i][1]);
        }
        //比较器进行排序 不用自己写排序
        Arrays.sort(lines, new startCom());
        //小根堆，每一条线段的结尾数值，使用默认的
        //因为要循环将小于当前起始位置的元素弹出 所以用默认的小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        //遍历所有线段 将小根堆里小于等于当前线段开始位置的值全部弹出
        //（小于等于说明这个线段没有覆盖过此时开头的节点）
        // lines[i] -> cur  在黑盒中，把<=cur.start 东西都弹出
        int ans = 0;
        for (int i = 0; i < lines.length; i++) {
            while (!heap.isEmpty() && heap.peek() <= lines[i].start) {
                heap.poll();
            }
            //将当前线段结束位置添加进去 统计个数 就是当前起始位置被覆盖的数量
            heap.add(lines[i].end);
            ans = Math.max(ans, heap.size());
        }
        //遍历完所有线段 返回最大的个数
        return ans;
    }


    //定义一个Line类 包含开始 结束位置
    static class Line {
        int start;
        int end;

        public Line(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    static class startCom implements Comparator<Line> {

        @Override
        public int compare(Line o1, Line o2) {
            return o1.start - o2.start;
        }
    }

    // for test
    public static int[][] generateLines(int N, int L, int R) {//生成随机线段数 给定size 起始和结束位置
        int size = (int) (Math.random() * N) + 1;
        int[][] arr = new int[size][2];
        for (int i = 0; i < arr.length; i++) {
            int a = L + (int) (Math.random() * (R - L + 1));
            int b = L + (int) (Math.random() * (R - L - 1));
            if (a == b) {
                b = a + 1;
            }
            arr[i][0] = Math.min(a, b);
            arr[i][1] = Math.max(a, b);
        }
        return arr;
    }

    public static void main(String[] args) {
        int N = 100;
        int L = 0;
        int R = 100;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[][] arr = generateLines(N, L, R);
            int ans1 = maxCover1(arr);
            int ans2 = maxCover2(arr);
            if (ans1 != ans2) {
                System.out.println("错误");
                break;
            }
        }
        System.out.println("测试结束");
    }

}

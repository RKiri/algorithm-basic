package test.class14;

import java.util.Arrays;
import java.util.Comparator;

public class Code03_BestArrange {
    static class Program {
        int start;
        int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    // 暴力！所有情况都尝试！
    public static int bestArrange1(Program[] programs) {
        if (programs == null || programs.length == 0) {
            return 0;
        }
        return process(programs, 0, 0);
    }

    // 还剩下的会议都放在programs里
    // done之前已经安排了多少会议的数量
    // timeLine目前来到的时间点是什么

    // 目前来到timeLine的时间点，已经安排了done多的会议，剩下的会议programs可以自由安排
    // 返回能安排的最多会议数量
    public static int process(Program[] programs, int done, int timeLine) {
        if (programs == null || programs.length == 0) {
            return done;
        }
        // 还剩下会议
        int max = done;
        // 当前安排的会议是什么会，每一个都枚举
        for (int i = 0; i < programs.length; i++) {
            if (timeLine <= programs[i].start) {
                Program[] nexts = copyButExcept(programs, i);
                max = Math.max(max, process(nexts, done + 1, programs[i].end));
            }
        }
        //当前时间小于当前会议开始时间 可以安排
        //剩余会议数组
        //递归调用 来到当前会议结束时间点 已经安排的会议数+1 剩余会议数组
        //返回最多会议数量 和之前的已经安排的会议数量取最大值
        return max;
    }

    public static Program[] copyButExcept(Program[] programs, int index) {
        int length = programs.length - 1;
        Program[] ans = new Program[length];
        int k = 0;
        for (int i = 0; i < programs.length; i++) {
            if (i != index) {
                ans[k++] = programs[i];
            }
        }
        return ans;
    }

    static class ProgramComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o1.end - o2.end;
        }
    }

    static int bestArrange2(Program[] programs) {
        Arrays.sort(programs, new ProgramComparator());
        int timeLime = 0;//时间没有负的 从0开始
        int result = 0;
        for (int i = 0; i < programs.length; i++) {
            if (timeLime <= programs[i].start) {//记得有=
                result++;
                timeLime = programs[i].end;
            }
        }
        return result;
    }

    // for test
    public static Program[] generatePrograms(int programSize, int timeMax) {
        Program[] programs = new Program[(int) (Math.random() * (programSize + 1))];
        for (int i = 0; i < programs.length; i++) {
            int a = (int) (Math.random() * timeMax);
            int b = (int) (Math.random() * timeMax);
            if (a == b) {
                programs[i] = new Program(a, a + 1);
            } else {
                programs[i] = new Program(Math.min(a, b), Math.max(a, b));
            }
        }
        return programs;
    }

    public static void main(String[] args) {
        int programSize = 12;
        int timeMax = 20;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            Program[] programs = generatePrograms(programSize, timeMax);
            if (bestArrange1(programs) != bestArrange2(programs)) {
                System.out.println("错误");
                break;
            }
        }
        System.out.println("测试结束");
    }
}

package test.class13;

import java.util.ArrayList;
import java.util.List;

public class Code04_MaxHappy {
    static class Employee {
        public int happy; // 这名员工可以带来的快乐值
        List<Employee> subordinates; // 这名员工有哪些直接下级

        public Employee(int happy) {
            this.happy = happy;
            this.subordinates = new ArrayList<>();//需要先建一个空数组 105行
        }
    }

    public static int maxHappy1(Employee boss) {
        if (boss == null) {
            return 0;
        }
        return process1(boss, false);
    }

    // 当前来到的节点叫cur，
    // up表示cur的上级是否来，
    // 该函数含义：
    // 如果up为true，表示在cur上级已经确定来，的情况下，cur整棵树能够提供最大的快乐值是多少？
    // 如果up为false，表示在cur上级已经确定不来，的情况下，cur整棵树能够提供最大的快乐值是多少？
    public static int process1(Employee cur, boolean up) {
        if (up) { // 如果cur的上级来的话，cur没得选，只能不来
            int ans = 0;
            for (Employee next : cur.subordinates) {
                ans += process1(next, false);
            }
            return ans;
        } else { // 如果cur的上级不来的话，cur可以选，可以来也可以不来
            int p1 = cur.happy;
            int p2 = 0;
            for (Employee next : cur.subordinates) {
                p1 += process1(next, true);
                p2 += process1(next, false);
            }
            return Math.max(p1, p2);
        }
    }
    //*假设一X位头节点 假设可以向X左树和X右树要任何信息
    //*在这个假设下 讨论一X为头结点的树 （考虑其中是否有和X相不相关）得到答案的可能性
    //分两种情况 头节点是否来
    //如果头节点来 他的孩子节点都不能来 加上他孩子节点不能来的情况下的最大值
    //如果头节点不来 他的孩子节点可以是来的情况下的最大值 和不来的情况下的最大值
    //*列出所有可能性后，确定需要想左树和右树要什么样的信息Info
    //*写递归函数 返回Info
    //是否来 最大值 //来的情况下的最大值 不来的情况下的最大值
    //*考虑当前节点为空的情况 base case 是否能直接写出 不能的话返回null 下面做对null的判断
    //info(0,0)
    //*写代码 考虑将左树右树信息整合出整棵树的信息 然后返回
    static int maxHappy2(Employee head) {
        return Math.max(process(head).yes, process(head).no);
    }

    static class Info {
        int yes;
        int no;

        public Info(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }

    static Info process(Employee head) {
        if (head == null) {
            return new Info(0, 0);
        }
        int yes = head.happy;//来的话一定包含自己
        int no = 0;
        //跟之前不同 不是只选左右节点之一 而可以将一层全部拿到
        for (Employee next : head.subordinates) {//循环遍历孩子节点 将不同情况每个节点的最大值添加到一起
            Info nextInfo = process(next);
            yes += nextInfo.no;//当前来的话 拿孩子节点不来的值
            no += Math.max(nextInfo.yes, nextInfo.no);//不来的话 拿最大的
        }
        return new Info(yes, no);
    }

    // for test
    public static Employee genarateBoss(int maxLevel, int maxNexts, int maxHappy) {
        if (Math.random() < 0.02) {
            return null;
        }
        Employee boss = new Employee((int) (Math.random() * (maxHappy + 1)));
        genarateNexts(boss, 1, maxLevel, maxNexts, maxHappy);
        return boss;
    }

    // for test
    public static void genarateNexts(Employee e, int level, int maxLevel, int maxNexts, int maxHappy) {
        if (level > maxLevel) {
            return;
        }
        int nextsSize = (int) (Math.random() * (maxNexts + 1));
        for (int i = 0; i < nextsSize; i++) {
            Employee next = new Employee((int) (Math.random() * (maxHappy + 1)));
            e.subordinates.add(next);//防止往里添加时 报空指针异常
            genarateNexts(next, level + 1, maxLevel, maxNexts, maxHappy);
        }
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxNexts = 7;
        int maxHappy = 100;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            Employee boss = genarateBoss(maxLevel, maxNexts, maxHappy);
            if (maxHappy1(boss) != maxHappy2(boss)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}

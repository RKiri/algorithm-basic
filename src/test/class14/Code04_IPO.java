package test.class14;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Code04_IPO {
    class Program {
        int c;
        int p;

        public Program(int c, int p) {
            this.c = c;
            this.p = p;
        }
    }

    class MinCostsComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o1.c - o2.c;
        }
    }

    class MaxProfitComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o2.p - o1.p;
        }
    }

    int findMaximizedCapital(int[] costs, int[] profits, int k, int m) {
        PriorityQueue<Program> minCostQ = new PriorityQueue<>(new MinCostsComparator());
        PriorityQueue<Program> maxProfitQ = new PriorityQueue<>(new MaxProfitComparator());
        for (int i = 0; i < costs.length; i++) {
            minCostQ.add(new Program(costs[i], profits[i]));
        }
        for (int i = 0; i < k; i++) {
            if (!minCostQ.isEmpty() && minCostQ.peek().c < m) {
                maxProfitQ.add(minCostQ.poll());
            }
            if (maxProfitQ.isEmpty()) {
                return m;
            }
            m += maxProfitQ.poll().p;
        }
        return m;
    }

}

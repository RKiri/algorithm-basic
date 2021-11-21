package test.class15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Code03_NumberOfIslandsⅡ {
    List<Integer> numIslandsⅡ1(int m, int n, int[][] positions) {
        UnionFind1 uf = new UnionFind1(m, n);
        List<Integer> ans = new ArrayList<>();
        for (int[] cur : positions) {//每一个都是一个int[]
            ans.add(uf.connect(cur[0], cur[1]));
        }
        return ans;
    }

    static class UnionFind1 {
        int row;
        int col;
        int[] parents;
        int[] size;
        int[] help;
        int sets;

        public UnionFind1(int m, int n) {
            this.row = m;
            this.col = n;
            int len = m * n;
            this.parents = new int[len];
            this.size = new int[len];
            help = new int[len];//
            this.sets = 0;
        }

        int findFather(int i) {
            int hi = 0;
            while (i != parents[i]) {
                help[hi++] = i;
                i = parents[i];
            }
            for (hi--; hi >= 0; hi--) {
                parents[help[hi]] = i;
            }
            return i;
        }

        void union(int r1, int c1, int r2, int c2) {
            if (r1 < 0 || r1 >= row || c1 < 0 || c1 >= col || r2 < 0 || r2 >= row || c2 < 0 || c2 >= col) {
                return;
            }
            int index1 = index(r1, c1);
            int index2 = index(r2, c2);
            if (size[index1] == 0 || size[index2] == 0) {
                return;
            }
            int f1 = findFather(index1);
            int f2 = findFather(index2);
            if (f1 != f2) {
                if (size[f1] > size[f2]) {
                    parents[f2] = f1;
                    size[f1] += size[f2];
                } else {
                    parents[f1] = f2;
                    size[f2] += size[f1];
                }
                sets--;
            }
        }

        int index(int r, int c) {
            return r * col + c;
        }

        int connect(int r, int c) {
            int index = index(r, c);
            if (size[index] == 0) {
                parents[index] = index;
                size[index] = 1;
                sets++;
                union(r, c, r - 1, c);
                union(r, c, r + 1, c);
                union(r, c, r, c - 1);
                union(r, c, r, c + 1);
            }
            return sets;
        }
    }

    List<Integer> numIslandsⅡ2(int m, int n, int[][] positions) {
        UnionFind2 uf = new UnionFind2();
        List<Integer> ans = new ArrayList<>();
        for (int[] cur : positions) {
            ans.add(uf.connect(cur[0], cur[1]));
        }
        return ans;
    }

    class UnionFind2 {
        HashMap<String, String> parent;
        HashMap<String, Integer> size;
        List<String> help;
        int sets;

        public UnionFind2() {
            this.parent = new HashMap<>();
            this.size = new HashMap<>();
            this.help = new ArrayList<>();
            this.sets = 0;
        }

        String findFather(String str) {
            while (!str.equals(parent.get(str))) {
                help.add(str);
                str = parent.get(str);
            }
            for (String cur : help) {
                parent.put(cur, str);
            }
            help.clear();
            return str;
        }

        void union(String str1, String str2) {
            if (parent.containsKey(str1) && parent.containsKey(str2)) {
                String head1 = findFather(str1);
                String head2 = findFather(str2);
                if (!head1.equals(head2)) {
                    int size1 = size.get(head1);
                    int size2 = size.get(head2);
                    String big = size1 > size2 ? head1 : head2;
                    String small = big == head1 ? head2 : head1;
                    parent.put(small, big);
                    size.put(big, size1 + size2);
                    sets--;
                }
            }
        }

        int connect(int r, int c) {
            String key = String.valueOf(r) + "_" + String.valueOf(c);
            if (!parent.containsKey(key)) {
                parent.put(key, key);
                size.put(key, 1);
                sets++;
                String up = String.valueOf(r - 1) + "_" + String.valueOf(c);
                String down = String.valueOf(r + 1) + "_" + String.valueOf(c);
                String left = String.valueOf(r) + "_" + String.valueOf(c - 1);
                String right = String.valueOf(r) + "_" + String.valueOf(c + 1);
                union(key, up);
                union(key, down);
                union(key, left);
                union(key, right);
            }
            return sets;
        }
    }
}

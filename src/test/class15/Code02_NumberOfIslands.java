package test.class15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Code02_NumberOfIslands {

    public static int numIslands1(char[][] grid) {
        int islands = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    islands++;
                    infect(grid, i, j);
                }
            }
        }
        return islands;
    }

    static void infect(char[][] grid, int i, int j) {
        if (i < 0 || i == grid.length || j < 0 || j == grid[0].length || grid[i][j] != '1') {
            return;
        }
        grid[i][j] = '0';
        infect(grid, i - 1, j);
        infect(grid, i + 1, j);
        infect(grid, i, j - 1);
        infect(grid, i, j + 1);
    }

    public static int numIslands2(char[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        Dot[][] dots = new Dot[row][col];
        List<Dot> list = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == '1') {
                    dots[i][j] = new Dot();
                    list.add(dots[i][j]);
                }
            }
        }
        UnionFind<Dot> unionFind = new UnionFind<>(list);
        for (int i = 1; i < col; i++) {
            if (grid[0][i - 1] == '1' && grid[0][i] == '1') {
                unionFind.union(dots[0][i - 1], dots[0][i]);
            }
        }
        for (int i = 1; i < row; i++) {
            if (grid[i - 1][0] == '1' && grid[i][0] == '1') {
                unionFind.union(dots[i - 1][0], dots[i][0]);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (grid[i][j] == '1') {
                    if (grid[i - 1][j] == '1') {
                        unionFind.union(dots[i - 1][j], dots[i][j]);
                    }
                    if (grid[i][j - 1] == '1') {
                        unionFind.union(dots[i][j - 1], dots[i][j]);
                    }
                }
            }
        }
        return unionFind.sets();
    }

    static class Dot {

    }

    static class Node<V> {
        V value;

        public Node(V value) {
            this.value = value;
        }
    }

    static class UnionFind<V> {
        HashMap<V, Node<V>> nodes;
        HashMap<Node<V>, Node<V>> parents;
        HashMap<Node<V>, Integer> sizeMap;

        //不需要set 直接根据sizeMap大小可判断
        public UnionFind(List<V> list) {
            this.nodes = new HashMap<>();
            this.parents = new HashMap<>();
            this.sizeMap = new HashMap<>();
            for (V cur : list) {
                Node<V> node = new Node<>(cur);
                nodes.put(cur, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        Node<V> findFather(Node<V> cur) {
            Stack<Node<V>> stack = new Stack<>();
            while (cur != parents.get(cur)) {
                stack.push(cur);
                cur = parents.get(cur);
            }
            while (!stack.isEmpty()) {
                parents.put(stack.pop(), cur);
            }
            return cur;
        }

        void union(V a, V b) {//传的是值
            Node<V> aHead = findFather(nodes.get(a));//先转成Node
            Node<V> bHead = findFather(nodes.get(b));
            if (aHead != bHead) {
                int aSetMap = sizeMap.get(aHead);
                int bSetMap = sizeMap.get(bHead);
                Node<V> big = aSetMap >= bSetMap ? aHead : bHead;
                Node<V> small = big == aHead ? bHead : aHead;
                parents.put(small, big);
                sizeMap.put(big, aSetMap + bSetMap);
                sizeMap.remove(small);
            }
        }

        int sets() {
            return sizeMap.size();
        }
    }

    public static int numIslands3(char[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        UnionFind2 uF = new UnionFind2(grid);//直接写 没有泛型
        for (int j = 1; j < col; j++) {
            if (grid[0][j] == '1' && grid[0][j - 1] == '1') {
                uF.union(0, j, 0, j - 1);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (grid[i][j] == '1') {
                    if (grid[i - 1][j] == '1') {
                        uF.union(i, j, i - 1, j);
                    }
                    if (grid[i][j - 1] == '1') {
                        uF.union(i, j, i, j - 1);
                    }
                }
            }
        }
        for (int i = 1; i < row; i++) {
            if (grid[i][0] == '1' && grid[i - 1][0] == '1') {
                uF.union(i, 0, i - 1, 0);
            }
        }
        return uF.sets;
    }

    static class UnionFind2 {
        int[] parent;
        int[] size;
        int[] help;
        int col;
        int sets;

        public UnionFind2(char[][] grid) {
            int row = grid.length;
            this.col = grid[0].length;
            int len = row * col;
            this.parent = new int[len];
            this.size = new int[len];
            this.help = new int[len];
            this.sets = 0;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (grid[i][j] == '1') {
                        int index = index(i, j);
                        parent[index] = index;
                        size[index] = 1;
                        sets++;
                    }
                }
            }
        }

        int index(int r, int c) {
            return r * col + c;
        }

        int findFather(int index) {//可直接传下标
            int hi = 0;
            while (index != parent[index]) {
                help[hi++] = index;
                index = parent[index];
            }
            hi--;
            while (hi >= 0) {
                parent[help[hi--]] = index;
            }
            return index;
        }

        void union(int r1, int c1, int r2, int c2) {
            int index1 = index(r1, c1);//只需在合并出进行下标转换
            int index2 = index(r2, c2);
            int f1 = findFather(index1);
            int f2 = findFather(index2);
            if (f1 != f2) {
                if (size[f1] > size[f2]) {
                    parent[f2] = f1;
                    size[f1] += size[f2];
                    sets--;
                } else {
                    parent[f1] = f2;
                    size[f2] += size[f1];
                    sets--;
                }
            }
        }
    }


    static char[][] generateRandomMatrix(int row, int col) {
        char[][] board = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = Math.random() < 0.5 ? '1' : '0';
            }
        }
        return board;
    }

    static char[][] copy(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        char[][] ans = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                ans[i][j] = board[i][j];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int row = 1000;
        int col = 1000;
        char[][] board1 = generateRandomMatrix(row, col);
        char[][] board2 = copy(board1);
        char[][] board3 = copy(board1);
        long start;//时间用long
        long end;
        System.out.println("感染方法，并查集（map实现），并查集（数组实现）的运行结果和运行时间");
        System.out.println("随机生成的二位矩阵规模" + row + "*" + col);
        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果" + numIslands1(board1));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间" + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println("并查集（map实现）的运行结果" + numIslands2(board2));
        end = System.currentTimeMillis();
        System.out.println("并查集（map实现）的运行时间" + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println("并查集（数组实现）的运行结果" + numIslands3(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集（数组实现）的运行时间" + (end - start) + "ms");
        System.out.println("***************************************************");
        row = 10000;
        col = 10000;
        board1 = generateRandomMatrix(row, col);
        board3 = copy(board1);
        System.out.println("感染方法，并查集（数组实现）的运行结果和运行时间");
        System.out.println("随机生成的二位矩阵规模" + row + "*" + col);
        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果" + numIslands1(board1));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间" + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println("并查集（数组实现）的运行结果" + numIslands3(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集（数组实现）的运行时间" + (end - start) + "ms");
    }
}

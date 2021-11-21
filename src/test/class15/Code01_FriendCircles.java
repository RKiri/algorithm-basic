package test.class15;

public class Code01_FriendCircles {
    //因为是N*N的正方形矩阵 集合大小就是N
    //建立矩阵总大小的并查集
    //循环遍历矩阵内所有元素 如果等于一 说明是相连通的 将其合并到一起（只需对斜对称线上方进行判断即可 因为连通是相互的）
    //返回集合数量
    //建立并查集 成员有数组构成 常数项比map好 map大概是数组的1.7到2倍
    //需要一个辅助数组 在查询祖先节点的时候用来替代栈
    //初始化 new对象 赋值 循环赋值
    //查询祖先节点方法 压缩路径 先记录到辅助数组 然后循环将其的父指向代表节点
    //合并方法 查询祖先节点 判断是否一样 不一样判断大小 合并
    //返回集合数量方法
    class Solution {
        public int findCircleNum(int[][] isConnected) {
            int N = isConnected.length;
            UnionFind unionFind = new UnionFind(N);
            for (int i = 0; i < N; i++) {
                for (int j = i + 1; j < N; j++) {
                    if (isConnected[i][j] == 1) {
                        unionFind.union(i, j);
                    }
                }
            }
            return unionFind.sets();
        }

        class UnionFind {
            int[] parent;
            int[] size;
            int[] help;
            int set;

            public UnionFind(int n) {
                this.parent = new int[n];
                this.size = new int[n];
                this.help = new int[n];
                this.set = n;
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                    size[i] = 1;
                }
            }

            int find(int i) {
                int hi = 0;
                while (i != parent[i]) {
                    help[hi++] = parent[i];
                    i = parent[i];
                }
                for (hi--; hi >= 0; hi--) {
                    parent[help[hi]] = i;
                }
                return i;
            }

            void union(int i, int j) {
                int ihead = find(i);
                int jhead = find(j);
                if (ihead != jhead) {
                    if (size[ihead] > size[jhead]) {
                        size[ihead] += size[jhead];
                        parent[jhead] = ihead;
                    } else {
                        size[jhead] += size[ihead];
                        parent[ihead] = jhead;
                    }
                    set--;
                }
            }

            int sets() {
                return set;
            }
        }
    }
}

package test.class14;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Code05_UnionFind {
    //仅仅包一层 对外使用没有影响
    class Node<V> {
        V value;

        public Node(V value) {
            this.value = value;
        }
    }

    //并查集
    class UnionFind<V> {
        //传入值 对应的节点
        //节点对应的父节点
        //节点对应的集合大小
        HashMap<V, Node<V>> nodes;
        HashMap<Node<V>, Node<V>> parents;
        HashMap<Node<V>, Integer> sizeMap;

        //构造函数 传一个List集合
        //建立map
        //循环将每个值
        //建立节点
        //添加到对应节点表
        //一开始节点都指向自己
        //大小都为1
        public UnionFind(List<V> list) {//<V>
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V cur : list) {
                Node<V> node = new Node<>(cur);
                nodes.put(cur, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        // 给你一个节点，请你往上到不能再往上，把代表返回
        //建立栈
        //如果自己的父亲不是自己 说明还没到最上的节点
        //循环将沿途路径添加到栈里
        //继续向上指
        //将路径全部弹出
        //全部指向顶端祖先节点 扁平化
        //然后再返回
        Node<V> findFather(Node<V> cur) {//两个方法的复杂度都在这个方法上
            Stack<Node<V>> path = new Stack<>();
            //让向上找的路径变短 减少复杂度
            //所以每次查询完一个祖先节点 将整条链上所有节点都直接指向该祖先节点
            //这样下次再找链中节点 只需向上插叙一步
            if (cur != parents.get(cur)) {
                path.push(cur);
                cur = parents.get(cur);
            }
            while (!path.isEmpty()) {
                parents.put(path.pop(), cur);
            }
            return cur;
        }

        boolean isSameSet(V a, V b) {
            return findFather(nodes.get(a)) == findFather(nodes.get(b));
        }

        //先获取两个节点的祖先节点
        //查看是否是同一个 如果是直接结束
        //然后获取大小
        //比较大小重定向
        //将小的指向大的
        //大小改变
        void union(V a, V b) {
            Node<V> aHead = findFather(nodes.get(a));//除此之外时间复杂的都是O(1)的
            Node<V> bHead = findFather(nodes.get(b));
            if (aHead != bHead) {
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
                Node<V> small = big == aHead ? bHead : aHead;
                parents.put(small, big);
                sizeMap.put(big, aSetSize + bSetSize);
                sizeMap.remove(small);
            }
        }

        int sets() {
            return sizeMap.size();
        }
    }

}

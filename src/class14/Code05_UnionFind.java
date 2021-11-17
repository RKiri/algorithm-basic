package class14;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Code05_UnionFind {

    public static class Node<V> {//仅仅包一层 对外使用没有影响
        V value;

        public Node(V v) {
            value = v;
        }
    }

    public static class UnionFind<V> {//并查集
        public HashMap<V, Node<V>> nodes;//传入值 对应的节点
        public HashMap<Node<V>, Node<V>> parents;//节点对应的父节点
        public HashMap<Node<V>, Integer> sizeMap;//节点对应的集合大小

        public UnionFind(List<V> values) {//构造函数 传一个List集合
            nodes = new HashMap<>();//建立map
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V cur : values) {//循环将每个值
                Node<V> node = new Node<>(cur);//建立节点
                nodes.put(cur, node);//添加到对应节点表
                parents.put(node, node);//一开始节点都指向自己
                sizeMap.put(node, 1);//大小都为1
            }
        }

        // 给你一个节点，请你往上到不能再往上，把代表返回
        public Node<V> findFather(Node<V> cur) {
            Stack<Node<V>> path = new Stack<>();//建立栈
            while (cur != parents.get(cur)) {//如果自己的父亲不是自己 说明还没到最上的节点
                path.push(cur);//循环将沿途路径添加到栈里
                cur = parents.get(cur);//继续向上指
            }
            while (!path.isEmpty()) {//将路径全部弹出
                parents.put(path.pop(), cur);//全部指向顶端祖先节点 扁平化
            }
            return cur;//然后再返回
        }

        public boolean isSameSet(V a, V b) {
            return findFather(nodes.get(a)) == findFather(nodes.get(b));
        }

        public void union(V a, V b) {
            Node<V> aHead = findFather(nodes.get(a));//先获取两个节点的祖先节点
            Node<V> bHead = findFather(nodes.get(b));
            if (aHead != bHead) {//查看是否是同一个 如果是直接结束
                int aSetSize = sizeMap.get(aHead);//然后获取大小
                int bSetSize = sizeMap.get(bHead);
                Node<V> big = aSetSize >= bSetSize ? aHead : bHead;//比较大小重定向
                Node<V> small = big == aHead ? bHead : aHead;
                parents.put(small, big);//将小的指向大的
                sizeMap.put(big, aSetSize + bSetSize);//大小改变
                sizeMap.remove(small);
            }
        }

        public int sets() {
            return sizeMap.size();
        }

    }
}

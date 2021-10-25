package test.class07;

import java.util.*;

public class HeapGreater<T> {
    //反向索引表
    ArrayList<T> heap;
    HashMap<T, Integer> indexMap;
    int heapSize;
    Comparator<? super T> comp;

    public HeapGreater(Comparator<T> c) {
        this.heap = new ArrayList<>();
        this.indexMap = new HashMap<>();
        this.heapSize = 0;
        this.comp = c;
    }

    boolean isEmpty() {
        return heapSize == 0;
    }

    int size() {
        return heapSize;
    }

    boolean contains(T obj) {
        return indexMap.containsKey(obj);
    }

    T peek() {
        return heap.get(0);
    }

    void push(T obj) {
        //基础类型 值传递 会被覆盖掉 包一层 引用传递 内存地址不同
        //从末尾处调整 size+1
        //先添加到末尾 记录位置 进行调整 调整交换改变位置记录
        heap.add(obj);
        indexMap.put(obj, heapSize);
        heapInsert(heapSize++);
    }

    T pop() {
        //根节点弹出
        T ans = heap.get(0);
        //与末尾交换
        swap(0, --heapSize);
        //反向索引表里移除掉这个元素
        indexMap.remove(ans);
        //移除末尾元素
        heap.remove(heapSize);
        //从0开始调整
        heapify(0);
        return ans;
    }

    void remove(T obj) {//移除掉某个元素
        //记录最后一个元素的值
        T replace = heap.get(heapSize - 1);
        //从反向索引表获取要移除的元素的位置
        int index = indexMap.get(obj);
        heap.remove(--heapSize);
        indexMap.remove(obj);
        //为什么要替换 因为堆逻辑概念上要求数组0到某一个值连续
        if (replace != obj) {
            heap.set(index, replace);
            indexMap.put(replace, index);
            resign(replace);
        }
    }

    void resign(T obj) {//调整顺序 传元素 因为外面调方法的时候只知道元素并不知道位置
        heapInsert(indexMap.get(obj));
        heapify(indexMap.get(obj));
    }

    // 请返回堆上的所有元素
    List<T> getAllElements() {
        List<T> ans = new LinkedList<>();
        for (T c : heap) {
            ans.add(c);
        }
        return ans;
    }

    void heapInsert(int index) {//index
        //因为负数将第一个参数放前面 所以第一个参数为index 当与父节点交换时 进入下面代码
        //或者comp.compare(heap.get((index - 1) / 2), heap.get(index)) > 0 正数 第二个参数放前面
        //但具体判断规则交由实际具体条件实现
        while (comp.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    void heapify(int index) {
        //不管规则 只看返回哪个 负数返回前面的 left+1放前面
        int left = index * 2 + 1;
        while (left < heapSize) {
            int best = left + 1 < heapSize && comp.compare(heap.get(left), heap.get(left + 1)) > 0 ? left + 1 : left;
            best = comp.compare(heap.get(best), heap.get(index)) > 0 ? index : best;
            if (best == index) {
                break;
            }
            swap(best, index);
            index = best;
            left = index * 2 + 1;
        }
    }

    void swap(int i, int j) {
        //记录位置的值 替换原来位置的值 更新反向索引表
        T o1 = heap.get(i);
        T o2 = heap.get(j);
        indexMap.put(o1, j);
        indexMap.put(o2, i);
        heap.set(i, o2);
        heap.set(j, o1);
        //add将指定的元素插入到指定的位置
        //*列表。将元素当前移动到该位置(如果有的话)并
        //*任何右边的后续元素(在它们的下标上加1)。
        //set将列表中指定位置的元素替换为
        //*指定的元素。
    }
}

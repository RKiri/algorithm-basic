package class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/*
 * T一定要是非基础类型，有基础类型需求包一层
 */
public class HeapGreater<T> {

    private ArrayList<T> heap;
    private HashMap<T, Integer> indexMap;//反向索引表
    private int heapSize;
    private Comparator<? super T> comp;

    public HeapGreater(Comparator<T> c) {
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
        heapSize = 0;
        comp = c;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public int size() {
        return heapSize;
    }

    public boolean contains(T obj) {
        return indexMap.containsKey(obj);
    }

    public T peek() {
        return heap.get(0);
    }

    public void push(T obj) {
        heap.add(obj);
        //基础类型 值传递 会被覆盖掉 包一层 引用传递 内存地址不同
        indexMap.put(obj, heapSize);
        heapInsert(heapSize++);//从末尾处调整 size+1
    }

    public T pop() {
        T ans = heap.get(0);//根节点弹出
        swap(0, heapSize - 1);//与末尾交换
        indexMap.remove(ans);//反向索引表里移除掉这个元素
        heap.remove(--heapSize);//移出末尾元素
        heapify(0);//从0开始调整
        return ans;
    }

    public void remove(T obj) {//移除掉某个元素
        T replace = heap.get(heapSize - 1);//记录最后一个元素的值
        int index = indexMap.get(obj);//从反向索引表获取要移除的元素的位置
        indexMap.remove(obj);
        heap.remove(--heapSize);
        if (obj != replace) {//为什么要替换 因为堆逻辑概念上要求数组0到某一个值连续
            heap.set(index, replace);
            indexMap.put(replace, index);
            resign(replace);//调整顺序
        }
    }

    public void resign(T obj) {//
        heapInsert(indexMap.get(obj));
        heapify(indexMap.get(obj));
    }

    // 请返回堆上的所有元素
    public List<T> getAllElements() {
        List<T> ans = new ArrayList<>();
        for (T c : heap) {
            ans.add(c);
        }
        return ans;
    }

    private void heapInsert(int index) {
        while (comp.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {//因为负数将第一个参数放前面 所以第一个参数为index 当与父节点交换时 进入下面代码
            //或者comp.compare(heap.get((index - 1) / 2), heap.get(index)) > 0 正数 第二个参数放前面
            //但具体判断规则交由实际具体条件实现
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    private void heapify(int index) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            //不管规则 只看返回哪个 负数返回前面的 left+1放前面
            int best = left + 1 < heapSize && comp.compare(heap.get(left + 1), heap.get(left)) < 0 ? (left + 1) : left;
            best = comp.compare(heap.get(best), heap.get(index)) < 0 ? best : index;
            if (best == index) {
                break;
            }
            swap(best, index);
            index = best;
            left = index * 2 + 1;
        }
    }

    private void swap(int i, int j) {
        T o1 = heap.get(i);//记录位置的值
        T o2 = heap.get(j);
        heap.set(i, o2);//替换原来位置的值
        heap.set(j, o1);
        indexMap.put(o2, i);//更新反向索引表
        indexMap.put(o1, j);
    }

}

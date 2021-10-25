package class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Code02_EveryStepShowBoss {

    public static class Customer {//定义顾客类 包含id 购买数量 加入某个区域时的时间
        public int id;
        public int buy;
        public int enterTime;

        public Customer(int v, int b, int o) {
            id = v;
            buy = b;
            enterTime = 0;
        }
    }

    public static class CandidateComparator implements Comparator<Customer> {
        //候选区比较规则 将购买数量多的放前面 当购买数量一样时 先加入的放前面

        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy != o2.buy ? (o2.buy - o1.buy) : (o1.enterTime - o2.enterTime);
        }

    }

    public static class DaddyComparator implements Comparator<Customer> {
        //得奖区比较规则 购买数量少的放前面 一致时 先进入的放前面

        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy != o2.buy ? (o1.buy - o2.buy) : (o1.enterTime - o2.enterTime);
        }

    }

    public static class WhosYourDaddy {
        private HashMap<Integer, Customer> customers;
        private HeapGreater<Customer> candHeap;//用加强堆
        private HeapGreater<Customer> daddyHeap;
        private final int daddyLimit;//K

        public WhosYourDaddy(int limit) {
            customers = new HashMap<Integer, Customer>();
            candHeap = new HeapGreater<>(new CandidateComparator());//比较器排序
            daddyHeap = new HeapGreater<>(new DaddyComparator());
            daddyLimit = limit;
        }

        // 当前处理i号事件，arr[i] -> id,  buyOrRefund
        public void operate(int time, int id, boolean buyOrRefund) {
            if (!buyOrRefund && !customers.containsKey(id)) {
                return;
            }
            if (!customers.containsKey(id)) {
                customers.put(id, new Customer(id, 0, 0));
            }
            Customer c = customers.get(id);
            if (buyOrRefund) {
                c.buy++;
            } else {
                c.buy--;
            }
            if (c.buy == 0) {
                customers.remove(id);
            }
            if (!candHeap.contains(c) && !daddyHeap.contains(c)) {
                if (daddyHeap.size() < daddyLimit) {
                    c.enterTime = time;
                    daddyHeap.push(c);//添加并且有序
                } else {
                    c.enterTime = time;
                    candHeap.push(c);
                }
            } else if (candHeap.contains(c)) {
                if (c.buy == 0) {
                    candHeap.remove(c);//删除并且有序
                } else {
                    candHeap.resign(c);//
                }
            } else {
                if (c.buy == 0) {
                    daddyHeap.remove(c);
                } else {
                    daddyHeap.resign(c);
                }
            }
            daddyMove(time);//处理完后 调整
        }

        public List<Integer> getDaddies() {//返回当前daddy
            List<Customer> customers = daddyHeap.getAllElements();
            List<Integer> ans = new ArrayList<>();
            for (Customer c : customers) {
                ans.add(c.id);
            }
            return ans;
        }

        private void daddyMove(int time) {
            if (candHeap.isEmpty()) {
                return;
            } else {
                if (daddyHeap.size() < daddyLimit) {
                    Customer p = candHeap.pop();
                    p.enterTime = time;
                    daddyHeap.push(p);
                } else {
                    if (candHeap.peek().buy > daddyHeap.peek().buy) {
                        Customer oldDaddy = daddyHeap.pop();
                        Customer newDaddy = candHeap.pop();
                        oldDaddy.enterTime = time;
                        newDaddy.enterTime = time;
                        daddyHeap.push(newDaddy);
                        candHeap.push(oldDaddy);
                        //添加 弹出后有序 logN
                    }
                }
            }
        }
    }

    public static List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        WhosYourDaddy whoDaddies = new WhosYourDaddy(k);//定义一个结构 传入K
        for (int i = 0; i < arr.length; i++) {
            whoDaddies.operate(i, arr[i], op[i]);//循环遍历 每次进入结构调整
            ans.add(whoDaddies.getDaddies());//返回当前daddy
        }
        return ans;
    }

    // 干完所有的事，模拟，不优化
    public static List<List<Integer>> compare(int[] arr, boolean[] op, int k) {//传入两个数组 和得奖区大小
        HashMap<Integer, Customer> map = new HashMap<>();//记录id和对应实例
        List<Customer> cands = new ArrayList<>();
        List<Customer> daddy = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int id = arr[i];
            boolean buyOrRefund = op[i];
            // 没有发生：用户购买数为0并且又退货了
            if (!buyOrRefund && !map.containsKey(id)) {//如果之前没有购买 还有退货 说明是无效的 直接返回当前的得奖区
                ans.add(getCurAns(daddy));
                continue;
            }

            if (!map.containsKey(id)) {//用户之前购买数是0，此时买货事件 进这个一定会进下方买事件
                map.put(id, new Customer(id, 0, 0));//先将id和实例初始化添加进表；此时表中已经有记录
            }
            // 用户之前购买数>0， 此时买货
            // 用户之前购买数>0, 此时退货
            Customer c = map.get(id);//获取实例
            if (buyOrRefund) {//根据买、卖进行调整
                c.buy++;
            } else {
                c.buy--;
            }
            if (c.buy == 0) {//当为0时 表中删除记录
                map.remove(id);
            }
            cands = cleanZeroBuy(cands);//如果之前有记录 退货后数量变为0 将无效数据去除
            daddy = cleanZeroBuy(daddy);
            // c
            // 下面做 对两个区域进行更新 存疑：进入这个没有必要进下方的clean
            if (!cands.contains(c) && !daddy.contains(c)) {//如果两个区域都没有
                if (daddy.size() < k) {//先看得奖区满没满 没满的话直接加入
                    c.enterTime = i;//时间改为当前时间
                    daddy.add(c);
                } else {//满了添加进候选区
                    c.enterTime = i;
                    cands.add(c);
                }
            }
            cands = cleanZeroBuy(cands);//如果之前有记录 退货后数量变为0 将无效数据去除
            daddy = cleanZeroBuy(daddy);
            cands.sort(new CandidateComparator());//重新排序
            daddy.sort(new DaddyComparator());
            move(cands, daddy, k, i);
            ans.add(getCurAns(daddy));
        }
        return ans;
    }

    public static void move(List<Customer> cands, List<Customer> daddy, int k, int time) {
        if (cands.isEmpty()) {//候选区为空 不会有变动 直接结束
            return;
        }
        // 候选区不为空
        if (daddy.size() < k) {
            Customer c = cands.get(0);
            c.enterTime = time;
            daddy.add(c);
            cands.remove(0);
        } else { // 等奖区满了，候选区有东西
            if (cands.get(0).buy > daddy.get(0).buy) {
                Customer oldDaddy = daddy.get(0);
                daddy.remove(0);
                Customer newDaddy = cands.get(0);
                cands.remove(0);
                newDaddy.enterTime = time;
                oldDaddy.enterTime = time;
                daddy.add(newDaddy);
                cands.add(oldDaddy);
            }
        }
        //结束前没有排序 因为排序只是为了判断如何移动 只需在移动前
    }

    public static List<Customer> cleanZeroBuy(List<Customer> arr) {
        List<Customer> noZero = new ArrayList<Customer>();//建立临时数组
        for (Customer c : arr) {
            if (c.buy != 0) {
                noZero.add(c);
            }
        }
        return noZero;
    }

    public static List<Integer> getCurAns(List<Customer> daddy) {
        List<Integer> ans = new ArrayList<>();
        for (Customer c : daddy) {
            ans.add(c.id);
        }
        return ans;
    }

    // 为了测试
    public static class Data {//包含两个数组信息
        public int[] arr;
        public boolean[] op;

        public Data(int[] a, boolean[] o) {
            arr = a;
            op = o;
        }
    }

    // 为了测试
    public static Data randomData(int maxValue, int maxLen) {//生成随机Data
        int len = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[len];
        boolean[] op = new boolean[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
            op[i] = Math.random() < 0.5 ? true : false;
        }
        return new Data(arr, op);
    }

    // 为了测试
    public static boolean sameAnswer(List<List<Integer>> ans1, List<List<Integer>> ans2) {
        if (ans1.size() != ans2.size()) {
            return false;
        }
        for (int i = 0; i < ans1.size(); i++) {//依次比较大List中每一个小List内容
            List<Integer> cur1 = ans1.get(i);
            List<Integer> cur2 = ans2.get(i);
            if (cur1.size() != cur2.size()) {
                return false;
            }
            cur1.sort((a, b) -> (a - b));//定义比较规则
            cur2.sort((a, b) -> (a - b));
            for (int j = 0; j < cur1.size(); j++) {
                if (!cur1.get(j).equals(cur2.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int maxValue = 10;
        int maxLen = 100;
        int maxK = 6;
        int testTimes = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            Data testData = randomData(maxValue, maxLen);
            int k = (int) (Math.random() * maxK) + 1;
            int[] arr = testData.arr;
            boolean[] op = testData.op;
            List<List<Integer>> ans1 = topK(arr, op, k);
            List<List<Integer>> ans2 = compare(arr, op, k);
            if (!sameAnswer(ans1, ans2)) {
                for (int j = 0; j < arr.length; j++) {
                    System.out.println(arr[j] + " , " + op[j]);
                }
                System.out.println(k);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束");
    }

}

package test.class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Code02_EveryStepShowBoss {
    //定义顾客类 包含id 购买数量 加入某个区域时的时间
    static class Customer {
        int id;
        int buy;
        int enterTime;

        public Customer(int id, int buy, int enterTime) {
            this.id = id;
            this.buy = buy;
            this.enterTime = enterTime;
        }
    }

    //候选区比较规则 将购买数量多的放前面 当购买数量一样时 先加入的放前面
    static class CandidateComparator implements Comparator<Customer> {

        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy == o2.buy ? (o1.enterTime - o2.enterTime) : (o2.buy - o1.buy);
        }
    }

    //得奖区比较规则 购买数量少的放前面 一致时 先进入的放前面
    static class DaddyComparator implements Comparator<Customer> {

        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy == o2.buy ? (o1.enterTime - o2.enterTime) : (o1.buy - o2.buy);
        }
    }

    //定义一个结构 传入K
    //循环遍历 每次进入结构调整
    //返回当前daddy
    static List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        WhoDaddies whoDaddies = new WhoDaddies(k);
        for (int i = 0; i < arr.length; i++) {
            whoDaddies.operate(arr[i], op[i], i);
            ans.add(whoDaddies.getDaddies());
        }
        return ans;
    }

    static class WhoDaddies {
        HashMap<Integer, Customer> map;
        HeapGreater<Customer> cands;//用加强堆
        HeapGreater<Customer> daddy;
        int daddyLimit;

        public WhoDaddies(int daddyLimit) {
            this.map = new HashMap<>();
            this.cands = new HeapGreater<>(new CandidateComparator());//比较器排序
            this.daddy = new HeapGreater<>(new DaddyComparator());
            this.daddyLimit = daddyLimit;
        }

        // 当前处理i号事件，arr[i] -> id,  buyOrRefund
        void operate(int id, boolean buyOrRefund, int time) {
            if (!map.containsKey(id) && !buyOrRefund) {
                return;
            }
            if (!map.containsKey(id)) {
                map.put(id, new Customer(id, 0, 0));
            }
            Customer c = map.get(id);
            if (buyOrRefund) {
                c.buy++;
            } else {
                c.buy--;
            }
            if (c.buy == 0) {
                map.remove(id);
            }
            if (!cands.contains(c) && !daddy.contains(c)) {
                if (daddy.size() < daddyLimit) {
                    c.enterTime = time;//
                    daddy.push(c);//添加并且有序
                } else {
                    c.enterTime = time;
                    cands.push(c);
                }
            } else if (cands.contains(c)) {
                if (c.buy == 0) {
                    cands.remove(c);//删除并且有序
                } else {
                    cands.resign(c);
                }
            } else if (daddy.contains(c)) {
                if (c.buy == 0) {
                    daddy.remove(c);
                } else {
                    daddy.resign(c);
                }
            }
            //处理完后 调整
            daddyMove(time);
        }

        //返回当前daddy
        List<Integer> getDaddies() {
            List<Customer> customers = daddy.getAllElements();
            List<Integer> ans = new ArrayList<>();
            for (Customer c : customers) {
                ans.add(c.id);
            }
            return ans;
        }

        void daddyMove(int time) {
            if (cands.isEmpty()) {
                return;
            } else {
                if (daddy.size() < daddyLimit) {
                    Customer c = cands.pop();
                    c.enterTime = time;
                    daddy.push(c);
                } else {
                    if (cands.peek().buy > daddy.peek().buy) {
                        Customer newDaddy = cands.pop();
                        Customer oldDaddy = daddy.pop();
                        newDaddy.enterTime = time;
                        oldDaddy.enterTime = time;
                        daddy.push(newDaddy);
                        cands.push(oldDaddy);//弹出后有序 logN
                    }
                }
            }
        }
    }


    // 干完所有的事，模拟，不优化
    static List<List<Integer>> compare(int[] arr, boolean[] op, int k) {//传入两个数组 和得奖区大小
        //记录id和对应实例
        HashMap<Integer, Customer> map = new HashMap<>();
        List<Customer> cands = new ArrayList<>();
        List<Customer> daddy = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            // 没有发生：用户购买数为0并且又退货了 不需要往表中添加
            int id = arr[i];
            boolean buyOrRefund = op[i];
            if (!buyOrRefund && !map.containsKey(id)) {//如果之前没有购买 还有退货 说明是无效的 直接返回当前的得奖区
                ans.add(getCurAns(daddy));
                continue;
            }
            if (!map.containsKey(id)) {
                //用户之前购买数是0，此时买货事件 进这个一定会进下方买事件
                //先将id和实例初始化添加进表；此时表中已经有记录
                map.put(id, new Customer(id, 0, 0));
            }
            // 用户之前购买数>0， 此时买货
            // 用户之前购买数>0, 此时退货
            //获取实例
            Customer c = map.get(id);
            //根据买、卖进行调整
            if (buyOrRefund) {
                c.buy++;
            } else {
                c.buy--;
            }
            if (c.buy == 0) {//当为0时 表中删除记录
                map.remove(id);
            }
            //下面做 对两个区域进行更新
            //如果两个区域都没有
            if (!cands.contains(c) && !daddy.contains(c)) {
                //先看得奖区满没满 没满的话直接加入
                if (daddy.size() < k) {
                    c.enterTime = i;//时间改为当前时间
                    daddy.add(c);
                } else {//满了添加进候选区
                    c.enterTime = i;
                    cands.add(c);
                }
            }
            //如果之前有记录 退货后数量变为0 将无效数据去除
            cands = cleanZeroBuy(cands);//O(N)
            daddy = cleanZeroBuy(daddy);
            //重新排序
            cands.sort(new CandidateComparator());//O(N)
            daddy.sort(new DaddyComparator());
            move(cands, daddy, k, i);
            ans.add(getCurAns(daddy));//将当前的将添加进
        }
        return ans;
    }

    static void move(List<Customer> cands, List<Customer> daddy, int k, int i) {
        //候选区为空 不会有变动 直接结束
        if (cands.isEmpty()) {
            return;
        } else {
            //候选区不为空
            if (daddy.size() < k) {
                Customer c = cands.get(0);
                c.enterTime = i;
                cands.remove(0);
                daddy.add(c);
            } else {//得奖区满了，候选区有东西
                if (cands.get(0).buy > daddy.get(0).buy) {
                    Customer newDaddy = cands.get(0);
                    Customer oldDaddy = daddy.get(0);
                    newDaddy.enterTime = i;
                    oldDaddy.enterTime = i;
                    cands.remove(0);
                    daddy.remove(0);
                    daddy.add(newDaddy);
                    cands.add(oldDaddy);//记得将得奖区弹出的放到候选区
                }
            }
            //结束前没有排序 因为排序只是为了判断如何移动 只需在移动前
        }
    }


    static List<Customer> cleanZeroBuy(List<Customer> list) {
        //建立临时数组
        List<Customer> ans = new ArrayList<>();
        for (Customer c : list) {
            if (c.buy != 0) {
                ans.add(c);
            }
        }
        return ans;
    }


    static List<Integer> getCurAns(List<Customer> daddy) {
        List<Integer> list = new ArrayList<>();
        for (Customer c : daddy) {
            list.add(c.id);
        }
        return list;
    }

    // 为了测试
    public static class Data {
        //包含两个数组信息
        int[] arr;
        boolean[] op;

        public Data(int[] arr, boolean[] op) {
            this.arr = arr;
            this.op = op;
        }
    }

    // 为了测试
    public static Data randomData(int maxValue, int maxLen) {//生成随机Data
        int size = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[size];
        boolean[] op = new boolean[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
            op[i] = (Math.random() > 0.5) ? true : false;
        }
        return new Data(arr, op);
    }

    // 为了测试
    public static boolean sameAnswer(List<List<Integer>> ans1, List<List<Integer>> ans2) {
        //依次比较大List中每一个小List内容
        if (ans1.size() != ans2.size()) {
            return false;
        }
        for (int i = 0; i < ans1.size(); i++) {
            List<Integer> list1 = ans1.get(i);
            List<Integer> list2 = ans2.get(i);
            if (list1.size() != list2.size()) {
                return false;
            } else {
                //定义相同比较规则 才可以将两个list每一位对上
                list1.sort((a, b) -> (a - b));//
                list2.sort((a, b) -> (a - b));
                for (int j = 0; j < list1.size(); j++) {
                    if (!list1.get(j).equals(list2.get(j))) {//equals
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int maxValue = 100;
        int maxLen = 100;
        int maxK = 6;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int k = (int) (Math.random() * maxK) + 1;
            Data data = randomData(maxValue, maxLen);
            int[] arr = data.arr;
            boolean[] op = data.op;
            List<List<Integer>> ans1 = topK(arr, op, k);
            List<List<Integer>> ans2 = compare(arr, op, k);
            if (!sameAnswer(ans1, ans2)) {
                for (int j = 0; j < arr.length; j++) {
                    System.out.println(arr[j] + " " + op[j]);
                }
                System.out.println(k);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("错误");
                break;
            }
        }
        System.out.println("测试结束");
    }
}

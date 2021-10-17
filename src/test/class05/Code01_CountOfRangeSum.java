package test.class05;

public class Code01_CountOfRangeSum {
    //3、因为有序会让其减少判断 先计算右组和左组之间存在的个数 然后排序
    class Solution {
        public int countRangeSum(int[] nums, int lower, int upper) {
            if (nums == null || nums.length == 0) {
                return 0;
            }
            //1、将原数组转换为前缀和数组 后续全依据前缀和数组进行处理
            long[] sum = new long[nums.length];//可能会越界 需要用long
            sum[0] = nums[0];
            for (int i = 1; i < nums.length; i++) {
                sum[i] = sum[i - 1] + nums[i];
            }
            return process(sum, 0, sum.length - 1, lower, upper);
        }

        int process(long[] sum, int L, int R, int lower, int upper) {
            //2、想求在[lower,upper]之间
            //①前缀和数组直接符合
            if (L == R) {
                return sum[L] <= upper && sum[L] >= lower ? 1 : 0;//没有的话返回0
            }
            int mid = L + ((R - L) >> 1);
            return process(sum, L, mid, lower, upper) + process(sum, mid + 1, R, lower, upper) + merge(sum, L, mid, R, lower, upper);
        }

        int merge(long[] sum, int L, int M, int R, int lower, int upper) {
            //4、merge过程中 先定义两个滑动窗口坐标 循环右组的数
            //起始位置均在L L比X-upper小就向右一直滑动 直到比他大； R如果比X-lower小就向右滑 直到比他大 [L,R)
            int windowL = L;
            int windowR = L;//
            int ans = 0;
            //②只需减去前面某个前缀和后符合即可 看前面的前缀和是否有在（当前第x位前缀和数组和为X）[X-upper,X-lower]
            for (int i = M + 1; i <= R; i++) {
                long min = sum[i] - upper;
                long max = sum[i] - lower;
                //要找到第一个在范围内的 小于不在范围内 需要加 左闭；需要看到M 仍小于min 不在范围内 继续往后移 移出一个；
                //符合既停 接下来只需看windowR是否符合
                while (sum[windowL] < min && windowL <= M) {
                    windowL++;
                }
                //要找到第一个不在范围内的 等于在范围内 继续加 右开；需要看到M 仍小于max 继续往后移 移出一个 不符合；
                //如果不符合既停 同一个位置为0 如果符合就向下移 然后计算数量
                while (sum[windowR] <= max && windowR <= M) {
                    windowR++;
                }
                //总个数累加上R-L
                ans += windowR - windowL;
            }
            int p1 = L;
            int p2 = M + 1;
            long[] help = new long[R - L + 1];//
            int index = 0;
            while (p1 <= M && p2 <= R) {
                help[index++] = sum[p1] < sum[p2] ? sum[p1++] : sum[p2++];
            }
            while (p1 <= M) {
                help[index++] = sum[p1++];
            }
            while (p2 <= R) {
                help[index++] = sum[p2++];
            }
            for (int i = 0; i < help.length; i++) {
                sum[L + i] = help[i];
            }
            return ans;
        }
    }
}

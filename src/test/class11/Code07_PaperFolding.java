package test.class11;

public class Code07_PaperFolding {
    static void printAllFolds(int N) {
        process(1, N, true);
    }

    // 当前你来了一个节点，脑海中想象的！
    // 这个节点在第i层，一共有N层，N固定不变的
    // 这个节点如果是凹的话，down = T
    // 这个节点如果是凸的话，down = F
    // 函数的功能：中序打印以你想象的节点为头的整棵树！ 左边凹 右边凸
    static void process(int i, int N, boolean down) {
        if (i > N) {
            return;
        }
        process(i + 1, N, true);
        System.out.print(down ? "凹 " : "凸 ");
        process(i + 1, N, false);
    }

    //额外空间复杂度N 并没有这个树 当打印完就已经释放了
    //将当前i层打印好返回
    //共N层
    public static void main(String[] args) {
        printAllFolds(4);
    }
}

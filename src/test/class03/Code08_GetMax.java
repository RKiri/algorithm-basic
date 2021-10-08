package test.class03;

public class Code08_GetMax {
    int getMax(int[] arr) {
        return process(arr, 0, arr.length - 1);
    }

    private int process(int[] arr, int L, int R) {
        if (L == R) {
            return L;
        }
        int mid = L + ((R - L) >> 1);
        int leftMax = process(arr, L, mid);
        int rightMax = process(arr, mid + 1, R);
        return Math.max(leftMax, rightMax);
    }
}

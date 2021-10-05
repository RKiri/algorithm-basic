package test.class02;

public class Code02_EvenTimesOddTimes {
    static void printOddTimesNum(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            ans ^= arr[i];
        }
        System.out.println(ans);
    }

    static int rightBit1(int N) {
        int ans = N & (~N + 1);
        System.out.println(ans);
        return ans;
    }

    static void printOddTimesNum2(int[] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        int eor1 = rightBit1(eor);
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] & eor1) != 0) {
                ans ^= arr[i];
            }
        }
        //eor^ans = a^b^a = b
        System.out.println(ans + " " + (eor ^ ans));

    }

    public static void main(String[] args) {
        int[] arr = {1, 1, 2, 2, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5};
        printOddTimesNum(arr);
        System.out.println("********************");
        int a = 20;
        rightBit1(a);
        System.out.println("********************");
        int[] arr2 = {1, 1, 1, 2, 2, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5};
        printOddTimesNum2(arr2);
    }
}

package test.class02;

public class Code01_Swap {
    public static void main(String[] args) {
        int a = 1;
        int b = 2;

        System.out.println(a);
        System.out.println(b);

        a = a ^ b;
        b = a ^ b;
        a = a ^ b;

        System.out.println(a);
        System.out.println(b);
        System.out.println("****************");

        int[] arr = {1, 2, 3};
        int i = 0;
        int j = 0;
        arr[i] = arr[i] ^ arr[j];
        System.out.println(arr[i] + " " + arr[j]);
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
        System.out.println(arr[i] + " " + arr[j]);
        System.out.println("****************");

        System.out.println(arr[1]);
        System.out.println(arr[2]);
        swap(arr, 1, 2);
        System.out.println(arr[1]);
        System.out.println(arr[2]);


    }

    static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }
}

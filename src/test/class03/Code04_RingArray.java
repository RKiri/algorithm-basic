package test.class03;

public class Code04_RingArray {
    //定义队列类
    class myQueue {
        //包含成员变量 数组 添加位置 弹出位置 大小 长度
        int[] arr;
        int pushi;
        int polli;
        int size;
        int limit;

        //构造函数根据长度生成数组 位置 大小均为0
        public myQueue(int limit) {
            this.arr = new int[limit];
            this.pushi = 0;
            this.polli = 0;
            this.size = 0;
            this.limit = limit;
        }

        //添加方法
        void push(int value) {
            if (size == limit) {
                throw new RuntimeException("满了");
            }
            size++;
            arr[pushi] = value;
            pushi = nextIndex(pushi);
        }
        //判断数组是否已满 满了抛异常信息
        //否则 大小加一 将数添加到添加位置
        //添加位置移动

        //弹出方法
        int poll() {
            if (size == 0) {
                throw new RuntimeException("空的");
            }
            size--;
            int ans = arr[polli];
            polli = nextIndex(polli);
            return ans;
        }
        //判断数组是否为空 空抛信息
        //大小减一 将弹出位置得数弹出 位置移动

        //是否为空方法
        boolean isEmpty() {
            return size == 0;
        }

        //移动方法
        int nextIndex(int index) {
            return index < arr.length - 1 ? index + 1 : 0;
        }
    }


}

package test.class08;

import java.util.HashMap;

public class Code02_TrieTree {
    static class Node1 {
        int pass;//节点常见的数据项
        int end;
        Node1[] next;//固定数组

        // char tmp = 'b'  (tmp - 'a')
        public Node1() {
            this.pass = 0;
            this.end = 0;
            // 0    a
            // 1    b
            // 2    c
            // ..   ..
            // 25   z
            // nexts[i] == null   i方向的路不存在
            // nexts[i] != null   i方向的路存在
            this.next = new Node1[26];
        }
    }

    static class Trie1 {
        //包含头节点 初始化
        Node1 root;

        public Trie1() {
            this.root = new Node1();
        }

        void insert(String word) {
            if (word == null) {//判断字符串是否为空
                return;
            }
            //转换成字符数组
            char[] str = word.toCharArray();
            // 从左往右遍历字符
            //从头节点触发 准备变量来到头节点 开始移动
            Node1 node = root;
            node.pass++;
            //定义一个路径
            int path = 0;
            for (int i = 0; i < str.length; i++) {
                path = str[i] - 'a';// 由字符，对应成走向哪条路
                if (node.next[path] == null) {//指向为空
                    node.next[path] = new Node1();//指向新的
                }
                node = node.next[path];//下移
                node.pass++;//值++
            }
            node.end++;//最后的值++
        }

        int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] str = word.toCharArray();
            int index = 0;
            Node1 node = root;
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                if (node.next[index] == null) {
                    return 0;
                }
                node = node.next[index];
            }
            return node.end;
        }

        void delete(String word) {
            if (search(word) != 0) {//先看是否有
                char[] str = word.toCharArray();
                int index = 0;
                Node1 node = root;
                node.pass--;
                for (int i = 0; i < str.length; i++) {
                    index = str[i] - 'a';
                    //将下一个节点的pass--看是否为零
                    //为零的话 说明下面都是这个字符串 可直接将链断开
                    //如果不是 只--
                    if (--node.next[index].pass == 0) {
                        node.next[index] = null;
                        return;
                    }
                    node = node.next[index];
                }
                node.end--;
            }
        }

        int prefixNumber(String word) {
            if (word == null) {
                return 0;
            }
            char[] str = word.toCharArray();
            Node1 node = root;
            int index = 0;
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                if (node.next[index] == null) {
                    return 0;
                }
                node = node.next[index];
            }
            return node.pass;
        }
    }

    static class Node2 {
        int pass;
        int end;
        HashMap<Integer, Node2> next;

        public Node2() {
            this.pass = 0;
            this.end = 0;
            this.next = new HashMap<>();
        }
    }

    static class Trie2 {
        Node2 root;

        public Trie2() {
            this.root = new Node2();
        }

        void insert(String word) {
            if (word == null) {
                return;
            }
            char[] str = word.toCharArray();
            Node2 node = root;
            node.pass++;
            int index = 0;
            for (int i = 0; i < str.length; i++) {
                index = str[i];//字符转int
                if (!node.next.containsKey(index)) {//如果不包含 建一个
                    node.next.put(index, new Node2());//将其添加进map
                }
                node = node.next.get(index);//node指向新的节点
                node.pass++;
            }
            node.end++;
        }

        int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] str = word.toCharArray();
            int index = 0;
            Node2 node = root;
            for (int i = 0; i < str.length; i++) {
                index = str[i];
                if (!node.next.containsKey(index)) {
                    return 0;
                }
                node = node.next.get(index);
            }
            return node.end;
        }

        int prefixNumber(String word) {
            if (word == null) {
                return 0;
            }
            char[] str = word.toCharArray();
            int index = 0;
            Node2 node = root;
            for (int i = 0; i < str.length; i++) {
                index = str[i];
                if (!node.next.containsKey(index)) {
                    return 0;
                }
                node = node.next.get(index);
            }
            return node.pass;
        }

        void delete(String word) {
            if (search(word) != 0) {
                char[] str = word.toCharArray();
                int index = 0;
                Node2 node = root;
                node.pass--;
                for (int i = 0; i < str.length; i++) {
                    index = str[i];
                    if (--node.next.get(index).pass == 0) {
                        node.next.remove(index);
                        return;
                    }
                    node = node.next.get(index);
                }
                node.end--;
            }
        }
    }

    public static class Right {

        HashMap<String, Integer> map;

        public Right() {
            this.map = new HashMap<>();
        }

        public void insert(String word) {
            if (map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, 1);
            }
        }

        public void delete(String word) {
            if (map.containsKey(word)) {
                if (map.get(word) == 1) {
                    map.remove(word);
                } else {
                    map.put(word, map.get(word) - 1);
                }
            }
        }

        public int search(String word) {
            if (map.containsKey(word)) {
                return map.get(word);
            } else {
                return 0;
            }
        }

        public int prefixNumber(String pre) {
            //遍历所有的key 查看是否有包含此前缀的 有的话将其value加上
            int count = 0;
            for (String str : map.keySet()) {
                if (str.startsWith(pre)) {
                    count += map.get(str);
                }
            }
            return count;
        }
    }

    // for test
    public static String generateRandomString(int strLen, int strValue) {
        //生成一个长度内随机字符串
        //定义一个char数组
        char[] arr = new char[(int) (Math.random() * strLen) + 1];
        //将数组在某个范围值内填满
        for (int i = 0; i < arr.length; i++) {
            int value = (int) (Math.random() * strValue);
            arr[i] = (char) (97 + value);
        }
        //返回char数组的字符串表示形式
        return String.valueOf(arr);
    }

    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen, int strValue) {
        //生成随机字符串数组
        String[] arr = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = generateRandomString(strLen, strValue);
        }
        return arr;
    }

    public static void main(String[] args) {
        int arrLen = 100;
        int strLen = 20;
        int strValue = 6;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen, strValue);
            Trie1 trie1 = new Trie1();
            Trie2 trie2 = new Trie2();
            Right right = new Right();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    trie1.insert(arr[j]);//j
                    trie2.insert(arr[j]);
                    right.insert(arr[j]);
                } else if (decide < 0.5) {
                    trie1.delete(arr[j]);
                    trie2.delete(arr[j]);
                    right.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans1 = trie1.search(arr[j]);
                    int ans2 = trie2.search(arr[j]);
                    int ans3 = right.search(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("search");
                        break;
                    }
                } else {
                    int ans1 = trie1.prefixNumber(arr[j]);
                    int ans2 = trie2.prefixNumber(arr[j]);
                    int ans3 = right.prefixNumber(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("prefixNum");
                        break;
                    }
                }
            }
        }
        System.out.println("测试结束");

    }
}

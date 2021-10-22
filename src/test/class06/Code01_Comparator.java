package test.class06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

public class Code01_Comparator {
    static class student {
        int id;
        int age;
        String name;

        public student(int id, int age, String name) {
            this.id = id;
            this.age = age;
            this.name = name;
        }

        @Override
        public String toString() {
            return "student{" +
                    "id=" + id +
                    ", age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    static void printArray(Integer[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    static class dCom implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }

    static class idUpAgeDown implements Comparator<student> {

        @Override
        public int compare(student o1, student o2) {
            return o1.id != o2.id ? o1.id - o2.id : o2.age - o1.age;//o1.id != o2.id
        }
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[]{5, 2, 3, 7, 1, 6, 4, 9, 8};//Integer
        Arrays.sort(arr, new dCom());
        printArray(arr);
        System.out.println("************************************");
        student student1 = new student(1, 11, "A");
        student student2 = new student(1, 22, "B");
        student student3 = new student(1, 33, "C");
        student student4 = new student(2, 44, "D");
        student student5 = new student(2, 55, "E");
        student[] students = new student[]{student1, student2, student3, student4, student5};
        Arrays.sort(students, new idUpAgeDown());
        for (int i = 0; i < students.length; i++) {
            student s = students[i];
            System.out.println(s.toString());
        }
        System.out.println("**************************");
        ArrayList<student> arrayList = new ArrayList<>();
        arrayList.add(student1);
        arrayList.add(student2);
        arrayList.add(student3);
        arrayList.add(student4);
        arrayList.add(student5);
        arrayList.sort(new idUpAgeDown());
        for (int i = 0; i < arrayList.size(); i++) {
            student s = arrayList.get(i);
            System.out.println(s.toString());
        }
        System.out.println("*********************************************");
        TreeMap<student, String> treeMap = new TreeMap<>((a, b) -> (a.id - b.id));//(a.id - b.id)
        treeMap.put(student1, "我是学生1");
        treeMap.put(student2, "我是学生2");
        treeMap.put(student3, "我是学生3");
        treeMap.put(student4, "我是学生4");
        treeMap.put(student5, "我是学生5");
        for (student s : treeMap.keySet()) {//student s
            System.out.println(s.toString());
        }
    }
}

package com.pigtom.study.introduction_algorithms.other;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutation {

    // the element from i+1 to end of array a is desc order
    // find the smallest element a[k] in array a that is bigger than a[i]
    // and then swap a[k] and a[i]
    // and then reverse element of a from index i+1 to the end
    public static void rearrange(int[] a, int i) {
        int len = a.length;
        if (i >= len - 1) {
            return;
        }
        // assert i < len - 1
        int k = len - 1;
        for (; k > i; k--) {

            // 找到一个大于a[i]的最小的数
            if (a[k] > a[i])
                break;
        }
        assert k == i;
        // swap a[i] and a[k];
        int temp = a[i];
        a[i] = a[k];
        a[k] = temp;

        // reverse a from i+1 to len-1
        int n = len - 1;
        for (k = i + 1; k < n; k++, n--) {
            temp = a[k];
            a[k] = a[n];
            a[n] = temp;
        }
    }

    public static void main(String[] args) {
        int[] a = {1,3,2,5,7,9,8};
        List<int[]> list = new ArrayList<>();

        // get sorted array asc order
        Arrays.sort(a);
        int[] a2 = Arrays.copyOf(a, a.length);
        list.add(a2);
        int len = a.length;
        int index = len - 1;
        while (index > 0) {

            // if a[index -1] < a[index]
            // then says this permutation should be rearrange
            // eg: 15432
            // a[index - 1] = 1
            // a[index]     = 5
            // rearrange result: 21345
            if (a[index - 1] < a[index]) {
                rearrange(a, index-1);
                index = len - 1;
                a2 = Arrays.copyOf(a, a.length);
                list.add(a2);
            }
            // if a[index - 1] > a[index]
            // then say sequence from a[index...end] is derder, and it is the last permutation
            // eg: 15432
            // a[index - 1] = 3
            // a[index]     = 2
            // index should go forward and result a[index]=3
            else {
                index--;
            }
        }
        for (int i =0; i < 50; i ++){
            int[] arr = list.get(i);
            System.out.println(Arrays.toString(arr));
        }

        // the total size of permutation
        int sum = 1;
        for (int i = 1; i <= len; i++) {
            sum *= i;
        }
        System.out.println(sum);
        System.out.println(list.size());
    }
}

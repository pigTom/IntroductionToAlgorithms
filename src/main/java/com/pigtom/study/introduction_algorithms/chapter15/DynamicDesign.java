package com.pigtom.study.introduction_algorithms.chapter15;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class DynamicDesign {
    /**
     * 根据钢条价格表算出钢条的
     *
     */
    void cutRod(int[] a, int len) {
        int[] b = new int[len + 1];
        int[] c = new int[len + 1];
        c[0]=0;
        // array a is given valuable
        for (int i = 1; i <= len; i++) {
            int q = a[i];
            c[i] = i;
            for (int j = 1; j <= i / 2; j++) {
                if (q < (b[j] + b[i - j])) {
                    q = b[j] + b[i - j];
                    c[i] = j;
                }
            }
            b[i] = q;
        }
        System.out.println(Arrays.toString(c));
        System.out.println(Arrays.toString(b));
        int n = c[len];
        while (n > 0) {
            System.out.print(c[n] + " ");
            n = len - n;
        }
    }

    @Test
    public void test() {
        int[] a = {-1, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        cutRod(a, a.length - 1);
    }

    public void addNewMethod () {
        // add to release1.0 brance
    }
}

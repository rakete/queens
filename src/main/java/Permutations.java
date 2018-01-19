package org.rastermann.compilerworks;

import java.util.function.Function;

import java.util.HashMap;
import java.util.Map;

public class Permutations {
    Integer permutation[];
    long num_permutations;

    public Permutations(int n) {
        if( n < 0 ) {
            throw new IllegalArgumentException();
        }

        Integer a[] = new Integer[n];
        for( int i = 0; i < n; i++ ) {
            a[i] = i;
        }

        permutation = a;
        num_permutations = factorial(a.length);
    }

    public static boolean print(Integer[] permutation) {
        System.out.format("[");
        for( int i = 0; i < permutation.length; i++ ) {
            System.out.format(" %d", permutation[i]);
        }
        System.out.format(" ]\n");

        return true;
    }

    public static long factorial(long n) {
        if (n == 0) {
            return 0;
        }

        if (n < 0 || n > 20) {
            throw new IllegalArgumentException();
        }

        long ret = 1;
        for (int i = 2; i <= n; i++) {
            ret *= i;
        }
        return ret;
    }

    public boolean permute(int i, Function<Integer[], Boolean> f) {
        if( i >= permutation.length - 1 ) {
            return f.apply(permutation);
        }

        for (int j = i; j < permutation.length; j++) {

            assert j >= i;
            if( j > i ) {
                int tmp = permutation[i];
                permutation[i] = permutation[j];
                permutation[j] = tmp;

                if( ! permute(i+1, f) ) {
                    return false;
                }

                tmp = permutation[i];
                permutation[i] = permutation[j];
                permutation[j] = tmp;
            } else if( ! permute(i+1, f) ) {
                return false;
            }
        }

        return true;
    }
}

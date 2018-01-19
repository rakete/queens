/**
 * Copyright 2018 Andreas Raster
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.rastermann.compilerworks;

import java.util.function.Function;

public class Permutations {
    private Integer[] permutation;
    public long numPermutations;

    public Permutations(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }

        permutation = a;
        numPermutations = factorial(a.length);
    }

    public static boolean print(Integer[] permutation) {
        System.out.format("[");
        for (int i = 0; i < permutation.length; i++) {
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
        if (i >= permutation.length - 1) {
            return f.apply(permutation);
        }

        for (int j = i; j < permutation.length; j++) {
            assert j >= i;
            if (j > i) {
                int tmp = permutation[i];
                permutation[i] = permutation[j];
                permutation[j] = tmp;

                if (!permute(i + 1, f)) {
                    return false;
                }

                tmp = permutation[i];
                permutation[i] = permutation[j];
                permutation[j] = tmp;
            } else if (!permute(i + 1, f)) {
                return false;
            }
        }

        return true;
    }
}

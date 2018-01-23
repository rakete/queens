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

import java.util.function.BiFunction;
import java.util.function.Consumer;

import java.util.Arrays;

public class Permutations {
    private Integer[] permutation;
    public long numPermutations;

    public Permutations(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        Integer[] initialPermutation = new Integer[n];
        for (int i = 0; i < n; i++) {
            initialPermutation[i] = i;
        }

        permutation = initialPermutation;
        numPermutations = factorial(initialPermutation.length);
    }

    public static long factorial(long n) {
        if (n == 0) {
            return 0;
        }

        // - anything larger than 20 would produce an integer overflow, so I just throw an exception
        // in that case, same as with negative arguments
        if (n < 0 || n > 20) {
            throw new IllegalArgumentException();
        }

        long ret = 1;
        for (int i = 2; i <= n; i++) {
            ret *= i;
        }
        return ret;
    }


    public static void print(Integer[] permutation) {
        System.out.println(Arrays.toString(permutation));
    }

    public void permute(int i, BiFunction<Integer[], Integer, Boolean> test, Consumer<Integer[]> collect) {
        if (i < 0 || i > permutation.length) {
            throw new IllegalArgumentException();
        }

        // - found this method here:
        // https://stackoverflow.com/questions/30387185/print-out-all-permutations-of-an-array
        // - this is a mix between an iterative an recursive method, which makes it relativly easy
        // to understand, but still fast
        // - this does permutations with a breadthfirst approach, meaning it first recursivly walks
        // along the permutation array until it hits the end, then starts flipping elements while
        // backtracking to the start, this is done until all possible swaps are exhausted
        // - the recursive calls increase i, which indicates the 'fixed' element with which
        // to swap, the for loop increases j, which indicates the element with which to swap
        // the fixed element with: [0 1 2 3] -> i = 2, j = 3 -> [0 1 3 2]
        int tmp = 0;
        for (int j = i; j < permutation.length; j++) {
            // - the algorithm starts with j=i, and it has to so that we can backtrack to here
            // again, but I check for i > j before swapping to avoid unecessary swaps
            if (j > i) {
                tmp = permutation[i];
                permutation[i] = permutation[j];
                permutation[j] = tmp;
            }

            // - this is the center piece of the algorithm where we decide if we collect a 'finished' permutation,
            // or if we continue or stop recuring
            if (i >= permutation.length - 1) {
                // - a permutation is 'finished' when i is the last indexable position in the permutation array,
                // then we test.apply to decide if the permutation should be collected with collect.accept
                if (test.apply(permutation, permutation.length)) {
                    collect.accept(permutation);
                }
            } else if (test.apply(permutation, i + 1)) {
                // - if i + 1 is not the end of permutation, then we test.apply a partial permutation to see if we
                // should continue recurring or abort early, notice the last argument to test.apply above is
                // permutation.length, meaning it tests the whole permutation, and second it is i + 1, meaning it
                // tests the permutation beginning from 0 up until index i + 1
                permute(i + 1, test, collect);
            }

            if (j > i) {
                tmp = permutation[i];
                permutation[i] = permutation[j];
                permutation[j] = tmp;
            }
        }
    }

    public void permute(int i, Consumer<Integer[]> collect) {
        permute(i, (r, c) -> { return true; }, collect);
    }
}

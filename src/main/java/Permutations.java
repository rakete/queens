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

    public boolean permute(int i, Function<Integer[], Boolean> f) {
        if (i >= permutation.length - 1) {
            return f.apply(permutation);
        }

        // - found this method here:
        // https://stackoverflow.com/questions/30387185/print-out-all-permutations-of-an-array
        // - I changed it a little, adding the if condition and an assert to make it more clear
        // how this works
        // - I also added a boolean return value to this function and the f argument so that the
        // caller can abort permuting by returning false
        // - this is a mix between an iterative an recursive method, which makes it relativly easy
        // to understand, but still fast
        // - on the first invocation with i = 0, the if above will do nothing, the for loop is started
        // with j = i = 0, which then means j == i and the else condition below is followed. this continues
        // until we reach the end of the permutation array, then the above if triggers and applies
        // f to the current permutation array, which is at this point completly unchanged, therefore
        // the first time f is called with just [0, 1, 2, ...., n] as input
        // - we then return to the previous call, where the for loop runs once, increases j, so that now
        // j > i and the main if body is triggered, where we swap two elements of the array, then call
        // ourself recursively, again triggering the above if and applying f to permutation with the last
        // two elements swapped
        // - now again we return to the for loop that already ran increasing j to j > i, now it would
        // increase once more, but that would make j >= permutation.length, which means instead we
        // return true below, go the the previous call again (the second to last one), increase j,
        // then swap two elements (the third to last with the second to last), and call ourself
        // recursivly again
        // - this continues until in the very first call, j ran from i = 0 to permutation.length, after
        // which the first element was switched with every other element at least once, which means
        // there are no more possible enumerations left, and we are done
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

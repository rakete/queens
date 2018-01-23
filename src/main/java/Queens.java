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

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

final class Queens {
    private Queens() {
    }

    public static void print(Integer[] config) {
        System.out.format("Solution: ");
        Permutations.print(config);

        for (int i = 0; i < config.length; i++) {
            for (int j = 0; j < config.length; j++) {
                if (config[i] == j) {
                    System.out.format(" Q");
                } else {
                    System.out.format(" .");
                }
            }
            System.out.format("\n");
        }
        System.out.format("\n");
    }

    public static boolean testBoard(Integer[] config, Integer end) {
        if (end > config.length) {
            throw new IllegalArgumentException();
        }

        // - this is where the magic happens
        // - the queens are initialized as array of integers, where each integer has exactly one bit
        // set for a queen in a row, and all integers in the array are different, so something like
        // this for n = 4:
        // 0: 1 0 0 0
        // 1: 0 1 0 0
        // 2: 0 0 1 0
        // 3: 0 0 0 1
        // - a config is a board configuration determined by an ordering of rows, so something like
        // [2, 0, 3, 1] would represent a board like this:
        // 2: 0 0 1 0
        // 0: 1 0 0 0
        // 3: 0 0 0 1
        // 1: 0 1 0 0
        // - to determine if a config is a valid solution we have to test if there are any two rows
        // with queens that threaten each other, we do not have to test if there are two queens in
        // a single row or column, because that is impossible, we only have to test if there are
        // two queens on a diagonal
        // - two rows threaten each other on the diagonal when one of them bitshifted left or right
        // by the amount of rows they are apart, equals the value of the other
        // - so with config [2, 0, 3, 1], to test if the i = 0 row (a), threatens the j = 1 row (b), we
        // get their indices from the config (config[i] = 2 and config[j] = 0), then get the row values
        // for a = queens[config[i]] = [0 0 1 0] and b = queens[config[j]] = [1 0 0 0], then compute
        // how much the rows are apart d = j - i = 1, and then we bitshift the row value a left and
        // right by d and test if either of the two resulting values are equal to b:
        // [0 0 1 0] >> 1 = [0 0 0 1] != [1 0 0 0] and [0 0 1 0] << 1 = [0 1 0 0] != [1 0 0 0]
        // - in this case they are not equal, which means we can continue testing rows against each other
        // in this fashion, if they were equal it would mean the queens in these rows threaten each
        // other, and we can discard the whole config because it cannot possibly be a valid solution
        // anymore
        // - notice that j only goes forward, we do not need to test row combinations twice, so if
        // we tested the 0 vs 1, we do not need to test 1 vs 0 as well, by only increasing j from
        // i to config.length(now replaced by end argument), and not decreasing it as well, we only
        // test the combinations the we have not tested yet

        // changelog 20.01.2018:
        // - I added the end argument to this function later so that this test can be limited to only
        // a partial set of rows, so that I can use it to prune a permutation subtree from the tree of
        // possible solutions

        // changelog 23.01.2018:
        // - the above is not entirely accurate anymore, I realised that using bitshifts is wasteful
        // and I can just use a single integer to represent the column where a queen stands, then
        // instead of using bitshifts, I can use addition and subtraction to test if two rows
        // threaten each other
        // - that means I can just use the integers in config directly, instead of having an extra
        // array with bitshifted integers to represent the queens

        if (config.length <= 4) {
            System.out.print(Arrays.toString(config));
            System.out.format(" ");
            System.out.format("end:%d ", end);
        }

        for (int i = 0; i < end - 1; i++) {
            if (config.length <= 4) {
                System.out.format("i:%d ", i);
            }
            for (int j = i + 1; j < end; j++) {

                int a = config[i];
                int b = config[j];

                if (a < 0 || b < 0 || a > config.length || b > config.length) {
                    throw new IllegalArgumentException();
                }

                int d = j - i;

                if (config.length <= 4) {
                    System.out.format("j:%d a:%d b:%d, ", j, a, b);
                }
                if (b == (a - d) || b == (a + d)) {
                    if (config.length <= 4) {
                        System.out.format("false\n");
                    }
                    return false;
                }
            }
        }
        if (config.length <= 4) {
            System.out.format("true\n");
        }
        return true;
    }

    public static boolean testRow(Integer[] config, Integer row) {
        if (row > config.length) {
            throw new IllegalArgumentException();
        }

        // - the above testBoard function always tests the whole board for threatening rows, this is not optimal
        // when using the testBoard function to test partial permutations, because we end up testing row combinations
        // that we already tested again, when with go further down a tree branch
        // - example: when testing [2, 0, 3, 1] we first test 2 vs 0, if these do not threaten each other we recur
        // further and then the next call to testBoard would test 2 vs 0, then 2 vs 3 and 0 vs 3, unecessarily
        // repeating the first 2 vs 0 test
        // - what needs to be done is to only test 2 vs 3 and 0 vs 3, that means when we test a row, we test
        // only this row against all preceding rows, and this is what this function does

        if (config.length <= 4) {
            System.out.print(Arrays.toString(config));
            System.out.format(" ");
            System.out.format("row:%d ", row);
        }

        int b = config[row - 1];
        for (int i = 0; i < row - 1; i++) {
            int a = config[i];

            if (a < 0 || b < 0 || a > config.length || b > config.length) {
                throw new IllegalArgumentException();
            }

            int d = row - 1 - i;

            if (config.length <= 4) {
                System.out.format("a:%d b:%d d:%d, ", a, b, d);
            }
            if (b == (a - d) || b == (a + d)) {
                if (config.length <= 4) {
                    System.out.format("false\n");
                }
                return false;
            }
        }

        if (config.length <= 4) {
            System.out.format("true\n");
        }
        return true;
    }

    public static List<Integer[]> allSolutions(int n) {
        Permutations p = new Permutations(n);

        ArrayList<Integer[]> solutions = new ArrayList<Integer[]>();
        p.permute(0, (r, c) -> testRow(r, c), c -> solutions.add(c));

        return solutions;
    }
}

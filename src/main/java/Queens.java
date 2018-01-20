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

public class Queens {
    private int[] queens;

    public Queens(int n) {
        if (n < 2) {
            throw new IllegalArgumentException();
        }

        queens = new int[n];
        for (int i = 0; i < n; i++) {
            queens[i] = 1 << i;
        }
    }

    public void print(Integer[] config) {
        System.out.format("Solution: ");
        Permutations.print(config);

        for (int i = 0; i < config.length; i++) {
            for (int j = 0; j < config.length; j++) {
                if (queens[config[i]] == 1 << j) {
                    System.out.format(" Q");
                } else {
                    System.out.format(" .");
                }
            }
            System.out.format("\n");
        }
        System.out.format("\n");
    }

    public boolean test(Integer[] config) {
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
        // i to config.length, and not decreasing it as well, we only test the combinations the we
        // have not tested yet
        for (int i = 0; i < config.length - 1; i++) {
            for (int j = i + 1; j < config.length; j++) {
                int a = queens[config[i]];
                int b = queens[config[j]];
                int d = j - i;

                if (b == (a >> d) || b == (a << d)) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Integer[]> allSolutions() {
        Permutations p = new Permutations(queens.length);

        ArrayList<Integer[]> solutions = new ArrayList<Integer[]>();
        p.permute(0, c -> {
                if (test(c)) {
                    solutions.add(c);
                }
                return true;
            });

        return solutions;
    }
}

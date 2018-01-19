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
                    System.out.format(" 1");
                } else {
                    System.out.format(" 0");
                }
            }
            System.out.format("\n");
        }
        System.out.format("\n");
    }

    public boolean test(Integer[] config) {
        for (int i = 0; i < config.length - 1; i++) {
            for (int k = i + 1; k < config.length; k++) {
                int a = queens[config[i]];
                int b = queens[config[k]];
                int d = k - i;

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

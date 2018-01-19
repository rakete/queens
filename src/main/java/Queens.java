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

import org.rastermann.compilerworks.Permutations;

public class Queens {
    int queens[];

    public Queens(int n) {
        queens = new int[n];
        for( int i = 0; i < n; i++ ) {
            queens[i] = 1 << i;
        }
    }

    public void print(Integer[] config) {
        for( int i = 0; i < config.length; i++ ) {
            for( int j = 0; j < config.length; j++ ) {
                if( queens[config[i]] == 1 << j ) {
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
        for( int i = 0; i < config.length-1; i++ ) {
            for( int k = i+1; k < config.length; k++ ) {
                int a = queens[config[i]];
                int b = queens[config[k]];
                int d = k - i;

                if( b == (a >> d) || b == (a << d) ) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Integer config[] = {0, 1, 2, 3};

        Permutations p = new Permutations(config);
        System.out.format("num_permutations: %d\n", p.num_permutations);

        Queens q = new Queens(4);
        Integer foo[] = {2,0,3,1};
        Integer bar[] = {0,1,2,3};
        Integer baz[] = {2,0,1,3};

        if( q.test(foo) ) {
            q.print(foo);
        }

        Queens q2 = new Queens(8);
        Integer solution[] = {2,4,1,7,0,6,3,5};
        if( q2.test(solution) ) {
            q2.print(solution);
        }
    }
}

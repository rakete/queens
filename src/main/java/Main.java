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
import org.rastermann.compilerworks.Queens;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        int n = 8;

        Permutations p = new Permutations(n);
        System.out.format("num_permutations: %d\n\n", p.num_permutations);

        AtomicInteger num_solutions = new AtomicInteger(0);
        Queens q = new Queens(n);
        p.permute(0, c -> {
                if( q.test(c) ) {
                    q.print(c);
                    num_solutions.getAndIncrement();
                }
                return true;
            });
        System.out.format("num_solutions: %d\n", num_solutions.get());
    }
}

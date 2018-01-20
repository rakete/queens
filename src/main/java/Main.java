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

import java.util.concurrent.atomic.AtomicInteger;

public final class Main {
    private Main() {
    }
    public static void main(String[] args) {
        int n = 8;

        Queens q = new Queens(n);
        Permutations p = new Permutations(n);
        System.out.format("numPermutation: %d\n", p.numPermutations);

        AtomicInteger numTests = new AtomicInteger(0);
        AtomicInteger numSolutions = new AtomicInteger(0);
        p.permute(0,
                  (c, r) -> {
                      numTests.getAndIncrement();
                      return q.test(c, r);
                  },
                  c -> {
                      numSolutions.getAndIncrement();
                      if( n <= 8 ) {
                          q.print(c);
                      }
                  });
        System.out.format("numTests: %d\n", numTests.get());
        System.out.format("numSolutions: %d\n", numSolutions.get());
    }
}

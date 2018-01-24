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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class TestPermutations {

    @Test public void inputTest() {
        try {
            Permutations.factorial(-1);
            fail("Exception not thrown");
        } catch (IllegalArgumentException e) {
        };

        try {
            Permutations wrong = new Permutations(-1);
            fail("Exception not thrown");
        } catch (IllegalArgumentException e) {
        };

        try {
            Permutations p = new Permutations(4);
            p.permute(-1, c -> { return; });
            fail("Exception not thrown");
        } catch (IllegalArgumentException e) {
        };

        try {
            int n = 4;
            Permutations p = new Permutations(n);
            p.permute(n + 1, c -> { return; });
            fail("Exception not thrown");
        } catch (IllegalArgumentException e) {
        };
    }

    @Test public void factorialTest() {
        assertEquals(Permutations.factorial(0), 0);
        assertEquals(Permutations.factorial(1), 1);
        assertEquals(Permutations.factorial(2), 2);
        assertEquals(Permutations.factorial(10), 3628800L);
        assertEquals(Permutations.factorial(20), 2432902008176640000L);
    }

    @Test public void permutationsTest() {
        int n = 4;

        Permutations p = new Permutations(n);
        assertEquals(p.numPermutations, Permutations.factorial(n));

        AtomicInteger permutationCounter = new AtomicInteger(0);
        p.permute(0, c -> {permutationCounter.getAndIncrement();});
        assertEquals(permutationCounter.get(), p.numPermutations);

        Map<String, Boolean> collection = new HashMap<String, Boolean>();
        p.permute(0, c -> {
                String s = Arrays.toString(c);
                assertEquals(collection.get(s), null);
                collection.put(s, true);
            });

        p.permute(0, c -> { return; });
        p.permute(0, c -> { return; });
        assertEquals(p.numPermutations, Permutations.factorial(n));
    }
}

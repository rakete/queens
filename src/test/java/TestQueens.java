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

import static org.junit.Assert.*;
import org.junit.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class TestQueens {

    @Test public void inputTest() {
        try {
            Queens q = new Queens(-1);
            fail("Exception not thrown");
        } catch(IllegalArgumentException e) {
        };

        try {
            Queens q = new Queens(0);
            fail("Exception not thrown");
        } catch(IllegalArgumentException e) {
        };

        try {
            Queens q = new Queens(1);
            fail("Exception not thrown");
        } catch(IllegalArgumentException e) {
        };
    }


    @Test public void countingTest() {
        Queens q = new Queens(8);
        List<Integer[]> solutions = q.allSolutions();
        assertEquals(solutions.size(), 92);
    }

    @Test public void distinctSolutionsTest() {
        Integer[][] distinct_solutions = {
            {3, 6, 2, 7, 1, 4, 0, 5},
            {4, 1, 3, 6, 2, 7, 5, 0},
            {3, 1, 6, 2, 5, 7, 4, 0},
            {3, 5, 7, 2, 0, 6, 4, 1},
            {2, 5, 7, 0, 3, 6, 4, 1},
            {4, 2, 7, 3, 6, 0, 5, 1},
            {4, 6, 3, 0, 2, 7, 5, 1},
            {3, 0, 4, 7, 5, 2, 6, 1},
            {2, 5, 3, 0, 7, 4, 6, 1},
            {5, 1, 6, 0, 3, 7, 4, 2},
            {3, 6, 0, 7, 4, 1, 5, 2},
            {5, 3, 6, 0, 7, 1, 4, 2}
        };

        Map<String, Boolean> my_solutions = new HashMap<String, Boolean>();
        Permutations p = new Permutations(8);
        Queens q = new Queens(8);
        p.permute(0, c -> {
                if( q.test(c) ) {
                    String s = Arrays.toString(c);
                    assertEquals(my_solutions.get(s), null);
                    my_solutions.put(s, true);
                }
                return true;
            });

        for( int i = 0; i < distinct_solutions.length; i++ ) {
            String s = Arrays.toString(distinct_solutions[i]);
            assertEquals(my_solutions.get(s), true);
        }
    }

    @Test public void wrongSolutionsTest() {
        Integer[][] wrong_solutions = {
            {0, 1, 2, 3, 4, 5, 6, 7},
            {3, 6, 1, 7, 2, 4, 0, 5},
            {4, 1, 3, 5, 2, 7, 6, 0},
            {3, 1, 7, 2, 5, 6, 4, 0},
            {3, 5, 7, 2, 6, 0, 4, 1},
            {2, 5, 7, 0, 3, 6, 1, 4},
            {4, 7, 2, 3, 6, 0, 5, 1},
            {7, 6, 5, 4, 3, 2, 1, 0}
        };

        Map<String, Boolean> my_solutions = new HashMap<String, Boolean>();
        Permutations p = new Permutations(8);
        Queens q = new Queens(8);
        p.permute(0, c -> {
                if( q.test(c) ) {
                    String s = Arrays.toString(c);
                    assertEquals(my_solutions.get(s), null);
                    my_solutions.put(s, true);
                }
                return true;
            });

        for( int i = 0; i < wrong_solutions.length; i++ ) {
            String s = Arrays.toString(wrong_solutions[i]);
            assertEquals(my_solutions.get(s), null);
        }
    }
}

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

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class TestQueens {

    @Test public void inputTest() {
        try {
            Integer[] foo = new Integer[2];
            Queens.testBoard(foo, foo.length + 1);
            fail("Exception not thrown");
        } catch (IllegalArgumentException e) {
        };

        try {
            Integer[] foo = new Integer[2];
            Queens.testBoard(foo, 4);
            fail("Exception not thrown");
        } catch (IllegalArgumentException e) {
        };

        try {
            Integer[] foo = new Integer[2];
            Queens.testRow(foo, foo.length + 1);
            fail("Exception not thrown");
        } catch (IllegalArgumentException e) {
        };

        try {
            Integer[] foo = new Integer[2];
            Queens.testRow(foo, 4);
            fail("Exception not thrown");
        } catch (IllegalArgumentException e) {
        };
    }


    @Test public void countingTest() {
        List<Integer[]> solutions = Queens.allSolutions(4);
        assertEquals(solutions.size(), 2);

        solutions = Queens.allSolutions(5);
        assertEquals(solutions.size(), 10);

        solutions = Queens.allSolutions(6);
        assertEquals(solutions.size(), 4);

        solutions = Queens.allSolutions(7);
        assertEquals(solutions.size(), 40);

        solutions = Queens.allSolutions(8);
        assertEquals(solutions.size(), 92);

        solutions = Queens.allSolutions(9);
        assertEquals(solutions.size(), 352);

        solutions = Queens.allSolutions(10);
        assertEquals(solutions.size(), 724);
    }

    @Test public void distinctSolutionsTest() {
        Integer[][] distinctSolutions = {
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

        Map<String, Boolean> mySolutions = new HashMap<String, Boolean>();
        Permutations p = new Permutations(8);
        p.permute(0, c -> {
                if (Queens.testBoard(c, c.length)) {
                    String s = Arrays.toString(c);
                    assertEquals(mySolutions.get(s), null);
                    mySolutions.put(s, true);
                }
            });

        for (int i = 0; i < distinctSolutions.length; i++) {
            String s = Arrays.toString(distinctSolutions[i]);
            assertEquals(mySolutions.get(s), true);
        }
    }

    @Test public void wrongSolutionsTest() {
        Integer[][] wrongSolutions = {
            {0, 1, 2, 3, 4, 5, 6, 7},
            {3, 6, 1, 7, 2, 4, 0, 5},
            {4, 1, 3, 5, 2, 7, 6, 0},
            {3, 1, 7, 2, 5, 6, 4, 0},
            {3, 5, 7, 2, 6, 0, 4, 1},
            {2, 5, 7, 0, 3, 6, 1, 4},
            {4, 7, 2, 3, 6, 0, 5, 1},
            {7, 6, 5, 4, 3, 2, 1, 0}
        };

        Map<String, Boolean> mySolutions = new HashMap<String, Boolean>();
        Permutations p = new Permutations(8);
        p.permute(0, c -> {
                if (Queens.testBoard(c, c.length)) {
                    String s = Arrays.toString(c);
                    assertEquals(mySolutions.get(s), null);
                    mySolutions.put(s, true);
                }
            });

        for (int i = 0; i < wrongSolutions.length; i++) {
            String s = Arrays.toString(wrongSolutions[i]);
            assertEquals(mySolutions.get(s), null);
        }
    }
}

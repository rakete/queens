package org.rastermann.compilerworks;

import static org.junit.Assert.*;
import org.junit.*;

import org.rastermann.compilerworks.Permutations;
import java.util.concurrent.atomic.AtomicInteger;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class TestPermutations {

    @Test public void factorialTest() {
        try {
            Permutations.factorial(-1);
            fail("Exception not thrown");
        } catch(IllegalArgumentException e) {
        };

        assertEquals(Permutations.factorial(0), 0);
        assertEquals(Permutations.factorial(1), 1);
        assertEquals(Permutations.factorial(2), 2);
        assertEquals(Permutations.factorial(10), 3628800L);
        assertEquals(Permutations.factorial(20), 2432902008176640000L);
    }

    @Test public void permutationsTest() {
        Integer config[] = {0, 1, 2, 3};

        Permutations p = new Permutations(config);
        assertEquals(p.num_permutations, Permutations.factorial(config.length));

        AtomicInteger permutation_counter = new AtomicInteger(0);
        assertEquals(p.permute(0, c -> {permutation_counter.getAndIncrement(); return true;}), true);
        assertEquals(permutation_counter.get(), p.num_permutations);

        Map<String, Boolean> collection = new HashMap<String, Boolean>();
        boolean all_unique = p.permute(0, c -> {
                String s = Arrays.toString(c);
                System.out.println(s);

                if( collection.get(s) == null ) {
                    collection.put(s, true);
                    return true;
                } else {
                    return false;
                }
            });
        assertEquals(all_unique, true);

        p.permute(0, c -> {return true;});
        p.permute(0, c -> {return true;});
        assertEquals(p.num_permutations, Permutations.factorial(config.length));

        Integer wrong1[] = {-1, 0, 1, 2};
        try {
            Permutations pwrong1 = new Permutations(wrong1);
            fail("Exception not thrown");
        } catch(IllegalArgumentException e) {
        };

        Integer wrong2[] = {0, 0, 1, 2};
        try {
            Permutations pwrong2 = new Permutations(wrong2);
            fail("Exception not thrown");
        } catch(IllegalArgumentException e) {
        };
    }
}

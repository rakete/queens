package org.rastermann.compilerworks;

import static org.junit.Assert.*;
import org.junit.*;

import org.rastermann.compilerworks.Queens;

public class TestQueens {

    @Test public void factorialTest() {
        Queens q = new Queens();

        try {
            q.factorial(-1);
            fail("Exception not thrown");
        } catch(IllegalArgumentException e) {
        };

        assertEquals(q.factorial(0), 0);
        assertEquals(q.factorial(1), 1);
        assertEquals(q.factorial(2), 2);
        assertEquals(q.factorial(10), 3628800L);
        assertEquals(q.factorial(20), 2432902008176640000L);

    }
}

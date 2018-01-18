package org.rastermann.compilerworks;

import static org.junit.Assert.*;
import org.junit.*;

import org.rastermann.compilerworks.HelloWorld;

public class TestTest {

    @Test public void factorialTest() {
        HelloWorld hw = new HelloWorld();

        try {
            hw.factorial(-1);
            fail("Exception not thrown");
        } catch(IllegalArgumentException e) {
        };

        assertEquals(hw.factorial(0), 0);
        assertEquals(hw.factorial(1), 1);
        assertEquals(hw.factorial(2), 2);
        assertEquals(hw.factorial(10), 3628800L);
        assertEquals(hw.factorial(20), 2432902008176640000L);

    }
}

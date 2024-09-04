package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesF(){
        IntList l = IntList.of(13, 15, 16, 18);
        boolean changed = IntListExercises.squarePrimes(l);
        assertEquals("169 -> 15 -> 16 -> 18", l.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesE(){
        IntList l = IntList.of(14, 15, 16, 17);
        boolean changed = IntListExercises.squarePrimes(l);
        assertEquals("14 -> 15 -> 16 -> 289", l.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesN(){
        IntList l = IntList.of(14, 15, 16, 18);
        boolean changed = IntListExercises.squarePrimes(l);
        assertEquals("14 -> 15 -> 16 -> 18", l.toString());
        assertFalse(changed);
    }
    @Test
    public void testSquarePrimes2(){
        IntList l = IntList.of(11, 13, 14, 15, 16, 18);
        boolean changed = IntListExercises.squarePrimes(l);
        assertEquals("121 -> 169 -> 14 -> 15 -> 16 -> 18", l.toString());
        assertTrue(changed);
    }
}

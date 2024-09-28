package flik;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;
public class flikTest {
    @Test
    public void testEqual(){
        int a = 200;
        int b = 200;
        assertEquals(Flik.isSameNumber(a, b), true);
    }
}

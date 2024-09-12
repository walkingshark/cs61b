package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove(){
        AListNoResizing<Integer> a = new AListNoResizing<>();
        BuggyAList<Integer> b = new BuggyAList<>();
        for (int i = 1; i <= 3; i++){
            a.addLast(i);
            b.addLast(i);
        }
        for (int i = 1; i <= 3; i++){
            assertEquals(a.removeLast() == b.removeLast(),true);
        }
    }

    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
                //System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size1 = L.size();
                int size2 = B.size();
                //System.out.println("size: " + size1);
                //System.out.println("buggySize: " + size2);
            } else if (operationNumber == 2 && L.size() > 0) {
                int x = L.getLast();
                int y = B.getLast();
                assertEquals(x == y, true);
            } else if (operationNumber == 3 && L.size() > 0) {
                int x = L.removeLast();
                int y = B.removeLast();
                assertEquals(x == y, true);
            }
        }

    }
}

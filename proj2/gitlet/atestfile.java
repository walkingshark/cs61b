package gitlet;

import java.util.*;

public class atestfile {
    public static void main(String[] args) {
        Queue<String> q = new ArrayDeque<>();
        q.add("a");
        q.add("b");
        q.add("c");
        for (int i = 0; i < q.size(); i++) {
            q.poll();
            q.add("d");
        }
        System.out.println(q);

    }
}


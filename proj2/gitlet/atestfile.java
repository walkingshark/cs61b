package gitlet;
import java.util.Date;
import java.util.TreeMap;

public class atestfile {
    public static void main(String[] args) {

        TreeMap<String, String> m = new TreeMap<>();
        if (m.isEmpty()) {
            System.out.println("yes");
        }
        if (!m.containsKey(null)) {
            System.out.println("doesn't contain this key.");
        }
    }
}


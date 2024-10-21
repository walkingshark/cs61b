package gitlet;

import java.io.File;
import java.util.HashMap;

import static gitlet.Utils.join;

public class StagingArea {
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static HashMap<String, String> Ad;
    public static File Rm = join(GITLET_DIR, "stageRm");
    // A file contain files??
    // Indirection: maybe create a set contains of sha-1 code of file,
    // which is the name of that file(or a map)
    public void add(String filename) {
        // Ad.put(filename, now_blob_id);
    }
}

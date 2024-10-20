package gitlet;

import java.io.File;

import static gitlet.Utils.join;

public class StagingArea {
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static File Ad = join(GITLET_DIR, "stageAdd");
    public static File Rm = join(GITLET_DIR, "stageRm");
    // A file contain files??
    // Indirection: maybe create a set contains of sha-1 code of file,
    // which is the name of that file(or a map)
}

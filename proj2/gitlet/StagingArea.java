package gitlet;

import java.io.File;

import static gitlet.Utils.join;

public class StagingArea {
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File stageAdd = join(GITLET_DIR, "stageAdd");
    public static final File stageRm = join(GITLET_DIR, "stageRm");
}

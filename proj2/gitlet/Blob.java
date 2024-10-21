package gitlet;

import java.io.File;

import static gitlet.Utils.*;

public class Blob {
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public File blobs = join(GITLET_DIR, "blobs");
    public File content;
    public String id;
    public Blob() {
        // read and write
        String s = readContentsAsString(CWD);
        id = sha1(s);
        content = join(blobs, id);
        writeContents(content, s);
    }
}

package gitlet;

import java.io.File;

import static gitlet.Utils.*;
// blob file need to change
public class Blob {
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File BLOBS = join(GITLET_DIR, "blobs");

    public File content;
    public String id;
    public String name;
    public Blob(String filename) {
        // read and write
        name = filename;
        String s = readContentsAsString(CWD);
        id = sha1(s);
        content = join(BLOBS, id);
        writeContents(content, s);
    }
}

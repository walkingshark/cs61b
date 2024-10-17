package gitlet;

import java.io.File;
import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */
    // A commit tree
    // but what data structures does this use?
    // Is it a tree?

    // how to actually represent pointer?

    // branch pointer

    // head pointer

    // some blobs

    // blob pointer

    // parent pointer

    // staging area(2)
    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /* TODO: fill in the rest of this class. */
    // init method in Main calls this constructor to get a new repo.
    // What's a repo?
    // make cwd a gitlet repo
    // how to properly represent date?
    public Repository() {
        //set up a new repo(copy from lab6)
        if (!GITLET_DIR.exists()) {
            GITLET_DIR.mkdir();
        }
    }
    /** this is probably the commit command and since I'm still working on
    init, probably should add first commit in a easier way.*/
    public void addCommit() {

    }
}

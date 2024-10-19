package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

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
    HashMap<String, Commit> commits = new HashMap<>();
    // how to actually represent pointer?

    // branch pointer
    Commit branch = new Commit();
    // head pointer
    Commit head = new Commit();
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
        GITLET_DIR.mkdir();
        Commit initialCommit = new Commit("initial commit");
        String id = sha1(serialize(initialCommit)); // there's a bug
        commits.put(id, initialCommit);
        File commitFile = join(GITLET_DIR, id);
        File treeFile = join(GITLET_DIR, "commitTree");
        writeObject(commitFile, initialCommit);
        writeObject(treeFile, treeFile);
    }
    /** this is probably the commit command and since I'm still working on
    init, probably should add first commit in a easier way.*/
    public void addCommit() {

    }
}

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
    public static HashMap<String, Commit> commits = new HashMap<>();
    // how to actually represent pointer?

    // public HashMap<String, String> commitPointers = new HashMap<>();
    public static String head;
    public static String master;
    // blob pointer

    // parent pointer

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    // some blobs
    public static final File BLOBS = join(GITLET_DIR, "blobs");
    // staging area(2)
    public static final File ADD = join(GITLET_DIR, "add");
    public HashMap<String, String> add = new HashMap<>();
    public static final File REMOVE = join(GITLET_DIR, "remove");
    public HashMap<String, String> remove = new HashMap<>();
    /* TODO: fill in the rest of this class. */
    // init method in Main calls this constructor to get a new repo.
    // What's a repo?
    // make cwd a gitlet repo
    // how to properly represent date?
    public Repository() {
        //set up a new repo(copy from lab6)
        if (!GITLET_DIR.exists()) {
            GITLET_DIR.mkdir();
            BLOBS.mkdir();
            Commit initialCommit = new Commit("initial commit");
            String id = sha1(serialize(initialCommit));
            commits.put(id, initialCommit);
            File commitFile = join(GITLET_DIR, id);
            File treeFile = join(GITLET_DIR, "commitTree");
            head = getId(initialCommit);
            master = getId(initialCommit);
            writeObject(commitFile, initialCommit);
            writeObject(treeFile, commits);
        } else {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }
    }
    public String getId(File f) {
        return sha1(readContents(f));
    }
    public String getId(Commit c) {
        return sha1(serialize(c));
    }
    // the add command
    public static void add(String filename) {
        /**
         * if current version == version in commit:
         *     if already in stagingAdd:
         *         delete
         * else:
         *     map[filename] = sha1 id
         *if file in stagingRm:
         *    rm it
         *  */

        // how to find a certain file?
        // should I pass in file or filename?
        /** ans: pass in filename, and file must be in cwd because otherwise
         * can't find a certain file(which means cwd changes)*/
        File f = join(CWD, filename);
        // how to represent current commit-> kinda fixed
        // should repo be instantiated?
        if (sha1(f) == sha1(commits.get(head))) {
            if (add.containsKey(filename)) {
                add.remove(filename);
            }
        } else {
            add.put(filename, sha1(file));
        }
        if (remove.containsKey(filename)) {
            remove.remove(filename);
        }

    }
    /** this is probably the commit command and since I'm still working on
    init, probably should add first commit in a easier way.*/
    public void addCommit() {

    }
}

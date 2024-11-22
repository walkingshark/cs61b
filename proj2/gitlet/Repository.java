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
    // a folder, contains several blobs, filename is blob's sha1 id
    public static final File BLOBS = join(GITLET_DIR, "blobs");

    // staging area(2)
    public static HashMap<String, String> initial = new HashMap<>();
    // a file, contains the "add" map
    public static File ADD = join(GITLET_DIR, "add");
    public static HashMap<String, String> add = new HashMap<>();
    // a file, contains the "remove" map
    public static  File REMOVE = join(GITLET_DIR, "remove");
    public static HashMap<String, String> remove = new HashMap<>();
    // files, contains a sha id
    public static final File HEAD = join(GITLET_DIR, "head");
    public static final File MASTER = join(GITLET_DIR, "master");

    // folder, contains a file for "commit" map, a file for each commit(name: id),
    public static final File COMMIT = join(GITLET_DIR, "commit");
    public static File commitMap = join(COMMIT, "commitMap");
    /* TODO: fill in the rest of this class. */
    // init method in Main calls this constructor to get a new repo.
    // What's a repo?
    // make cwd a gitlet repo
    // how to properly represent date?
    public static void init() {
        //set up a new repo(copy from lab6)
        if (!GITLET_DIR.exists()) {
            GITLET_DIR.mkdir();
            BLOBS.mkdir();

            initial.put("a", "b");
            add = initial;
            remove = initial;

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
    public static String getId(File f) {
        return sha1(readContents(f));
    }
    public static String getId(Commit c) {
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
        // should repo be instantiated? ans: no
        if (sha1(f) == sha1(commits.get(head))) {
            if (add.containsKey(filename)) {
                add.remove(filename);
            }
        } else {
            add.put(filename, sha1(f));
        }
        if (remove.containsKey(filename)) {
            remove.remove(filename);
        }

    }
    public static void commit(String message) {
        Commit newCommit = new Commit(message);
        for (String filename : add.keySet()) {
            newCommit.version.put(filename, add.get(filename));
        }
        for (String filename : remove.keySet()) {
            newCommit.version.remove(filename);
        }
        // clear staging area
        add.clear();
        remove.clear();
        // add commit to commit tree
        head = sha1(newCommit);
        commits.put(head, newCommit);
        // store stuff
        writeObject(HEAD, head);
        writeObject(MASTER, master);
        writeObject(commitMap, commits);
        writeObject(ADD, add);
        writeObject(REMOVE, remove);
    }
    public static void rm(String filename) {
        /**
         * if file is staged for addition:
         *  unstage
         *  if file is tracked in current commit:
         *      stage the file for removal and delete file in the cwd
         * */
        // how to check if sth already read or not?
        // maybe initialize sth
        if (add.equals(initial)) {
            // read sth
            readObject(ADD, HashMap.class);
        }
        if (commits.get(head).version.containsKey(filename)) {
            remove.put(filename, add.get(filename));
        }
        if (add.containsKey(filename)) {
            add.remove(filename);
        }

    }
}

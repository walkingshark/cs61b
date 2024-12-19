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
    /**Maintain a runtime map between these strings and the runtime objects they refer to.
     * You create and fill in this map while Gitlet is running, but never read or write it to a file.
     * */
    //finding a certain commit can be done by using "join", after deserliazing, put it in a runtime map
    //sha id-->commit

    public static HashMap<String, String> branches = new HashMap<>();
    public static String head;
    //public static String master; // combine with branches

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    // a folder, contains several blobs, filename is blob's sha1 id
    public static final File BLOBS = join(GITLET_DIR, "blobs");

    // staging area(2)
    // a file, contains the "add" map
    public static File ADD = join(GITLET_DIR, "add");
    public static HashMap<String, String> add = new HashMap<>();
    // a file, contains the "remove" map
    public static  File REMOVE = join(GITLET_DIR, "remove");
    public static HashMap<String, String> remove = new HashMap<>();
    // files, contains a sha id
    public static final File HEAD = join(GITLET_DIR, "head");
    //public static final File MASTER = join(GITLET_DIR, "master");
    public static final File BRANCHES = join(GITLET_DIR, "branches");
    // folder, contains a file for each commit(name: id),
    public static final File COMMIT = join(GITLET_DIR, "commit");
    // how to properly represent date?
    public static void init() {
        //set up a new repo(copy from lab6)
        if (!GITLET_DIR.exists()) {
            GITLET_DIR.mkdir();
            BLOBS.mkdir();

            Commit initialCommit = new Commit("initial commit");
            String initialId = getId(initialCommit);
            branches.put("master", initialId);
            head = "master";
            commits.put(initialId, initialCommit);
            File commitFile = join(GITLET_DIR, initialId);
            writeObject(commitFile, initialCommit);
            writeObject(HEAD, head);
            writeObject(BRANCHES, branches);
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
        File f = join(CWD, filename);
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
    // persistence(load) head or master
    private static String loadString(String s) {
        if (s.isEmpty()) {
            File f = join(COMMIT, s);
            s = readObject(f, String.class);
        }
        return s;
    }
    // persistence(load) for commit
    private static Commit getCommit(String s) {
        if (commits.containsKey(s)) {
            return commits.get(s);
        } else {
            File f = join(COMMIT, s);
            Commit c = readObject(f, Commit.class);
            commits.put(s, c);
            return c;
        }
    }
    public static void commit(String message) {
        head = loadString("head");
        Commit newCommit = new Commit(message, head, getCommit(head));// already copy its parent in the constructor

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
        writeObject(ADD, add);
        writeObject(REMOVE, remove);
    }
    // persistence(load) for add

    /** if a map is empty
     * 1 haven't read stuff --> read from file
     * 2 just got cleared, should already wrote to file, so the file is empty
     * --> read file*/
    private static void getAdd() {
        if (add.isEmpty()) {
            readObject(ADD, HashMap.class);
        }
    }
    // persistence(load) for remove
    private static void getRemove() {
        if (remove.isEmpty()) {
            readObject(REMOVE, HashMap.class);
        }
    }
    public static void rm(String filename) {

        // how to check if sth already read or not?
        // maybe initialize sth
        getAdd();
        getRemove();
        if (commits.get(head).version.containsKey(filename)) {
            remove.put(filename, add.get(filename));
        }
        if (add.containsKey(filename)) {
            add.remove(filename);
        }
        //store stuff
        writeObject(ADD, add);
        writeObject(REMOVE, remove);

    }
    public static void log() {

         //read stuff
         loadString("head");

         Commit currentCommit = getCommit(head);
         while (!currentCommit.message.equals("initial commit")) {
             // print a commit
             currentCommit = getCommit(currentCommit.parent);
         }
         //print initial commit
         //store stuff(didn't change anything so no need)

    }
    public static void global_log() {


    }
    public static void find(String message) {
        /** Prints out the ids of all commits that have the given commit message, one per line.
         *  Hint: the hint for this command is the same as the one for global-log.
         * */
    }
    public static void status() {
        /** display branches, the current branches is added a *.
         * display files that are staged for addition, removed(in dictionary order)
         * Ignore any subdirectories that may have been introduced, since Gitlet does not deal with them.(?)
         * */
    }
    public static void checkout() {
        /** has three versions of it.
         * 1. java gitlet.Main checkout -- [file name]
         * update the file in cdw from the head commit(this changed is not staged)
         * 2. java gitlet.Main checkout [commit id] -- [file name]
         * similar to 1., but find commit with given id
         * 3. java gitlet.Main checkout [branch name]
         * update cdw with latest commit of given branch, files that aren't in branch->delete
         * clear staged area(with some conditions)
         * ! need to implement connvient search for commit ids(abbreviated id)
         * */
    }
    public static void branch(String branch_name) {
        /** add a new pointer to head commit*/
    }
    public static void rm_branch(String branch_name) {
        /** remove a branch pointer*/
    }
    public static void reset(String id) {
        /** The command is essentially checkout of an arbitrary commit that also changes the current branch head.*/
    }
    public static void merge(String branch_name) {
        /** */
    }



}

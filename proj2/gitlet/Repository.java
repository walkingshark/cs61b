package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public static TreeMap<String, Commit> commits = new TreeMap<>();
    /**Maintain a runtime map between these strings and the runtime objects they refer to.
     * You create and fill in this map while Gitlet is running, but never read or write it to a file.
     * */
    //finding a certain commit can be done by using "join", after deserliazing, put it in a runtime map
    //sha id-->commit

    public static TreeMap<String, String> branches = new TreeMap<>();
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
    public static File HEAD = join(GITLET_DIR, "head");
    //public static final File MASTER = join(GITLET_DIR, "master");
    public static File BRANCHES = join(GITLET_DIR, "branches");
    // folder, contains a file for each commit(name: id),
    public static final File COMMIT = join(GITLET_DIR, "commit");
    // how to properly represent date?
    public static void init() {
        //set up a new repo(copy from lab6)
        if (!GITLET_DIR.exists()) {
            GITLET_DIR.mkdir();
            BLOBS.mkdir();
            COMMIT.mkdir();
            try {
                ADD.createNewFile();
                REMOVE.createNewFile();
                HEAD.createNewFile();
                BRANCHES.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Commit initialCommit = new Commit("initial commit");
            String initialId = getId(initialCommit);
            branches.put("master", initialId);
            head = "master";
            commits.put(initialId, initialCommit);
            File commitFile = join(COMMIT, initialId);
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
        List<String> file_names = plainFilenamesIn(CWD);
        if (!file_names.contains(filename)) {
            System.out.println("File does not exist.");
            System.exit(0);
        }
        getHead();
        getAdd();
        getRemove();
        getBranches();
        String fileID = getId(join(CWD, filename));
        String head_id = branches.get(head);
        TreeMap<String, String> head_commit_version = getCommit(head_id).version;
        if (head_commit_version.containsKey(filename) && fileID.equals(head_commit_version.get(filename))) { // fails
            if (add.containsKey(filename)) {
                add.remove(filename);
            }
        } else if (!fileID.equals(add.get(filename))){ // a file has changed since last add
            add.put(filename, fileID);
            // create blob
            Blob newBlob = new Blob(filename);
        }
        if (remove.containsKey(filename)) {
            remove.remove(filename);
        }
        writeObject(ADD, add);
        writeObject(REMOVE, remove);

    }
    // persistence(load) head or
    private static void getHead() {
        if (head == null) {
            head = readObject(HEAD, String.class);
        }

    }
    private static void getBranches() {
        if (branches.isEmpty() && BRANCHES.length() != 0) {
            branches = (TreeMap<String, String>) readObject(BRANCHES, TreeMap.class);
        }
    }
    // persistence(load) for commit
    private static Commit getCommit(String s) {
        if (commits.containsKey(s)) { // s shouldn't be null
            return commits.get(s);
        } else {
            File f = join(COMMIT, s);
            Commit c = readObject(f, Commit.class);
            // notice that "commits" is a runtime map
            commits.put(s, c);
            return c;
        }
    }
    public static void commit(String message) {
        getHead();
        getBranches();
        getAdd();
        getRemove();
        if (add.isEmpty()) {
            System.out.println("No changes added to the commit.");
            return; // abort
        }
        if (message.isEmpty()) {
            System.out.println("Please enter a commit message.");
            return;
        }
        String headID = branches.get(head);
        Commit newCommit = new Commit(message, headID, getCommit(headID));// already copy its parent in the constructor
        // update so that now the new commit differs its parent
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
        String newCommitID = getId(newCommit);
        branches.put(head, newCommitID);
        commits.put(newCommitID, newCommit);
        // store stuff
        writeObject(HEAD, head);
        writeObject(BRANCHES, branches);
        writeObject(ADD, add);
        writeObject(REMOVE, remove);
        writeObject(join(COMMIT, newCommitID), newCommit);
    }
    // persistence(load) for add

    /** if a map is empty
     * 1 haven't read stuff --> read from file
     * 2 just got cleared, should already wrote to file, so the file is empty
     * --> read file*/
    private static void getAdd() {
        if (add.isEmpty() && ADD.length() != 0) {
            add = readObject(ADD, HashMap.class);
        }
    }
    // persistence(load) for remove
    private static void getRemove() {
        if (remove.isEmpty() && REMOVE.length() != 0) {
            remove = readObject(REMOVE, HashMap.class);
        }
    }
    public static void rm(String filename) {

        // how to check if sth already read or not?
        // maybe initialize sth
        getAdd();
        getRemove();
        getHead();
        Boolean staged = add.containsKey(filename);
        Boolean tracked = getCommit(branches.get(head)).version.containsKey(filename);
        if (!(tracked || staged)) {
            System.out.println("No reason to remove the file.");
        }
        if (tracked) {
            remove.put(filename, add.get(filename));
        }
        if (staged) {
            add.remove(filename);
        }
        //store stuff
        writeObject(ADD, add);
        writeObject(REMOVE, remove);

    }
    public static void print_commit(String commitID) {
        Commit currentCommit = getCommit(commitID);
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.ENGLISH);
        String formattedDate = formatter.format(currentCommit.time);
        //String s = String.format("Date: " + "%1$ta %1$tb %1$te %1$tT %1$tY %1$tz", currentCommit.time);// the date is in chinese, might need to fix
        System.out.println("===");
        System.out.println("commit " + commitID);
        if (!(currentCommit.parent2 == null)) {
            System.out.println("Merge: " + currentCommit.parent.substring(0, 7) + currentCommit.parent2.substring(0, 7));
        }
        System.out.println("Date: " + formattedDate);
        System.out.println(currentCommit.message);
        System.out.println();
    }
    public static void log() {
         //read stuff
         getHead();
         getBranches();
         String currentCommitID = branches.get(head);
         Commit currentCommit = getCommit(currentCommitID);
         while (!currentCommit.message.equals("initial commit")) {
             // print a commit
             print_commit(currentCommitID);
             currentCommitID = currentCommit.parent;
             currentCommit = getCommit(currentCommitID);

         }
         //print initial commit
         print_commit(currentCommitID);
         //store stuff(didn't change anything so no need)

    }
    public static void global_log() {
        List<String> commit_names = plainFilenamesIn(COMMIT);
        for (String name : commit_names) {
            print_commit(name);
        }
    }
    public static void find(String commit_message) {
        /** Prints out the ids of all commits that have the given commit message, one per line.
         *  Hint: the hint for this command is the same as the one for global-log.
         * */
        Boolean found = false;
        List<String> commit_names = plainFilenamesIn(COMMIT);
        for (String currentCommitID : commit_names) {
            Commit currentCommit = getCommit(currentCommitID);
            if (currentCommit.message.equals(commit_message)) {
                System.out.println(currentCommitID);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Found no commit with that message.");
        }



    }

    public static void status() {
        /** display branches, the current branches is added a *.
         * display files that are staged for addition, removed(in dictionary order)
         * Ignore any subdirectories that may have been introduced, since Gitlet does not deal with them.(?)
         * */
        getHead();
        getAdd();
        getRemove();
        getBranches();
        System.out.println("=== Branches ===");
        System.out.println("*" + head);
        for (Map.Entry<String, String> entry : branches.entrySet()) {
            String branch = entry.getKey();
            if (branch != head) {
                //print branches in lexicographic oreder
                System.out.println(branch);
            }
        }
        System.out.println();
        for (Map.Entry<String, String> entry : add.entrySet()) {
            String file = entry.getKey();
            //print branches in lexicographic oreder
            System.out.println(file);
        }
        System.out.println();
        for (Map.Entry<String, String> entry : remove.entrySet()) {
            String file = entry.getKey();
            //print branches in lexicographic oreder
            System.out.println(file);
        }
        System.out.println();

    }
    public static void checkout1(String filename) {
    getHead();
    getBranches();
    TreeMap<String, String> file_versions = getCommit(branches.get(head)).version;
    if (file_versions.containsKey(filename)) {
        String blob_id = file_versions.get(filename);
        writeContents(join(CWD, filename), readContents(join(BLOBS, blob_id)));
    } else {
        System.out.println("File does not exist in that commit.");
    }



    }
    public static void checkout2(String commitID, String filename) {
        List<String> commit_names = plainFilenamesIn(COMMIT);
        for (String name : commit_names) { // name = commit id
            if (name.substring(0, 6).equals(commitID.substring(0, 6))) {
                TreeMap<String, String> file_versions = getCommit(name).version;
                if (file_versions.containsKey(filename)) {
                    String blob_id = file_versions.get(filename);
                    writeContents(join(CWD, filename), readContents(join(BLOBS, blob_id)));
                } else {
                    System.out.println("File does not exist in that commit.");
                }
                return;
            }
        }
        System.out.println("No commit with that id exists.");

    }
    public static void checkout3(String branch_name) {
        getBranches();
        getHead();
        if (!branches.containsKey(branch_name)) {
            System.out.println("No such branch exists.");
            return;
        }
        if (head.equals(branch_name)) {
            System.out.println("No need to checkout the current branch.");
            return;
        }
        TreeMap<String, String> file_versions = getCommit(branches.get(branch_name)).version;
        TreeMap<String, String> head_file_versions = getCommit(branches.get(head)).version;
        List<String> cwd_file_names = plainFilenamesIn(CWD);
        for (String filename : cwd_file_names) {
            if ((!head_file_versions.containsKey(filename)) && (!file_versions.containsKey(filename))) {
                System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                System.exit(0);
            }
        }
        for (Map.Entry<String, String> entry : file_versions.entrySet()) {
            String filename = entry.getKey();
            String blob_id = entry.getValue();
            writeContents(join(CWD, filename), readContents(join(BLOBS, blob_id)));
        }
        for (Map.Entry<String, String> entry : head_file_versions.entrySet()) {
            String filename = entry.getKey();
            String blob_id = entry.getValue();
            if (!file_versions.containsKey(filename)) {
                restrictedDelete(filename);
            }
        }
        head = branch_name;
        writeObject(HEAD, head);
    }
    public static void branch(String branch_name) {
        /** add a new pointer to head commit*/
        getBranches();
        getHead();
        if (branches.containsKey(branch_name)) {
            System.out.println("A branch with that name already exists.");
        } else {
            branches.put(branch_name, branches.get(head));
            writeObject(BRANCHES, branches);
        }
    }
    public static void rm_branch(String branch_name) {
        /** remove a branch pointer*/
        getBranches();
        getHead();
        if (!branches.containsKey(branch_name)) {
            System.out.println("A branch with that name does not exist.");
        } else if (head.equals(branch_name)) {
            System.out.println("Cannot remove the current branch.");
        } else {
            branches.remove(branch_name);
            writeObject(BRANCHES, branches);
        }
    }
    public static void reset(String id) {
        /** The command is essentially checkout of an arbitrary commit that also changes the current branch head.*/
        /** Note that in Gitlet, there is no way to be in a detached head state since there is no checkout command that will move the HEAD pointer to a specific commit.
         *  The reset command will do that, though it also moves the branch pointer.
         *  Thus, in Gitlet, you will never be in a detached HEAD state.*/
        // getHead(); checkout2 already do so
        // getBranches();
        getAdd();
        TreeMap<String, String> tracked_files = getCommit(id).version;
        List<String> commit_names = plainFilenamesIn(COMMIT);
        if (!commit_names.contains(id)) {
            System.out.println("No commit with that id exists.");
            return;
        }
        for (Map.Entry<String, String> entry : tracked_files.entrySet()) {
            String filename = entry.getKey();
            checkout2(id, filename);
        }
        branches.put(head, id);
        add.clear();
        writeObject(ADD, add);
        writeObject(BRANCHES, branches);
    }
    public static void merge(String branch_name) {
        /** */
    }



}

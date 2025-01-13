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
    public static TreeMap<String, String> add = new TreeMap<>();
    // a file, contains the "remove" map
    public static  File REMOVE = join(GITLET_DIR, "remove");
    public static TreeMap<String, String> remove= new TreeMap<>();
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
        if (add.isEmpty() && remove.isEmpty()) {
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
            add = readObject(ADD, TreeMap.class);
        }
    }
    // persistence(load) for remove
    private static void getRemove() {
        if (remove.isEmpty() && REMOVE.length() != 0) {
            remove = readObject(REMOVE, TreeMap.class);
        }
    }
    public static void rm(String filename) {

        // how to check if sth already read or not?
        // maybe initialize sth
        getAdd();
        getRemove();
        getHead();
        getBranches();
        Boolean staged = add.containsKey(filename);
        Boolean tracked = getCommit(branches.get(head)).version.containsKey(filename);
        if (!(tracked || staged)) {
            System.out.println("No reason to remove the file.");
        }
        if (tracked) {
            remove.put(filename, add.get(filename));
            restrictedDelete(filename);
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
            System.out.println("Merge: " + currentCommit.parent.substring(0, 6) + currentCommit.parent2.substring(0, 6));
        }
        System.out.println("Date: " + formattedDate);
        System.out.println(currentCommit.message);
        System.out.println();
    }
    private static List<String> getCommitChain(String currentCommitID) {
        List<String> chain = new ArrayList<>();
        Commit currentCommit = getCommit(currentCommitID);
        while (!currentCommit.message.equals("initial commit")) {
            chain.add(currentCommitID);
            currentCommitID = currentCommit.parent;
            currentCommit = getCommit(currentCommitID);
        }
        chain.add(currentCommitID);
        return chain;
    }
    public static void log() {
         //read stuff
         getHead();
         getBranches();
         String currentCommitID = branches.get(head);
         List<String> commitChain = getCommitChain(currentCommitID);
         for (String commit_id : commitChain) {
             print_commit(commit_id);
         }
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
            if (!branch.equals(head)) {
                //print branches in lexicographic oreder
                System.out.println(branch);
            }
        }
        System.out.println();
        System.out.println("=== Staged Files ===");
        for (Map.Entry<String, String> entry : add.entrySet()) {
            String file = entry.getKey();
            //print branches in lexicographic oreder
            System.out.println(file);
        }
        System.out.println();
        System.out.println("=== Removed Files ===");
        for (Map.Entry<String, String> entry : remove.entrySet()) {
            String file = entry.getKey();
            //print branches in lexicographic oreder
            System.out.println(file);
        }
        System.out.println();
        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();
        System.out.println("=== Untracked Files ===");
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
    private static String original_id(String short_id) {
        List<String> commit_names = plainFilenamesIn(COMMIT);
        int l = short_id.length();
        for (String name : commit_names) { // name = commit id
            if (name.substring(0, l).equals(short_id.substring(0, l))) {
                return name; // get back the orginal length id
            }
        }
        return "error";
    }
    public static void checkout2(String commitID, String filename) {
        if (commitID.length() < 40) {
            commitID = original_id(commitID);
        }
        if (commitID.equals("error") || (!plainFilenamesIn(COMMIT).contains(commitID))) {
            System.out.println("No commit with that id exists.");
            return;
        }
        TreeMap<String, String> file_versions = getCommit(commitID).version;
        if (file_versions.containsKey(filename)) { // a file is present in current commit
            String blob_id = file_versions.get(filename);
            writeContents(join(CWD, filename), readContents(join(BLOBS, blob_id))); // write cwd files according to  commit
        } else {
            System.out.println("File does not exist in that commit.");
        }

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
        List<String> files = plainFilenamesIn(CWD);
        for (String filename : files) {
            // if a file is untracked in current branch and would be overwritten
            if (!istracked(filename) && file_versions.containsKey(filename)) {
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
    private static boolean istracked(String filename) {
        getAdd();
        getHead();
        getBranches();
        TreeMap<String, String> head_file_version = getCommit(branches.get(head)).version;
        if (head_file_version.containsKey(filename) || add.containsKey(filename)) {
            return true;
        } else {
            return false;
        }
    }
    public static void reset(String id) {
        /** The command is essentially checkout of an arbitrary commit that also changes the current branch head.*/
        /** Note that in Gitlet, there is no way to be in a detached head state since there is no checkout command that will move the HEAD pointer to a specific commit.
         *  The reset command will do that, though it also moves the branch pointer.
         *  Thus, in Gitlet, you will never be in a detached HEAD state.*/
        getAdd();
        getBranches();
        getHead();
        getRemove();

        List<String> commit_names = plainFilenamesIn(COMMIT);
        if (!commit_names.contains(id)) {
            System.out.println("No commit with that id exists.");
            return;
        }
        TreeMap<String, String> this_commit_version = getCommit(id).version;
        TreeMap<String, String> head_file_versions = getCommit(branches.get(head)).version;
        List<String> files = plainFilenamesIn(CWD);
        for (String filename : files) {
            // if a file is untracked in current branch and would be overwritten
            if (!istracked(filename) && this_commit_version.containsKey(filename)) {
                System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                return;
            }
        }
        for (Map.Entry<String, String> entry : this_commit_version.entrySet()) {
            String filename = entry.getKey();
            checkout2(id, filename);
        }
        //remove tracked files that are not present in that commit
        for (Map.Entry<String, String> entry : head_file_versions.entrySet()) {
            String filename = entry.getKey();
            if (!this_commit_version.containsKey(filename)) {
                restrictedDelete(filename);
            }
        }
        branches.put(head, id);
        add.clear();
        remove.clear();
        writeObject(REMOVE, remove);
        writeObject(ADD, add);
        writeObject(BRANCHES, branches);
    }
    private static boolean ismodified(String commit1, String commit2, String filename) {
        // check if a file is modified since split point
        // ! file must exist in split point and branch(content1 and content2)
        String content1 = getCommit(commit1).version.get(filename);
        String content2 = getCommit(commit2).version.get(filename);
        if (content1.equals(content2)) { // has a bug
            return false;
        } else {
            return true;
        }
    }
    private static boolean isabscent(String branch_id, String filename) {
        getAdd();
        TreeMap<String, String> branch_file_version = getCommit(branch_id).version;
        if (branch_file_version.containsKey(filename) || add.containsKey(filename)) {
            return false;
        } else {
            return true;
        }
    }
    public static void merge(String branch_name) {
        /** */
        String split_point = "";
        getBranches();
        getHead();
        if (!add.isEmpty() && !remove.isEmpty()) {
            System.out.println("You have uncommitted changes.");
            System.exit(0);
        }
        if (!branches.containsKey(branch_name)) {
            System.out.println("A branch with that name does not exist.");
            return;
        }
        if (head.equals(branch_name)) {
            System.out.println("Cannot merge a branch with itself.");
            return;
        }
        String head_id = branches.get(head);
        String branch_id = branches.get(branch_name);
        List<String> headChain = getCommitChain(head_id); // from latest to initial commit
        List<String> branchChain = getCommitChain(branch_id);
        // find split point
        for (String commit_id : headChain) {
            if (branchChain.contains(commit_id)) {
                split_point = commit_id;
                break;
            }
        }
        TreeMap<String, String> head_version = getCommit(head_id).version;
        TreeMap<String, String> branch_version = getCommit(branch_id).version;
        TreeMap<String, String> split_version = getCommit(split_point).version;
        if (split_point.equals(head_id)) {
            checkout3(branch_name);
            System.out.println("Current branch fast-forwarded.");
            return;
        } else if (split_point.equals(branch_id)) {
            System.out.println("Given branch is an ancestor of the current branch.");
            return;
        } else {
            Set<String> file_names = new TreeSet<>();
            file_names.addAll(head_version.keySet());
            file_names.addAll(branch_version.keySet());
            file_names.addAll(split_version.keySet());
            boolean conflict = false;
            for (String filename : file_names) {
                 boolean in_split = split_version.containsKey(filename);
                 boolean in_head = head_version.containsKey(filename);
                 boolean in_branch = branch_version.containsKey(filename);
                 if (in_split && !ismodified(split_point, branch_id, filename) && isabscent(head_id, filename)){
                    // do nothing
                 } else if (!in_split && !in_branch && in_head) {
                 // do nothing
                 } else if (!in_split && in_branch && !in_head) {
                     // checkout and staged(what type of checkout?)
                     checkout2(branch_id, filename);
                     add(filename);
                 } else if ((!in_head && !in_branch) || (in_head && in_branch && !ismodified(branch_id, head_id, filename))) {
                     // do nothing
                     //modified in the same way
                 } else if (in_split && in_head && in_branch && ismodified(split_point, head_id, filename) && !ismodified(split_point, branch_id, filename)) {
                 // modified in head but not other, file not exist in split point and branch and head
                 } else if (in_split && !ismodified(split_point, head_id, filename) && isabscent(branch_id, filename)) {
                    if (!istracked(filename)) {
                        System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                        System.exit(0);
                    }
                    restrictedDelete(filename);
                    head_version.remove(filename);
                 } else if (in_split && in_head && in_branch && !ismodified(split_point, head_id, filename) && ismodified(split_point, branch_id, filename)) {
                     // modified in other but not head, file not exist in split point and branch and head
                     checkout2(branch_id, filename);
                     add(filename);
                 } else {
                 // conflicted, overwrite the file with two versions
                     conflict = true;
                     if (!istracked(filename)) {
                         System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                         System.exit(0);
                     }
                     String file_in_head = "";
                     String file_in_branch = "";
                     if (in_head) {
                         file_in_head = readContentsAsString(join(BLOBS, head_version.get(filename)));
                     }
                     if (in_branch) {
                         file_in_branch = readContentsAsString(join(BLOBS, branch_version.get(filename)));
                     }
                     //String debug = "<<<<<<< HEAD\n"+file_in_head+
                             //"=======\n"+file_in_branch+">>>>>>>";
                     writeContents(join(CWD, filename), "<<<<<<< HEAD\n"+file_in_head+
                             "=======\n"+file_in_branch+">>>>>>>");
                     //System.out.println(debug);
                 }
            }
            if (conflict) {
                System.out.println("Encountered a merge conflict.");
            } else {
                commit("Merged " + branch_name + " into " + head + ".");
                getCommit(branches.get(head)).parent2 = branch_name;
            }

        }

    }



}

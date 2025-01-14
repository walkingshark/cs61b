package gitlet;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

//import static gitlet.Repository.head;

/** Represents a gitlet commit object.
 *
 *  does at a high level.
 *
 *  @author Mark
 */
public class Commit implements Serializable {
    /**
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    public String message;
    public Date time;
    // filenames to blob's id
    public TreeMap<String, String> version = new TreeMap<>();
    public String parent;
    public String parent2;
    /* TODO: fill in the rest of this class. */

    public Commit(String message, String head, Commit parentCommit) {
        this.time = new Date();
        this.message = message;
        this.parent = head;
        this.version = parentCommit.version;
    }
    public Commit(String message) {
        this.message = message;
        this.time = new Date(0);
    }

}

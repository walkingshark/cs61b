package gitlet;

// TODO: any imports you need here

import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashMap;

import static gitlet.Repository.head;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    private Date time;
    // filenames to blob's id
    public HashMap<String, String> version = new HashMap<>();
    // parent's sha1 id, probably need a map from sha1 to commit
    public String parent;
    public String parent2;
    /* TODO: fill in the rest of this class. */

    public Commit(String message) {
        this.message = message;
        if (message.equals("initial commit")) {
            this.time = new Date(0);
        } else {
            this.time = new Date();
            this.parent = head;
            this.version = Repository.commits.get(parent).version;
        }
    }

}

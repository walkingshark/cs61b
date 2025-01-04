package gitlet;

import java.io.File;
import java.io.IOException;


import static gitlet.Utils.join;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) throws IOException {
        // TODO: what if args is empty?
        File CWD = new File(System.getProperty("user.dir"));
        /** The .gitlet directory. */
        File GITLET_DIR = join(CWD, ".gitlet");
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                Repository.init();
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                if (GITLET_DIR.exists()) {
                    Repository.add(args[1]);
                }
                break;
            case "commit":
                if (GITLET_DIR.exists()) {
                    Repository.commit(args[1]);
                }
                break;
            case "rm":
                if (GITLET_DIR.exists()) {
                    Repository.rm(args[1]);
                }
                break;
            case "log":
                if (GITLET_DIR.exists()) {
                    Repository.log();
                }
                break;
            case "checkout":
                if (GITLET_DIR.exists()) {
                    if (args.length == 3) {
                        Repository.checkout1(args[2]);
                    } else if (args.length == 4) {
                        Repository.checkout2(args[1], args[3]);
                    } else {
                        Repository.checkout3(args[1]);
                    }
                }
                break;
        }
    }
    // create a repo and make a commit
    public static void init() {

    }
}

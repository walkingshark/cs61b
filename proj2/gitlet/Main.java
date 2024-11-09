package gitlet;

import java.io.File;


import static gitlet.Utils.join;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        File CWD = new File(System.getProperty("user.dir"));
        /** The .gitlet directory. */
        File GITLET_DIR = join(CWD, ".gitlet");
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                Repository thisRepo = new Repository();
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                if (GITLET_DIR.exists()) {
                    thisRepo.add(args[1]);
                }
                break;
            // TODO: FILL THE REST IN
        }
    }
    // create a repo and make a commit
    public static void init() {

    }
}

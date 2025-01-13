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
    public static void main(String[] args) { // need to handle
        // TODO: what if args is empty?
        File CWD = new File(System.getProperty("user.dir"));
        /** The .gitlet directory. */
        File GITLET_DIR = join(CWD, ".gitlet");
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        if ((!GITLET_DIR.exists()) && (!args[0].equals("init"))) {
            System.out.println("Not in an initialized Gitlet directory.");
        } else {
            String firstArg = args[0];
            switch(firstArg) {
                case "init":
                    // TODO: handle the `init` command
                    check_operands(args, 1);
                    Repository.init();
                    break;
                case "add":
                    // TODO: handle the `add [filename]` command
                    check_operands(args, 2);
                    Repository.add(args[1]);
                    break;
                case "commit":
                    check_operands(args, 2);
                    Repository.commit(args[1]);
                    break;
                case "rm":
                    check_operands(args, 2);
                    Repository.rm(args[1]);
                    break;
                case "log":
                    check_operands(args, 1);
                    Repository.log();
                    break;
                case "checkout":
                    if (args.length == 3 && args[1].equals("--")) {
                        Repository.checkout1(args[2]);
                    } else if (args.length == 4 && args[2].equals("--")) {
                        Repository.checkout2(args[1], args[3]);
                    } else if (args.length == 2){
                        Repository.checkout3(args[1]);
                    } else {
                        System.out.println("Incorrect operands.");
                        System.exit(0);
                    }

                    break;
                case "global-log":
                    check_operands(args, 1);
                    Repository.global_log();

                    break;
                case "find":
                    check_operands(args, 2);
                    Repository.find(args[1]);

                    break;
                case "status":
                    check_operands(args, 1);
                    Repository.status();

                    break;
                case "branch":
                    check_operands(args, 2);
                    Repository.branch(args[1]);

                    break;
                case "rm-branch":
                    check_operands(args, 2);
                    Repository.rm_branch(args[1]);

                    break;
                case "reset":
                    check_operands(args, 2);
                    Repository.reset(args[1]);

                    break;
                case "merge":
                    check_operands(args, 2);
                    Repository.merge(args[1]);
                    break;
                default:
                    System.out.println("No command with that name exists.");
                    System.exit(0);
            }
        }
    }
    public static void check_operands(String[] args, int n) {
        if (args.length != n) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
    }
}

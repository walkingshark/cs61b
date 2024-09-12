package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        for (int n = 0; n < 8; n++) {
            int N = (int) Math.pow(2, n) * 1000;
            if (n == 7) {N *= 100;}
            Ns.addLast(N);
            AList<Integer> a = new AList<>();
            Stopwatch sw = new Stopwatch();
            for (int i = 1; i <= N; i++){
                a.addLast(i);
            }
            times.addLast(sw.elapsedTime());
        }
        printTimingTable(Ns, times, Ns);

    }
}

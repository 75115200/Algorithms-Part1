import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();

        int k = Integer.parseInt(args[0]);
        if (k == 0) {
            return;
        }
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String item = StdIn.readString();
            if (i < k) {
                randomizedQueue.enqueue(item);
            } else if (StdRandom.uniform(i + 1) < k) {
                randomizedQueue.dequeue();
                randomizedQueue.enqueue(item);
            }
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(randomizedQueue.dequeue());
        }
    }
}

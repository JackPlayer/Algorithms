import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> randQueue = new RandomizedQueue<>();

        int permutations = Integer.parseInt(args[0]);

        while(!StdIn.isEmpty()) {
            String val = StdIn.readString();
            randQueue.enqueue(val);
        }

        Iterator<String> iterator = randQueue.iterator();

        for (int i = 0; i < permutations; i++) {
            System.out.println(iterator.next());
        }
    }
}

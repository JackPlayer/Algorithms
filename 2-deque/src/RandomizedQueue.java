
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

@SuppressWarnings("unchecked")

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int size;
    private final int head;
    private int capacity;

    public RandomizedQueue() {

        capacity = 1;
        queue = (Item[]) new Object[capacity];
        head = 0;
        size = 0;
    }

    /**
     * Checks if the structure is empty
     * @return true if it is empty
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Gets the size of the structure
     * @return the size
     */
    public int size() {
        return size;
    }

    /**
     * Places an item in the queue
     * @param item The item to insert into the queue
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (size >= capacity / 2) {
            capacity *= 2;
            resize(capacity);
        }
        queue[size] = item;
        size++;
    }

    /**
     * Randomly removes an item from the structure
     * @return The randomly removed item
     */
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("No elements to sample");
        }

        if (size <= capacity / 4) {
            capacity /= 4;
            resize(capacity);
        }

        int randIndex = StdRandom.uniform(head, size);

        Item randItem = queue[randIndex];
        queue[randIndex] = null;

        shiftQueueLeft(randIndex);
        size --;
        return randItem;
    }

    /**
     * Gets a random item from the structure
     * @return The random item
     */
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException("No elements to sample");
        }
        int randIndex = StdRandom.uniform(head, size);

        return queue[randIndex];
    }

    /**
     * Iterator for the
     * @return The random iterator
     */
    public Iterator<Item> iterator() {
        return new RandArrIterator();
    }

    private class RandArrIterator implements Iterator<Item> {

        private final Item[] randQueue;
        private int curr;
        public RandArrIterator() {
            randQueue = Arrays.copyOf(queue, size);
            curr = 0;
            StdRandom.shuffle(randQueue);
        }

        public boolean hasNext() {
            return curr < randQueue.length;
        }


        public Item next() {
            Item returnItem = randQueue[curr];
            curr++;
            return returnItem;
        }
    }

    /**
     * Resizes the queue
     * @param newSize The new size of the queue
     */
    private void resize (int newSize) {
        Item[] newArr = (Item[]) new Object[newSize];

        for (int i = 0; i < size; i++) {
            newArr[i] = queue[i];
            queue[i] = null;
        }
        queue = newArr;
    }

    /**
     * Shifts the queue 1 element left, starting at the startIndex
     * @param startIndex The index to start the shift from (Should be a null element that was removed)
     */
    private void shiftQueueLeft(int startIndex) {
        // Loop starting at the random index for the whole queue length.
        for (int i = startIndex; i < queue.length; i++) {

            // If next element is not out of bounds
            if (i + 1 < queue.length) {

                // Set current element to next element
                queue[i] = queue[i+1];

                // If next element is null, then break
                if (queue[i + 1] == null) {
                    break;
                }
            }
        }
    }

    private void printVariables() {
        StdOut.print("\tSize: " + size);
        StdOut.print(", Capacity: " + capacity);
        StdOut.print(", Array: ");
        for (Item i: queue) {
            StdOut.print(i + " ");
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        String[] strList = {"One", "Two", "Three", "Four", "Five", "Six"};
        Integer[] intList = {22, 18, 70, 50, 66};
        RandomizedQueue<String> qString = new RandomizedQueue<>();
        RandomizedQueue<Integer> qInt = new RandomizedQueue<>();

        StdOut.println("--RANDOMIZED QUEUE TESTING---");
        StdOut.println("\nTEST: Initial conditions");
        qString.printVariables();
        qInt.printVariables();

        StdOut.println("\nTEST: empty");
        StdOut.println("\t Empty: " + qString.isEmpty());
        StdOut.println("\t Empty: " + qInt.isEmpty());


        StdOut.println("\nTEST: Enqueue");
        qString.enqueue(strList[0]);
        qString.enqueue(strList[5]);
        qString.printVariables();

        StdOut.println("\nTEST: sample()");
        StdOut.println("\tRandom sample: " + qString.sample());

        StdOut.println("\nTEST: deque()");
        StdOut.println("\tDeque Element: " + qString.dequeue());
        qString.printVariables();

        StdOut.println("\nTEST: size");
        StdOut.println("\tSize: " + qString.size());

        StdOut.println("\nTEST: deque and enqueue");
        qInt.enqueue(intList[0]);
        qInt.enqueue(intList[1]);
        qInt.enqueue(intList[2]);
        qInt.enqueue(intList[3]);
        StdOut.println("\tDeque Element: " + qInt.dequeue());
        StdOut.println("\tDeque Element: " + qInt.dequeue());
        qInt.printVariables();

        StdOut.println("\nTEST: iterator()");
        StdOut.print("\titerator: ");
        for (Integer s: qInt) {
            StdOut.print(s + " ");
        }
    }
}

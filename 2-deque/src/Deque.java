import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;


/**
 * Deque allows the first item to be added and removed and the last item to be added and removed
 * @param <Item> Generic item to use for the deque
 */
public class Deque<Item> implements Iterable<Item> {
    /**
     * Linked list node representing the item and a reference to the next node
     */
    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private Node first;
    private Node last;
    private int size;

    public Deque() {
        size = 0;
        first = null;
    }

    /**
     * Checks if the deque is empty
     * @return true if the deque is empty
     */
    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    /**
     * Adds an item to the start of the deque
     * @param item The item to add to the start of the deque
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item to add cannot be null");
        }
        Node newNode = new Node();
        if (first != null) {
            first.prev = newNode;

        }
        newNode.item = item;
        newNode.prev = null;
        newNode.next = first;

        first = newNode;
        if (size == 0) {
            last = first;
        }

        size++;
    }

    /**
     * Adds an item ot the back of the deque
     * @param item To add to the end of the deque
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item to add cannot be null");
        }
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = null;
        newNode.prev = last;
        Node temp = last;
        if (temp != null) {
            temp.next = newNode;
        }
        last = newNode;
        if (size == 0) {
            first = last;
        }
        size++;
    }

    /**
     * Removed the first item from the deque
     * @return The item removed
     */
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("No element in the deque to remove");
        }
        if (size == 1) {
            last = null;
        }

        Node temp = first;
        first = first.next;
        if (first != null) {
            first.prev = null;
        }
        size --;
        return temp.item;
    }

    /**
     * Removes the last item from the deque
     * @return The last item
     */
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("No element in the deque to remove");
        }
        Node temp = last;
        last = last.prev;

        if (last != null) {
            last.next = null;
        }

        if (size == 1) {
            first = null;
        }
        size--;
        return temp.item;
    }

    /**
     * Iterator for deque
     * @return The deque iterator
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    /**
     * Iterator over a linked list
     */
    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        /**
         * Is there a next item in the list
         * @return true if there is a next item
         */
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Operation not implemented. Throws a OperationNotSupportedException
         */
        public void remove() {
            try {
                throw new UnsupportedOperationException("Remove is not supported for iterator");
            } catch (UnsupportedOperationException e) {
                e.printStackTrace();
            }
        }

        /**
         * Gets the current item and increments the iterator
         * @return The current item
         */
        public Item next() {
            Item item = current.item;
            if (item == null) {
                throw new NoSuchElementException("No next element in deque");
            }
            current = current.next;
            return item;
        }
    }

    /**
     * Gets the class variables
     */
    public String toString() {
        return "First: " + first.item + ", Last: " + last.item + ", Size: " + size;
    }

    public static void main(String[] args) {
        StdOut.println("TESTING");
        String[] testStrings = {"a", "b", "c", "d", "e", "f", "g"};
        int[] testInts = {1,2,3,4,5,6};

        Deque<Integer> dequeInt = new Deque<>();
        Deque<String> dequeStr = new Deque<>();

        StdOut.println("Test: isEmpty() on empty deque");
        boolean empty = dequeInt.isEmpty();
        if (empty) {
            StdOut.println("PASS");
        } else {
            StdOut.println("FAIL");
        }
        StdOut.println();

        StdOut.println("Test: isEmpty() on filled deque");
        dequeInt.addLast(testInts[0]);
        boolean notEmpty = !dequeInt.isEmpty();
        if (notEmpty) {
            StdOut.println("PASS");
        } else {
            StdOut.println("FAIL");
        }

        StdOut.println();

        StdOut.println("Test: size()");
        StdOut.println("size: " + dequeInt.size());

        StdOut.println();

        StdOut.println("Test: addFirst() addLast() removeLast() removeFirst()");
        dequeStr.addFirst(testStrings[4]);
        dequeStr.addFirst(testStrings[3]);
        dequeStr.addFirst(testStrings[2]);
        dequeStr.addFirst(testStrings[1]);
        dequeStr.addLast(testStrings[0]);
        dequeStr.addLast(testStrings[5]);
        dequeStr.addLast(testStrings[6]);
        dequeStr.addFirst(testStrings[5]);
        String lastStr = dequeStr.removeLast();
        String firstStr = dequeStr.removeFirst();

        if (lastStr.equals("g") && firstStr.equals("f")) {
            StdOut.println("PASS");
        } else {
            StdOut.println("FAIL");
        }

    }
}

import javax.naming.OperationNotSupportedException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
    }

    private Node first;
    private Node last;
    private int size;

    public Deque() {
        size = 0;
        first = null;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item to add cannot be null");
        }
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = first;
        first = newNode;
        if (size == 0) {
            last = first;
        }

        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item to add cannot be null");
        }
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = null;
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

    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("No element in the deque to remove");
        }
        Node temp = first;

        first = first.next;
        size --;
        return temp.item;
    }

    public Item removeLast() {
        return null;
    }
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            try {
                throw new OperationNotSupportedException("Remove is not supported for iterator");
            } catch (OperationNotSupportedException e) {
                e.printStackTrace();
            }
        }
        public Item next() {
            Item item = current.item;
            if (item == null) {
                throw new NoSuchElementException("No next element in deque");
            }
            current = current.next;
            return item;
        }
    }

    private void debug() {
        if (first != null) {
            System.out.println("First: " + first.item);
        }
        if (last != null) {
            System.out.println("Last: " + last.item);
        }
        System.out.println("Size: " + size);
    }

    public static void main(String[] args) {
        System.out.println("TESTING");
        String[] testStrings = {"a", "b", "c", "d", "e", "f"};
        int[] testInts = {1,2,3,4,5,6};

        Deque<Integer> dequeInt = new Deque<Integer>();
        Deque<String> dequeStr = new Deque<String>();

        System.out.println("Test: isEmpty() on empty deque");
        boolean empty = dequeInt.isEmpty();
        if (empty) {
            System.out.println("PASS");
        } else {
            System.out.println("FAIL");
        }
        System.out.println();

        System.out.println("Test: isEmpty() on filled deque");
        dequeInt.addLast(4);
        boolean notEmpty = !dequeInt.isEmpty();
        if (notEmpty) {
            System.out.println("PASS");
        } else {
            System.out.println("FAIL");
        }

        System.out.println();

        System.out.println("Test: Deque String add first and add last");
        dequeStr.addFirst(testStrings[4]);
        dequeStr.addFirst(testStrings[3]);
        dequeStr.addFirst(testStrings[2]);
        dequeStr.addFirst(testStrings[1]);
        dequeStr.addLast(testStrings[0]);
        dequeStr.addLast(testStrings[5]);
        dequeStr.addFirst(testStrings[5]);
        dequeStr.debug();
        for (String s: dequeStr) {
            System.out.print(s + " ");
        }

    }
}

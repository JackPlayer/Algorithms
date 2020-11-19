import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item content;
        Node next;
    }

    private Node first;
    private int size;

    public Deque() {
        size = 0;
        first = null;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void addFirst(Item item) {
        Node newNode = new Node();
        newNode.content = item;
        newNode.next = first;
        first = newNode;
        size++;
    }

    public void addLast(Item item) {

        size++;
    }
    public Iterator<Item> iterator() {
        return null;
    }

    public static void main(String[] args) {
        System.out.println("Test");
    }
}

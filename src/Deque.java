import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node head;
    private Node tail;
    private int size;

    private class Node {
        private Item item;
        private Node next;
        private Node pre;

        public Node(Item item) {
            super();
            this.item = item;
        }
    }

    public Deque() {
        tail = null;
        head = tail;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        checkNull(item);
        if (head == null) {
            head = new Node(item);
            tail = head;
        } else {
            Node oldHead = head;
            head = new Node(item);
            head.next = oldHead;
            oldHead.pre = head;
        }
        size += 1;
    }

    public void addLast(Item item) {
        checkNull(item);
        if (tail == null) {
            tail = new Node(item);
            head = tail;
        } else {
            Node oldTail = tail;
            tail = new Node(item);
            oldTail.next = tail;
            tail.pre = oldTail;
        }
        size += 1;
    }

    public Item removeFirst() {
        checkEmpty();
        Node oldHead = head;
        head = oldHead.next;
        if (head != null) {
            head.pre = null;
        } else {
            tail = head;
        }
        size -= 1;
        return oldHead.item;
    }

    private void checkEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("can not remove item from empty queue");
        }
    }

    public Item removeLast() {
        checkEmpty();
        Node oldTail = tail;
        tail = oldTail.pre;
        if (tail != null) {
            tail.next = null;
        } else {
            head = tail;
        }
        size -= 1;
        return oldTail.item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator(head);
    }

    private void checkNull(Item item) {
        if (item == null) {
            throw new NullPointerException("the item can not be null:" + item);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Iterator<Item> iterator = iterator();
        while (iterator.hasNext()) {
            builder.append(iterator.next());
        }
        return builder.toString();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node head;

        public DequeIterator(Node head) {
            super();
            this.head = head;
        }

        @Override
        public boolean hasNext() {
            return head != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = head.item;
            head = head.next;
            return item;
        }

    }

    public static void main(String[] args) {
//        Deque<String> deque = new Deque<>();
//        deque.addFirst(null);
//        deque.addFirst("b");
//        deque.addLast("c");
//        deque.removeFirst();
//        deque.removeLast();
//        deque.removeLast();
//        System.out.println(deque.size);
//        System.out.println(deque);
    }
}
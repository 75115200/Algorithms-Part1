import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int DEFAULT_CAPACITY = 10;
    private Item[] items;
    private int size;

    public RandomizedQueue() {
        items = (Item[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        // add the item
        checkNull(item);
        ensureCapacity(size + 1);
        items[size++] = item;
    }

    public Item dequeue() {
        // remove and return a random item
        checkEmpty();
        ensureCapacity(size - 1);
        Item target;
        //特殊情况，当队列只有一个元素的时候
        if (size == 1) {
            target = items[0];
        } else {
            //注意随机的边界值
            int targetIndex = StdRandom.uniform(0, size);
            target = items[targetIndex];
            for (int i = targetIndex; i < size - 1; i++) {
                items[i] = items[i + 1];
            }
        }
        items[--size] = null;
        return target;
    }

    public Item sample() {
        // return (but do not remove) a random item
        checkEmpty();
        if (size == 1) {
            return items[0];
        }
        return items[StdRandom.uniform(0, size)];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator(size, items);
    }

    private void checkEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("can not dequeue empty queue");
        }
    }

    private void checkNull(Item item) {
        if (item == null) {
            throw new NullPointerException("the item can not be null");
        }
    }

    private void ensureCapacity(int nowSize) {
        //注意应该是数组压缩后的一半
        if (nowSize < items.length / 4) {
            resize(items.length / 2);
        }
        if (nowSize > items.length) {
            resize(items.length * 2);
        }
    }

    private void resize(int newCapacity) {
        Item[] newItems = (Item[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int queueSize;

        private Item[] queueItems;

        public RandomizedQueueIterator(int queueSize, Item[] items) {
            super();
            this.queueSize = queueSize;
            //注意不可以直接用引用，会导致原来的队列被打乱
            this.queueItems = (Item[]) new Object[queueSize];
            for (int i = 0; i < queueSize; i++) {
                queueItems[i] = items[i];
            }
            StdRandom.shuffle(queueItems);
        }

        @Override
        public boolean hasNext() {
            return queueSize != 0;
        }

        @Override
        public Item next() {
            if (queueSize <= 0) {
                throw new NoSuchElementException();
            }
            return queueItems[--queueSize];
        }

    }

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        for (int i = 0; i < 20000; i++) {
            queue.enqueue("a" + i);
        }
        for (int i = 0; i < 20000; i++) {
            System.out.println(queue.sample());
        }
    }
}

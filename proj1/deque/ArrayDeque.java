package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    private T[] items;

    // make array circular, nextFirst and nextLast are indexes
    private int nextFirst;
    private int nextLast;

    /** Creates an empty list. */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }
    // resizing the array(the spaces are at the back) and reset nextFirst, nextLast
    private void resize(int capacity) {
        T[] temp = (T[]) new Object[capacity];
        for (int j = 0; j < size; j++) {
            temp[j] = get(j);
        }
        items = temp;
        nextFirst = items.length - 1;
        nextLast = size;
    }
    /** Inserts X into the back of the list. */
    @Override
    public void addLast(T x) {

        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = x;
        size++;
        nextLast = Math.floorMod(nextLast + 1, items.length);
    }

    /** Returns the item from the back of the list.*/
    private T getLast() {
        return items[Math.floorMod(nextLast - 1, items.length)];
    }
    /** Gets the ith item in the list (0 is the front). */
    @Override
    public T get(int i) {
        // if the 0th item is not actually at 0th in the array
        return items[Math.floorMod(nextFirst + 1 + i, items.length)];
    }

    /** Returns the number of items in the list. */
    @Override
    public int size() {
        return size;
    }

    /** Deletes item from back of the list and
     * returns deleted item. */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (size > 16 && size - 1 < items.length * 0.25) {
            resize(size);
        }
        T result = getLast();
        items[Math.floorMod(nextLast - 1, items.length)] = null;
        nextLast = Math.floorMod(nextLast - 1, items.length);
        size--;

        return result;
    }
    @Override
    public void addFirst(T item) {
        if (items.length == size) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = Math.floorMod(nextFirst - 1, items.length);
        size++;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i));
            System.out.print(" ");
        }
        System.out.println();
    }
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (size > 16 && size - 1 < items.length * 0.25) {
            resize(size);
        }
        T result = get(0);
        items[Math.floorMod(nextFirst + 1, items.length)] = null;
        nextFirst = Math.floorMod(nextFirst + 1, items.length);
        size--;
        return result;
    }
    private class ArrayIterator implements Iterator<T> {
        private int pos;
        ArrayIterator() {
            pos = 0;
        }
        public boolean hasNext() {
            return pos < size;
        }
        public T next() {
            T result = get(pos);
            pos++;
            return result;
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new ArrayDeque.ArrayIterator();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Deque)) {
            return false;
        }
        Deque<T> o = (Deque<T>) other;
        if (o.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (this.get(i).equals(o.get(i))) {
                return false;
            }
        }
        return true;
    }

}

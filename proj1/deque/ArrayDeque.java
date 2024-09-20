package deque;

import java.util.Iterator;

public class ArrayDeque<Item> implements Deque<Item>, Iterable<Item>{
    private int size;
    private Item[] items;

    // make array circular, nextFirst and nextLast are indexes
    private int nextFirst;
    private int nextLast;

    /** Creates an empty list. */
    public ArrayDeque() {
        items = (Item[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }
    // resizing the array(the spaces are at the back) and reset nextFirst, nextLast
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int j = 0; j < size; j++){
            temp[j] = get(j);
        }
        items = temp;
        nextFirst = items.length - 1;
        nextLast = size;
    }
    /** Inserts X into the back of the list. */
    @Override
    public void addLast(Item x) {

        if (size == items.length){
            resize(size * 2);
        }
        items[nextLast] = x;
        size++;
        nextLast = Math.floorMod(nextLast + 1, items.length);
    }

    /** Returns the item from the back of the list.*/
    public Item getLast() {
        return items[Math.floorMod(nextLast - 1, items.length)];
    }
    /** Gets the ith item in the list (0 is the front). */
    @Override
    public Item get(int i) {
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
    public Item removeLast() {
        if (size == 0) {
            return null;
        }
        if (size > 16 && size - 1 < items.length * 0.25){
            resize(size);
        }
        Item result = getLast();
        items[Math.floorMod(nextLast - 1, items.length)] = null;
        nextLast = Math.floorMod(nextLast - 1, items.length);
        size--;

        return result;
    }
    @Override
    public void addFirst(Item item) {
        if (items.length == size) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = Math.floorMod(nextFirst - 1, items.length);
        size++;
    }

    @Override
    public void printDeque(){
        for (int i = 0; i < size; i++){
            System.out.print(get(i));
            System.out.print(" ");
        }
        System.out.println();
    }
    @Override
    public Item removeFirst(){
        if (size == 0) {
            return null;
        }
        if (size > 16 && size - 1 < items.length * 0.25){
            resize(size);
        }
        Item result = get(0);
        items[Math.floorMod(nextFirst + 1, items.length)] = null;
        nextFirst = Math.floorMod(nextFirst + 1, items.length);
        size--;
        return result;
    }
    private class ArrayIterator implements Iterator<Item> {
        private int pos;
        public ArrayIterator() {
            pos = 0;
        }
        public boolean hasNext() {
            return pos < size;
        }
        public Item next() {
            Item result = get(pos);
            pos++;
            return result;
        }
    }
    @Override
    public Iterator<Item> iterator(){
        return new ArrayDeque.ArrayIterator();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Deque)){
            return false;
        }
        Deque<Item> o = (Deque<Item>) other;
        if (o.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (this.get(i) != o.get(i)) {
                return false;
            }
        }
        return true;
    }

}
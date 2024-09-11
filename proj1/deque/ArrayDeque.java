package deque;

public class ArrayDeque<Item> {
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
    // resizing the array
    public void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, temp, 0, size);
        items = temp;
    }
    /** Inserts X into the back of the list. */
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
        return items[size-1];
    }
    /** Gets the ith item in the list (0 is the front). */
    public Item get(int i) {
        // if the 0th item is not actually at 0th in the array
        if (nextFirst != items.length - 1){
            // backNum: how many front-items that are at the back of the array
            int backNum = items.length - nextFirst;
            if (i < backNum){
                return items[nextFirst + i];
            } else {
                return items[i - backNum];
            }
        } else {
            return items[i];
        }
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    /** Deletes item from back of the list and
     * returns deleted item. */
    public Item removeLast() {
        Item result = getLast();
        items[size - 1] = null;
        nextLast = size - 1;
        size--;
        return result;
    }
    public void addFirst(Item item) {
        if (nextFirst == size - 1) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst--;
        size++;
    }
    public boolean isEmpty(){
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                return false;
            }
        }
        return true;
    }
    public void printDeque(){

    }
    public Item removeFirst(){
        if (nextFirst == items.length - 1){
            Item result = items[0];
            items[0] = null;

        }
    }

}
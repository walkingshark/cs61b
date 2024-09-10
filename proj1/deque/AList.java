package deque;
public class AList {
    private int size;
    private int[] items;
    /** Creates an empty list. */
    public AList() {
        items = new int[100];
        size = 0;
    }
    // resizing the array
    public void resize(int capacity) {
        int[] temp = new int[capacity];
        System.arraycopy(items, 0, temp, 0, size);
        items = temp;
    }
    /** Inserts X into the back of the list. */
    public void addLast(int x) {

        if (size == items.length){
            resize(size*2);
        }
        items[size] = x;
        size++;
    }

    /** Returns the item from the back of the list. */
    public int getLast() {
        return items[size-1];
    }
    /** Gets the ith item in the list (0 is the front). */
    public int get(int i) {
        return items[i];
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    /** Deletes item from back of the list and
     * returns deleted item. */
    public int removeLast() {
        int result = getLast();
        size--;
        return result;
    }
}


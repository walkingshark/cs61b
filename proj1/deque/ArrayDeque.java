package deque;
public class ArrayDeque<Item> {
    private int size;
    private Item[] items;
    /** Creates an empty list. */
    public ArrayDeque() {
        items = (Item[]) new Object[100];
        size = 0;
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
        items[size] = x;
        size++;
    }

    /** Returns the item from the back of the list.*/
    public Item getLast() {
        return items[size-1];
    }
    /** Gets the ith item in the list (0 is the front). */
    public Item get(int i) {
        return items[i];
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
        size--;
        return result;
    }
    public void addFirst(Item item){

    }
    public boolean isEmpty(){

    }
    public void printDeque(){

    }
    public Item removeFirst(){

    }

}
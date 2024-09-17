package deque;

public class ArrayDeque<Item> implements Deque<Item>{
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
        Item result = getLast();
        items[Math.floorMod(nextLast - 1, items.length)] = null;
        nextLast = Math.floorMod(nextLast - 1, items.length);
        size--;
        return result;
    }
    @Override
    public void addFirst(Item item) {
        if (nextFirst == size - 1) {
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
        Item result = get(0);
        items[Math.floorMod(nextFirst + 1, items.length)] = null;
        nextFirst = Math.floorMod(nextFirst + 1, items.length);
        size--;
        return result;
    }

}
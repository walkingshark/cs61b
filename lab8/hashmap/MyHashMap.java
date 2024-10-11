package hashmap;

import javax.naming.AuthenticationNotSupportedException;
import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int initialSize = 16;
    private double loadFactor = 0.75;
    private int size = 0;
    private HashSet<K> mset = new HashSet<>();
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        buckets = createTable(initialSize);
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
        size = 0;
        mset = new HashSet<>();
    }

    public MyHashMap(int initialSize) {
        buckets = createTable(initialSize);
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
        size = 0;
        mset = new HashSet<>();
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = createTable(initialSize);
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
        loadFactor = maxLoad;
        size = 0;
        mset = new HashSet<>();
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucknet type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return (Collection<Node>[]) new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    public void clear() {
        buckets = createTable(initialSize);
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
        size = 0;
        mset = new HashSet<>();

    }
    public boolean containsKey(K key) {
        int index = Math.floorMod(key.hashCode(), buckets.length);
        for (Node cur : buckets[index]) {
            if (key.equals(cur.key)) {
                return true;
            }
        }
        return false;
    }
    public V get(K key) {
        int index = Math.floorMod(key.hashCode(), buckets.length);
        for (Node cur : buckets[index]) {
            if (key.equals(cur.key)) {
                return cur.value;
            }
        }
        return null;
    }
    public int size() {
        return size;
    }
    protected void resize(int newSize) {
        Collection<Node>[] result = createTable(newSize);
        System.arraycopy(buckets, 0, result, 0, size());
        buckets = result;
    }
    public void put(K key, V value) {
        if ((size / buckets.length) > loadFactor) {
            resize(buckets.length * 2);
        }
        int index = Math.floorMod(key.hashCode(), buckets.length);
        for (Node cur : buckets[index]) {
            if (key.equals(cur.key)) {
                cur.value = value;
                return;
            }
        }
        buckets[index].add(createNode(key, value));
        size++;
        mset.add(key);
    }
    public Set<K> keySet() {
        return mset;
    }
    public Iterator<K> iterator() {
        return this.iterator();
    }
    public V remove(K key) {
        throw new UnsupportedOperationException("not supported");
    }
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("not supported");
    }
}

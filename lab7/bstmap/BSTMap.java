package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable, V> implements Map61B<K, V>{
    /* A bstNode has left node and right node*/
    private class BSTNode<K extends Comparable, V> {
        K key;
        V value;
        // BSTNode parent, left, right;
        BSTNode(K k, V v) {
            key = k;
            value = v;
        }
    }
    BSTNode<K, V> root;
    BSTMap<K, V> left, right;
    int size;
    BSTMap(){
        root = null;
        left = null;
        right = null;
        size = 0;
    }
    BSTMap(K key, V value) {
        root = new BSTNode<>(key, value);
        left = null;
        right = null;
        size = 1;
    }
    private boolean is_leaf() {
        return (left == null && right == null);
    }
    public void clear(){
        root.value = null;
        if (is_leaf()) {
            left.clear();
            right.clear();
        }
        size = 0;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key){
        if (is_leaf()) {
            return key.compareTo(root.key) == 0;
        } else if (key.compareTo(root.key) == 0) {
            return true;
        } else if (key.compareTo(root.key) > 0) {
            return right.containsKey(key);
        } else if (key.compareTo(root.key) < 0) {
            return left.containsKey(key);
        }
        return false;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key){
        if (is_leaf()) {
            if (root.key.compareTo(key) == 0) {
                return root.value;
            } else {
                return null;
            }
        } else if (root.key.compareTo(key) > 0) {
            return right.get(key);
        } else if (root.key.compareTo(key) < 0) {
            return left.get(key);
        } else {
            return root.value;
        }
    }

    /* Returns the number of key-value mappings in this map. */
    public int size(){
        return size;
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value){
        if (is_leaf() ) {
            if (key.compareTo(root.key) == 0) {
                root.value = value;
            } else if (key.compareTo(root.key) > 0) {
                right = new BSTMap(key, value);
            } else if (key.compareTo(root.key) < 0) {
                left = new BSTMap(key, value);
            }
            size++;
        } else if (key.compareTo(root.key) == 0) {
            root.value = value;
            size++;
        } else if (key.compareTo(root.key) > 0) {
            right.put(key, value);
        } else if (key.compareTo(root.key) < 0) {
            left.put(key, value);
        }
    }

    public Set<K> keySet(){
        throw new UnsupportedOperationException("not supported");
    }

    public V remove(K key){
        throw new UnsupportedOperationException("not supported");
    }

    public V remove(K key, V value){
        throw new UnsupportedOperationException("not supported");
    }
    private class BSTIterator implements Iterator<K> {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public K next() {
            return null;
        }
    }
    public Iterator<K> iterator(){
        throw new UnsupportedOperationException("not supported");
    }

}

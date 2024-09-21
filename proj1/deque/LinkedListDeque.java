package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private class StuffNode {
        private StuffNode prev;
        private T item;
        private StuffNode next;
        public StuffNode(StuffNode p, T i, StuffNode n) {
            prev = p;
            item = i;
            next = n;
        }
    }
    private StuffNode sentinel;
    private int size;
    public LinkedListDeque(T x) {
        sentinel = new StuffNode(null,  null, null);
        StuffNode newNode = new StuffNode(null, x, null);
        sentinel.next = newNode;
        sentinel.prev = newNode;
        newNode.prev = sentinel;
        newNode.next = sentinel;
        size = 1;
    }
    public LinkedListDeque() {
        sentinel = new StuffNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }
    @Override
    public void addFirst(T x) {
        StuffNode first = new StuffNode(null, x, null);
        first.next = sentinel.next;
        sentinel.next.prev = first;
        sentinel.next = first;
        first.prev = sentinel;

        size++;
    }


    @Override
    public void addLast(T x) {
        StuffNode last = new StuffNode(null, x, null);
        last.prev = sentinel.prev;
        sentinel.prev.next = last;
        sentinel.prev = last;
        last.next = sentinel;
        size++;

    }


    @Override
    public T removeLast() {
        //if there's only sentinel node
        if (sentinel.prev == sentinel) {
            return null;
        }
        T result = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return result;
    }
    @Override
    public T removeFirst() {
        if (sentinel.prev == sentinel) {
            return null;
        }
        T result = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size--;
        return result;
    }

    @Override
    public int size() {
        return size;
    }
    @Override
    public void printDeque() {
        StuffNode p = sentinel;
        while (p.next != sentinel) {
            p = p.next;
            System.out.print(p.item);
            System.out.print(" ");
        }
        System.out.println();
    }
    @Override
    public T get(int index) {
        StuffNode p = sentinel;
        for (int i = 0; i < size; i++) {
            p = p.next;
            if (i == index) {
                return p.item;
            } else if (i > index) {
                return null;
            }
        }
        return null;
    }
    private T getRecursive(int index, StuffNode p) {
        if (index == 0) {
            return p.item;
        } else {
            return getRecursive(index - 1, p.next);
        }
    }
    public T getRecursive(int index) {
        if (index + 1 > size) {
            return null;
        } else {
            return getRecursive(index, sentinel.next);
        }
    }
    /**
    public boolean equals(Object o) {
        if (!(o instanceof LinkedListDeque) || (o.size != this.size)){
            return false;
        }
    }*/
    // user can only get iterator from the method below, but can use hasNext, next, hence public
    private class LinkIterator implements Iterator<T> {
        private int pos;
        public LinkIterator() {
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
        return new LinkIterator();
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


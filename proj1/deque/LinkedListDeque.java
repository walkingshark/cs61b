package deque;

public class LinkedListDeque<T> {
    public class StuffNode {
        public StuffNode prev;
        public T item;
        public StuffNode next;
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
    public void addFirst(T x) {
        StuffNode first = new StuffNode(null, x, null);
        first.next = sentinel.next;
        sentinel.next.prev = first;
        sentinel.next = first;
        size++;
    }
    public T getFirst() {
        return sentinel.next.item;
    }
    public void addLast(T x) {
        StuffNode last = new StuffNode(null, x, null);
        last.prev = sentinel.prev;
        sentinel.prev.next = last;
        sentinel.prev = last;
        last.next = sentinel;
        size++;

    }
    public T getLast() {
        return sentinel.prev.item;
    }
    public T removeLast() {
        //if there's only sentinel node
        if (sentinel.prev == sentinel) {
            return null;
        }
        T result = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        return result;
    }
    public T removeFirst() {
        if (sentinel.prev == sentinel) {
            return null;
        }
        T result = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        return result;
    }
    public boolean isEmpty() {
        return sentinel.prev == sentinel;
    }
    public int size() {
        return size;
    }
    public void printDeque() {
        StuffNode p = sentinel;
        while (p.next != sentinel) {
            p = p.next;
            System.out.print(p.item);
            System.out.print(" ");
        }
        System.out.println();
    }
    public T get(int index) {
        StuffNode p = sentinel;
        for (int i = 0; i < size; i++) {
            p = p.next;
            if (i == index){
                return p.item;
            } else if (i > index) {
                return null;
            }
        }
        return null;
    }
    private T getRecursive(int index, StuffNode p) {
        if (index == 0){
            return p.item;
        } else {
            return getRecursive(index - 1, p.next);
        }
    }
    public T getRecursive(int index) {
        if (index + 1 > size) {
            return null;
        } else {
            return getRecursive(index, sentinel);
        }
    }
    public boolean equals(Object o) {
        if (!(o instanceof LinkedListDeque) || (o.size != this.size)){
            return false;
        }
    }
    public static void main(String[] args) {
        LinkedListDeque l = new LinkedListDeque(15);
        l.addFirst(10);
        l.addFirst(5);
        l.addLast(20);
        System.out.println(l.size());
    }
}

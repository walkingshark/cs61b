package deque;

public class SLList {
    public static class IntNode {
        public IntNode prev;
        public int item;
        public IntNode next;
        public IntNode(IntNode p, int i, IntNode n) {
            prev = p;
            item = i;
            next = n;
        }
    }
    private IntNode sentinel;
    private int size;
    public SLList(int x) {
        sentinel = new IntNode(null, -1, null);
        IntNode newNode = new IntNode(null, x, null);
        sentinel.next = newNode;
        sentinel.prev = newNode;
        newNode.prev = sentinel;
        newNode.next = sentinel;
        size = 1;
    }
    public SLList() {
        sentinel = new IntNode(null, -1, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }
    public void addFirst(int x) {
        IntNode first = new IntNode(null, x, null);
        first.next = sentinel.next;
        sentinel.next.prev = first;
        sentinel.next = first;
        size++;
    }
    public int getFirst() {
        return sentinel.next.item;
    }
    public void addLast(int x) {
        IntNode last = new IntNode(null, x, null);
        last.prev = sentinel.prev;
        sentinel.prev.next = last;
        sentinel.prev = last;
        last.next = sentinel;
        size++;

    }
    public int getLast() {
        return sentinel.prev.item;
    }
    public int removeLast() {
        //if there's only sentinel node
        if (sentinel.prev == sentinel) {
            return null;
        }
        int result = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        return result;
    }
    public int removeFirst() {
        if (sentinel.prev == sentinel) {
            return null;
        }
        int result = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        return result;
    }
    public int size() {
        return size;
    }
    public static void main(String[] args) {
        SLList l = new SLList(15);
        l.addFirst(10);
        l.addFirst(5);
        l.addLast(20);
        System.out.println(l.size());
    }
}

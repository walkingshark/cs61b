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
        sentinel = new IntNode(-1, null);
        sentinel.next = new IntNode(x, null);
        size = 1;
    }
    public SLList() {
        sentinel = new IntNode(-1, null);
        size = 0;
    }
    public void addFirst(int x) {
        sentinel.next = new IntNode(x, sentinel.next);
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

    }
    public int getLast() {

    }
    public int removeLast() {

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

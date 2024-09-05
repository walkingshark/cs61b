package deque;

public class SLList {
    public static class IntNode {
        public int item;
        public IntNode next;
        public IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }
    private IntNode first;
    private int size;
    public SLList(int x) {
        first = new IntNode(x, null);
        size = 1;
    }
    public void addFirst(int x) {
        first = new IntNode(x, first);
        size++;
    }
    public int getFirst() {
        return first.item;
    }
    public void addLast(int x) {
        IntNode p = first;
        while (p.next != null) {
            p = p.next;
        }
        p.next = new IntNode(x, null);
        size++;
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

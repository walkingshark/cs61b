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
    
    public SLList(int x) {
        first = new IntNode(x, null);

    }
    public void addFirst(int x) {
        first = new IntNode(x, first);

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

    }
    private int size(IntNode p) {
        if (p == null) {
            return 0;
        } else{
            return 1 + size(p.next);
        }
    }
    public int size() {
        return size(first);
    }
    public static void main(String[] args) {
        SLList l = new SLList(15);
        l.addFirst(10);
        l.addFirst(5);
        l.addLast(20);
        System.out.println(l.size());
    }
}

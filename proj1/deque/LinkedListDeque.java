package deque;

public class LinkedListDeque<T>{
    private static class aNode {
        public aNode prev;
        public T item;
        public aNode next;
        public aNode(T i, aNode n){
            item = i;
            next = n;
        }
    }
    private aNode sentinel;
    private int size;

    public LinkedListDeque(){
        sentinel = new aNode(-1, null);
        size = 0;
    }
    public LinkedListDeque(T x){
        sentinel = new aNode(-1, null);
        sentinel.next = new aNode(x, null);
        size = 1;
    }
}

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
        sentinel = new aNode(null, null);
        size = 0;
    }

    public void addFirst(T item){
        sentinel.item = item;
        sentinel.next = new aNode(item, sentinel.next);
        size++;
    }
    
    public static void main(String[] args) {

    }

}

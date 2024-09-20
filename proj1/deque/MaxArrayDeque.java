package deque;

import java.util.Comparator;

public class MaxArrayDeque<Item> extends ArrayDeque<Item> {
    Comparator<Item> maxCom;
    // Do I need to implement comparable?(If yes, might have to use tricks in 6.4-->bonus video)
    //comparator just need to use compareTo?(since elements are all comparables)

    public MaxArrayDeque(Comparator<Item> c) {
        super();
        maxCom = c;
    }
    public Item max() {
        Item result = get(0);
        for (int i = 0; i < size(); i++){
            Item cur = get(i);
            if (maxCom.compare(cur, result) > 0) {
                result = cur;
            }
        }
        return result;
    }

    public Item max(Comparator<Item> c) {
        Item result = get(0);
        for (int i = 0; i < size(); i++){
            Item cur = get(i);
            if (c.compare(cur, result) > 0) {
                result = cur;
            }
        }
        return result;
    }
}

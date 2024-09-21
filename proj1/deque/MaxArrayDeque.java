package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> maxCom;
    // Do I need to implement comparable?(If yes, might have to use tricks in 6.4-->bonus video)
    //comparator just need to use compareTo?(since elements are all comparables)

    public MaxArrayDeque(Comparator<T> c) {
        super();
        maxCom = c;
    }
    public T max() {
        T result = get(0);
        for (int i = 0; i < size(); i++) {
            T cur = get(i);
            if (maxCom.compare(cur, result) > 0) {
                result = cur;
            }
        }
        return result;
    }

    public T max(Comparator<T> c) {
        T result = get(0);
        for (int i = 0; i < size(); i++) {
            T cur = get(i);
            if (c.compare(cur, result) > 0) {
                result = cur;
            }
        }
        return result;
    }
}


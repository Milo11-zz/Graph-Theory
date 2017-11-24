package graphTheory;

import java.util.*;

/*
 * @author Eric
 */
public class RandomIterator {
    private ArrayList list = new ArrayList();
    
    public RandomIterator(java.util.List input) {
        for (Object o:input) {
            list.add(o);
        }
        Collections.shuffle(list);
    }
    
    public boolean hasNext() {
        return !list.isEmpty();
    }
    
    public Object getNext() {
        Object item = list.get(0);
        list.remove(0);
        return item;
    }
    
}

package IteratorList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class IteratorListV2<T> implements Iterator<T> {   //Итератор использует нтерфейс ListIterator, дженерик
    private ListIterator<T> listIt;

    public IteratorListV2(List<T> list) {
        listIt = list.listIterator();
     while (listIt.hasNext()) {
         listIt.next();
     }
     }

    @Override
    public boolean hasNext() {
        if(listIt.hasPrevious()) {
            return true;
        }
        return false;
    }

    @Override
    public T next() {
        if (hasNext()) {
            return (T)listIt.previous();
        }
        return null;


    }
}


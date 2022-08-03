package IteratorList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

//Класс использует интерфейс ListIterator для итерации, дженерик
public class IteratorListV2<T> implements Iterator<T>
{
    private ListIterator<T> listIterator;

    public IteratorListV2(List<T> list)
    {
        listIterator = list.listIterator();
        while(listIterator.hasNext())
        {
            listIterator.next();
        }
    }

    @Override
    public boolean hasNext()
    {
        if(listIterator.hasPrevious())
        {
            return true;
        }
        return false;
    }

    @Override
    public T next()
    {
        if(!hasNext())
        {
            throw new NoSuchElementException();
        }
        else
        {
            return (T)listIterator.previous();
        }
    }
}


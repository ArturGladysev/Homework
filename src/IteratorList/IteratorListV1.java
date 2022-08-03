package IteratorList;
import java.util.*;


//Итератор использует индекс List-а для итерации и не является дженериком (row тип, просто для наглядности)
public class IteratorListV1 implements Iterator
{
    private List list;

    private int index;

    public IteratorListV1(List list)
    {
        this.list = list;
        this.index = list.size() - 1;
    }

    @Override
    public boolean hasNext()
    {
        if(index > 0)
        {
            return true;
        }
        return false;
    }

    @Override
    public Object next() throws NoSuchElementException
    {
        if(!hasNext())
        {
            throw new NoSuchElementException();
        }
        else
        {
            --index;
            return list.get(index);
        }
    }
}

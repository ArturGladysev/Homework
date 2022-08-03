package CountMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CountMapImp<T> implements CountMap<T>
{
    private Object[] entryArray = new Object[10];

    private int size = 0;

    private int capacity = 10;

    public void add(T elem)
    {
        if(size == capacity)
        {
            Object[] copyEntryArray = new Object[size * 2];
            for(int i = 0; i < entryArray.length; ++i)
            {
                copyEntryArray[i] = entryArray[i];
            }
            entryArray = copyEntryArray;
            ++size;
            entryArray[size - 1] = elem;
            capacity *= capacity;
        }
        else
        {
            ++size;
            entryArray[size - 1] = elem;
        }
    }

    public int getCount(T elem)
    {
        int count = 0;
        for(int i = 0; i < this.size; ++i)
        {
            if(elem.equals((T)entryArray[i]))
            {
                ++count;
            }
        }
        return count;
    }

    public int remove(T elem)
    {
        int count = getCount(elem);
        Object[] destinationEntryArray = new Object[capacity];
        int position = 0;
        for(int i = 0; i < this.size; ++i)
        {
            if(elem.equals((T)entryArray[i]))
            {
                ++position;
            }
            else
            {
                destinationEntryArray[i - position] = entryArray[i];
            }
        }
        entryArray = destinationEntryArray;
        size -= count;
        return count;
    }

    public int uniqueValuesAmount()
    {
        HashSet<T> uniqueValues = new HashSet<>();
        for(int i = 0; i < this.size; ++i)
        {
            uniqueValues.add((T)entryArray[i]);
        }
        return uniqueValues.size();
    }

    public void addAll(CountMap<T> source)
    {
        Map<T, Integer> sourceMap = source.toMap();
        for(Map.Entry<T, Integer> entryMap : sourceMap.entrySet())
        {
            for(int i = 0; i < entryMap.getValue(); ++i)
            {
                this.add(entryMap.getKey());
            }
        }
    }

    public Map<T, Integer> toMap()
    {
        Map<T, Integer> map = new HashMap<>();
        for(int i = 0; i < this.size; ++i)
        {
            int val = getCount((T)entryArray[i]);
            map.put((T)entryArray[i], val);
        }
        return map;
    }

    public void toMap(Map<? super T, Integer> destination)
    {
        for(int i = 0; i < this.size; ++i)
        {
            int val = getCount((T)entryArray[i]);
            destination.put((T)entryArray[i], val);
        }
    }
}




package CountMap;

import java.util.Map;

public interface CountMap<T>
{
    void add(T o);

    int getCount(T o);

    int remove(T o);

    int uniqueValuesAmount();

    void addAll(CountMap<T> source);

    Map<T, Integer> toMap();

    void toMap(Map<? super T, Integer> destination);
}


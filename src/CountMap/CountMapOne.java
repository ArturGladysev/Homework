package CountMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CountMapOne<T> implements CountMap<T> {

    private Object[] entry_o = new Object[10];
private int size = 0;
private int capasity = 10; // заменить имя

    public void add(T o) {
        if (size == capasity) {
            Object[] copy_entry = new Object[size * 2];
            for (int i = 0; i < entry_o.length; ++i) {
                copy_entry[i] = entry_o[i];
            }
            entry_o = copy_entry;
            ++size;
            entry_o[size - 1] = o;
            capasity = capasity * 2;

        }
         else {
           ++size;
           entry_o[size-1] = o;
        }

    }

   public int getCount(T elem) {
        int count = 0;
           for (int i = 0; i < this.size; ++i) {
               if (elem.equals((T) entry_o[i])) {
                   ++count;
               }
           }
       return count;
   }

    public int remove(T elem) {
        int count = getCount(elem);
        Object[] entry_remove = new Object[capasity];
        int position = 0;
        for (int i = 0; i< this.size; ++i) {
            if (elem.equals((T) entry_o[i])) {
                ++position;
            }
        else {
            entry_remove[i-position] = entry_o[i];
        }

            }
        entry_o = entry_remove;
        size-=count;
        return count;
    }



    public int size() {
        HashSet<T> unique_val = new HashSet<>();
        for (int i = 0; i < this.size; ++i) {
            unique_val.add((T) entry_o[i]);
        }
        return unique_val.size();
    }


    public void addAll( CountMap<T> source) {
        Map<T, Integer> source_map = source.toMap();
        for (Map.Entry<T, Integer> g : source_map.entrySet()) {
            for (int i = 0; i < g.getValue(); ++i) {
                this.add(g.getKey());
            }
        }
    }

    public Map<T , Integer> toMap() {
        Map<T, Integer> map = new HashMap<>();
        for (int i = 0; i < this.size; ++i) {
            int val = getCount((T)entry_o[i]);
            map.put((T) entry_o[i], val);
        }
        return map;
    }

    public void toMap(Map<? super T, Integer> destination) {
        for (int i = 0; i < this.size; ++i) {
            int val = getCount((T)entry_o[i]);
            destination.put((T) entry_o[i], val);
        }
    }

    }




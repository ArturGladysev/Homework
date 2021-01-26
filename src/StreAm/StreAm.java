package StreAm;

import java.io.*;
import java.util.*;
import java.util.function.*;


public class StreAm<T> {      //Класс хранит итератор , конструкторы могут принимать коллекции, массивы и потоки. Объект создается через
                              // статический метод, методы возвращают новый Стрим и с новым итератором, либо ссылку this.
    Iterator<T> entryIterator;

    private StreAm(Collection<T> collection) {
        entryIterator = collection.iterator();
    }


    public static <T> StreAm<T> of(Collection<T> collection) {
        return new StreAm<T>(collection);

    }

    public static <T> StreAm<T> of(T[] mas) {
        List<T> list = Arrays.asList(mas);
        return new StreAm<T>(list);

    }

    public static <T> StreAm<T> of(InputStream in) {
        ArrayList<T> inputObjects = new ArrayList<>();
        try {
            ObjectInputStream inPut = new ObjectInputStream(in);
            for (Object ob = inPut.readObject(); ; ob = inPut.readObject()) {
                inputObjects.add((T) ob);
            }
        } catch (IOException eo) {
            eo.printStackTrace();
            System.out.println("Достигнут конец файла");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new StreAm<T>(inputObjects);

    }


    public StreAm<T> filter(Predicate<? super T> pred) {
        ArrayList<T> methodList = new ArrayList<T>();
        while (entryIterator.hasNext()) {
            T val = entryIterator.next();
            if (pred.test(val)) {
                methodList.add(val);
            }
        }
        System.out.println(methodList);
        return new StreAm<T>(methodList);
    }

    public <R> StreAm<R> transform(Function<? super T, ? extends  R> func) {
        ArrayList<R> methodList = new ArrayList<R>();
        while (entryIterator.hasNext()) {
            T val = entryIterator.next();
            methodList.add(func.apply(val));
        }
        System.out.println(methodList);
        return new StreAm<R>(methodList);
    }

    public <K, V> HashMap<K, V> toMap(Function<? super T,? extends K> getKeyForMap, Function<? super T,? extends V> getValForMap) {
        Map<K, V> map = new HashMap<K, V>();
        while (entryIterator.hasNext()) {
            T val = entryIterator.next();
            map.put(getKeyForMap.apply(val), getValForMap.apply(val));
        }
        return new HashMap<K, V>(map);
    }

    public StreAm<T> distinct() {
        distinctDeep(entryIterator);
        return this;
    }

    private void distinctDeep(Iterator<T> it) {
        LinkedHashMap<String, T> map = new LinkedHashMap<>();
        while (it.hasNext()) {
            T val = it.next();
            String key = val.toString();
            map.put(key, val);
        }
        //System.out.println(map);
    Collection<T> set = map.values();
        System.out.println(set);
        entryIterator = set.iterator();
    }

 public void forEach(Consumer<? super T> consum) {
     while (entryIterator.hasNext()) {
         T val = entryIterator.next();
    consum.accept(val);
     }
 }

public <E>StreAm<E> generate( Supplier<? extends E> generait, int count) {
        ArrayList<E> getValues = new ArrayList<>();
    for(int j = 0; j<count; ++j) {
        getValues.add(generait.get());
    }
 return new StreAm<E>(getValues);
}

    public T reduce(T sum , BinaryOperator<T> accum) {
        while (entryIterator.hasNext()) {
            T val = entryIterator.next();
            sum = accum.apply(sum, val);
        }
        return sum;
    }


public  StreAm<T> sorted(Comparator<? super T> comp) {
    ArrayList<T> methodList = new ArrayList<T>();
    while (entryIterator.hasNext()) {
        T val = entryIterator.next();
        methodList.add(val);
    }
 Collections.sort(methodList, comp);
return new StreAm<T>(methodList);
}

public StreAm<Integer> toInt() {
    ArrayList<Integer> methodList = new ArrayList<Integer>();
    while (entryIterator.hasNext()) {
        T val = entryIterator.next();
methodList.add(Integer.parseInt((String)val));
    }
return new StreAm<Integer>(methodList);

}
}
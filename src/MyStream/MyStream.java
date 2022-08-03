package MyStream;

import java.io.*;
import java.util.*;
import java.util.function.*;

/*Класс хранит итератор для перебора элементов.
  Конструкторы могут принимать коллекции, массивы и потоки. Объект создается через
  статический метод, методы возвращают новый Стрим с новым итератором, либо ссылку this.
*/
public class MyStream<T>
{
    Iterator<T> entryIterator;

    private MyStream(Collection<T> collection)
    {
        entryIterator = collection.iterator();
    }

    public static <T> MyStream<T> of(Collection<T> collection)
    {
        return new MyStream<>(collection);
    }

    public static <T> MyStream<T> of(T[] mas)
    {
        List<T> list = Arrays.asList(mas);
        return new MyStream<T>(list);
    }

    public static <T> MyStream<T> of(InputStream in)
    {
        ArrayList<T> sourceObjects = new ArrayList<>();
        ObjectInputStream objectInputStream = null;
        try
        {
            objectInputStream = new ObjectInputStream(in);
            for(Object ob = objectInputStream.readObject(); ; ob = objectInputStream.readObject())
            {
                sourceObjects.add((T)ob);
            }
        }
        catch(IOException eo)
        {
            eo.printStackTrace();
            System.out.println("Достигнут конец файла");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                objectInputStream.close();
            }
            catch(IOException ec)
            {
                System.out.println("Файл не может быть закрыт");
                ec.printStackTrace();
            }
        }
        return new MyStream<>(sourceObjects);
    }

    public MyStream<T> filter(Predicate<? super T> pred)
    {
        ArrayList<T> values = new ArrayList<T>();
        while(entryIterator.hasNext())
        {
            T val = entryIterator.next();
            if(pred.test(val))
            {
                values.add(val);
            }
        }
        System.out.println(values);
        return new MyStream<>(values);
    }

    public <R> MyStream<R> transform(Function<? super T, ? extends R> func)
    {
        ArrayList<R> values = new ArrayList<>();
        while(entryIterator.hasNext())
        {
            T val = entryIterator.next();
            values.add(func.apply(val));
        }
        System.out.println(values);
        return new MyStream<>(values);
    }

    public <K, V> HashMap<K, V> toMap(Function<? super T, ? extends K> getKeyByMap, Function<? super T, ? extends V> getValByMap)
    {
        Map<K, V> destinationMap = new HashMap<K, V>();
        while(entryIterator.hasNext())
        {
            T val = entryIterator.next();
            destinationMap.put(getKeyByMap.apply(val), getValByMap.apply(val));
        }
        return new HashMap<>(destinationMap);
    }

    public MyStream<T> distinct()
    {
        distinctDeep(entryIterator);
        return this;
    }

    private void distinctDeep(Iterator<T> it)
    {
        LinkedHashMap<String, T> destinationMap = new LinkedHashMap<>();
        while(it.hasNext())
        {
            T val = it.next();
            String key = val.toString();
            destinationMap.put(key, val);
        }
        Collection<T> values = destinationMap.values();
        System.out.println(values);
        entryIterator = values.iterator();
    }

    public void forEach(Consumer<? super T> consumer)
    {
        while(entryIterator.hasNext())
        {
            T val = entryIterator.next();
            consumer.accept(val);
        }
    }

    public <E> MyStream<E> generate(Supplier<? extends E> supplier, int count)
    {
        ArrayList<E> values = new ArrayList<>();
        for(int j = 0; j < count; ++j)
        {
            values.add(supplier.get());
        }
        System.out.println(values);
        return new MyStream<>(values);
    }

    public T reduce(T sum, BinaryOperator<T> accum)
    {
        while(entryIterator.hasNext())
        {
            T val = entryIterator.next();
            sum = accum.apply(sum, val);
        }
        return sum;
    }

    public MyStream<T> sorted(Comparator<? super T> comp)
    {
        ArrayList<T> values = new ArrayList<T>();
        while(entryIterator.hasNext())
        {
            T val = entryIterator.next();
            values.add(val);
        }
        Collections.sort(values, comp);
        System.out.println(values);
        return new MyStream<>(values);
    }

    public MyStream<Integer> toInt()
    {
        ArrayList<Integer> values = new ArrayList<Integer>();
        while(entryIterator.hasNext())
        {
            T val = entryIterator.next();
            values.add(Integer.parseInt((String)val));
        }
        System.out.println(values);
        return new MyStream<>(values);
    }
}
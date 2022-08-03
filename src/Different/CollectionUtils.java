package Different;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/* Задача: написать утильный класс, методы которого работают
   с параметризованными коллекциями (наследниками List), помогая осуществлять
   распространенные операции с одной и двумя коллекциями.
 */

public class CollectionUtils {

    public static void main(String[] args)
    {
        List<Integer> integerList = newArrayList();
        List<Number> numberList = newArrayList();
        Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).forEach(number -> integerList.add(number));
        addAll(integerList, numberList);
        System.out.println("Метод allAll()" + "\n" + numberList);

        List<Integer> numbers = newArrayList();
        for(int i = 10; i < 20; ++i)
        {
            numbers.add(i);
        }
        System.out.println("метод indexOf:" + "\n" + indexOf(integerList, 1));
        List<Integer> subList = limit(integerList, 5);
        System.out.println("метод limit:" + "\n" + subList);
        add(integerList, 10);
        add(integerList, 11);
        add(integerList, 12);
        System.out.println("метод add:" + "\n" + integerList);
        removeAll(integerList, numbers);
        System.out.println("метод removeAll:" + "\n" + integerList);
        List<Integer> copyIntegerList = new ArrayList(integerList);
        boolean isContains = containsAll(integerList, copyIntegerList);
        System.out.println("метод containsAll:" + "\n" + isContains);
        isContains = containsAll(numbers, copyIntegerList);
        System.out.println(isContains + "\n" + "метод containsAny:");
        System.out.println(containsAny(integerList, copyIntegerList));
        System.out.println(containsAny(integerList, numbers));
        List<Integer> ran = range(integerList, 2, 6);
        System.out.println("метод range:" + "\n" + ran);
    }

    public static <T> void addAll(List<? extends T> source, List<? super T> destination)
    {
        destination.addAll(source);
    }

    public static <T> List<T> newArrayList()
    {
        return new ArrayList<>();
    }

    public static <T> int indexOf(List<? extends T> source, T value)
    {
        return source.indexOf(value);
    }

    public static <T> List<T> limit(List<T> source, int size)
    {
        return source.subList(0, size);
    }

    public static <T> void add(List<? super T> source, T value)
    {
        source.add(value);
    }

    public static <T> void removeAll(List<? super T> target, List<? extends T> source)
    {
        target.removeAll(source);
    }

    public static <T> boolean containsAll(List<? super T> target, List<? extends T> source)
    {
        return target.containsAll(source);
    }

    public static <T> boolean containsAny(List<? super T> target, List<? extends T> source)
    {
        for(int i = 0; i < source.size(); ++i)
        {
            if(target.contains(source.get(i)))
            {
                return true;
            }
        }
        return false;
    }

    public static <T extends Comparable<? super T>> List<T> range(List<T> source, T min, T max)
    {
        Collections.sort(source);
        int minIndex = source.indexOf(min);
        int maxIndex = source.indexOf(max);
        return source.subList(minIndex, maxIndex);
    }

    public static <T extends Comparator<? super T>> List<T> range(List<T> source, T min, T max, Comparator<T> comparator)
    {
        source.sort(comparator);
        int minIndex = source.indexOf(min);
        int maxIndex = source.indexOf(max);
        return source.subList(minIndex, maxIndex);
    }
}
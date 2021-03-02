import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionUtils {

    public static <T> void addAll(List<? extends T> source, List<? super T> destination) {
        destination.addAll(source);
    }


    public static <T> List<T> newArrayList() {
        return new ArrayList<T>();
    }


    public static <T>int indexOf(List<? extends T> source, T value) {
    return source.indexOf(value);
    }

    public static <T> List<T> limit(List<T> source, int size) {
      return source.subList(0 , size);
    }

    public static <T>void add(List<? super T> source, T value) {
    source.add(value);
    }

    public static <T>void removeAll(List<? super T> removeFrom, List<? extends T> removethis) {
      removeFrom.removeAll(removethis);
    }

    public static <T>boolean containsAll(List<? super T> c1, List<? extends T> c2) {
    return c1.containsAll(c2);
    }

    public static <T>boolean containsAny(List<? super T> c1, List<? extends T> c2) {
    for(int i = 0; i< c2.size(); ++i) {
        if(c1.contains((T)c2.get(i))) {
            return true;
    }
    }
        return false;
    }

    public static <T extends Comparable<? super T>> List<T> range(List<T> list, T min, T max) {
        Collections.sort(list);
        int min_ind = list.indexOf(min);
int max_ind = list.indexOf(max);
return list.subList(min_ind, max_ind);
    }

    public static <T extends Comparator<? super T>>List<T> range(List<T> list, T min, T max, Comparator<T> comparator) {
    list.sort(comparator);
        int min_ind = list.indexOf(min);
        int max_ind = list.indexOf(max);
        return list.subList(min_ind, max_ind);
    }


    //метод newArrayList()
    public static void main(String[] args) {
         List<Integer> list_one = newArrayList();
        List<Number> list_two = newArrayList();
     list_one.add(1);
        list_one.add(2);
        list_one.add(3);
        list_one.add(4);
        list_one.add(5);
        list_one.add(6);
        addAll(list_one, list_two);
        System.out.println("Метод allAll()");
        System.out.println(list_two);

        List<Integer> test = newArrayList();
        for(int i = 0; i<10; ++i) {
            test.add(i);
        }
        List<Integer> test2 = newArrayList();
   for(int i = 10; i<20; ++i) {
       test2.add(i);
   }
        System.out.println("метод indexOf:");
        System.out.println(indexOf(test, 1));

        List<Integer> sub = limit(test, 5) ;
        System.out.println("метод limit:");
        System.out.println(sub);
      add(test, 10);
        add(test, 11);
        add(test, 12);
      System.out.println("метод add:");
        System.out.println(test);
   removeAll(test, test2);
        System.out.println("метод removeAll:");
        System.out.println(test);

        List<Integer> test3 = new ArrayList(test);
        boolean b = containsAll(test , test3);
        System.out.println("метод containsAll:");
        System.out.println(b);
        b = containsAll(test2 , test3);
        System.out.println(b);
        System.out.println("метод containsAny:");
        System.out.println(containsAny(test, test3));
        System.out.println(containsAny(test, test2));

    List<Integer> ran = range(test , 2, 6);
        System.out.println("метод range:");
        System.out.println(ran);


    }

}
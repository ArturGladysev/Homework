
import java.util.Scanner;

public class BinarySearch {
    public static Integer[] getSortArray(int size) {    //Метод получает массив заданного пользователем размера
        Integer[] sort_array = new Integer[size];       //со случайными значениями < 1000
        for(int i = 0; i<size; ++i) {
            sort_array[i] =(int) (Math.random()*1000);
        }
        BubbleSort.sort_ins(sort_array);                 //Сортировка "пузырьком"
        return sort_array;
    }


    public static void testBinarySort( Integer[] sort_mass) {     //Метод ищет каждый элемент массива
        for(int v : sort_mass) {                                 // бинарным поиском и возвращает его индекс
            int res = myBinarySearch(sort_mass, v);
            System.out.println("Element:" + Integer.toString(v) + " >" + "Position:" + Integer.toString(res));
        }
    }


    public static <T extends Comparable<? super T>> int  myBinarySearch(T[] sort_mass, T val) { //Бинарный поиск
        int first = 0;
        int last = sort_mass.length - 1;
        while(first <= last) {
            int bin = (first + last)/2;
            T v = sort_mass[bin];
            int res = v.compareTo(val);
            if(res ==0)  {
                return bin;
            }
            else if (res < 0 ) {
                first = bin + 1;

            }
            else if (res > 0) {
                last = bin - 1;

            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("Введите размер массива >");
        Scanner scan =  new Scanner(System.in);
        int size = scan.nextInt();
        Integer[] array = getSortArray(size);
        for(int i = 0; i < array.length; ++i) {
            System.out.print("[" + i + "]" + Integer.toString(array[i]) + " ");
        }
        System.out.println();
        testBinarySort(array);

    }
}



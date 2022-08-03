package CountMap;

import java.util.HashMap;
import java.util.Map;

/*
Написать класс, похожий на ArrayList (без использования стандартных библотек),
реализовав для него импелементацию CountMap (методы интерфейса были даны в задаче)- класса, подсчитывающего количество повторяющихся
значений, уникальных значений. Реализовать метод добавления подсчитанных значений и метод, возвращающий Map с подсчитанными значениями.
 */

public class Main
{
    public static void main(String[] args)
    {
        CountMap<String> countMapImp = new CountMapImp<String>();
        countMapImp.add("one");
        countMapImp.add("two");
        countMapImp.add("two");
        countMapImp.add("three");
        countMapImp.add("three");
        countMapImp.add("three");
        countMapImp.add("four");
        countMapImp.add("four");
        countMapImp.add("four");
        countMapImp.add("four");
        countMapImp.add("five");
        countMapImp.add("five");
        countMapImp.add("five");
        countMapImp.add("five");
        countMapImp.add("five");

        System.out.println("Метод  uniqueValuesAmount:" + "\n" + countMapImp.uniqueValuesAmount() + "\n" + "Метод  getCount:" + "\n" +
          countMapImp.getCount("one") + " " + countMapImp.getCount("two") + " " + countMapImp.getCount("four"));

        System.out.println("Метод  remove(\"five\"):");
        int count = countMapImp.remove("five");
        System.out.println("совпадений:" + " " + count + " " + "количество уникальных значений:" + " " + countMapImp.uniqueValuesAmount());

        System.out.println("Метод  toMap:");
        Map<String, Integer> map = countMapImp.toMap();
        System.out.println(map.toString());

        System.out.println("Метод  toMap(map):");
        Map<String, Integer> countValues = new HashMap<>();
        countMapImp.toMap(countValues);
        System.out.println(countValues);

        System.out.println("Метод addAll:");
        CountMap<String> countMapImpTwo = new CountMapImp<String>();
        countMapImpTwo.add("six");
        countMapImpTwo.add("six");
        countMapImpTwo.add("six");
        countMapImpTwo.add("six");
        countMapImpTwo.add("six");
        countMapImpTwo.add("six");
        countMapImpTwo.add("one");
        countMapImpTwo.add("two");
        countMapImp.addAll(countMapImpTwo);
        countValues = countMapImp.toMap();
        System.out.println(countValues);
    }
}

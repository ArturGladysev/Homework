package StreAm;

import java.util.*;

public class MainStreAm {
    public static void main(String[] args) {
        List<Person> testList = Arrays.asList(new Person("Leo", 22, "Male"), new Person("Anna", 21, "Female"),
                new Person("Alex", 18, "Male") , new Person("Rita", 34, "Female"),
                new Person("Rita", 34, "Female"));
      ArrayList<Person> persons = new ArrayList<>(testList);
        System.out.println("Коллекция до изменений:");
      System.out.println(persons);
      System.out.println("/");
      Map<String , Integer> map = StreAm.of(persons).distinct().filter(person -> person.getAge() >20 ).transform(person -> person.getName())
              .toMap(name -> name , name -> name.length());   //1.Убираются дублекаты. 2.персоны фильтруются по возрасту.
        System.out.println(map);                              // 3.Производится выборка имен. 4.Возвращается Map - имя\кол-во символов в имени.
        System.out.println("/");
        System.out.println("Коллекция после изменений:");
        System.out.println(persons);
        System.out.println("/");
        StreAm.of(persons).sorted((person1, person2) -> person1.compareTo(person2)).forEach(System.out::println);
// 1.Сортировка по возрасту 2.Вывод значений
        List<String> testList2 = Arrays.asList("1" , "2" , "3" , "4" ,"5");
      int sum =  StreAm.of(testList2).toInt()/*.generate(() -> 10, 10)*/.reduce(0 , (n , n2) -> { return n + n2;});
        System.out.println(sum);            //1.Трансформация в инты если на входе строки. 2.Подсчет значений и возврат результата
    }


}

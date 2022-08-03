package MyStream;

import java.util.*;

/* Задача: написать класс, похожий на Stream, взяв за основу итератор.*/

public class Main
{
  public static void main(String[] args)
  {
    List<Person> personList = Arrays.asList(new Person("Leo", 22, "Male"), new Person("Anna", 21, "Female"),
      new Person("Alex", 18, "Male"), new Person("Rita", 34, "Female"),
      new Person("Rita", 34, "Female"));
    ArrayList<Person> persons = new ArrayList<>(personList);
    System.out.println("Коллекция до изменений:" + "\n"+ persons + "\n" + "/");

    /*1. Исключаются дубликаты.
      2. Личности фильтруются по возрасту.
      3. Производится выборка имен.
      4. Возвращается Map - имя\кол-во символов в имени.
      (Для наглядности, происходящие в Стриме изменения выводятся в консоль из методов)
      */
    Map<String, Integer> resultMap = MyStream.of(persons).distinct().filter(person -> person.getAge() > 20).transform(person -> person.getName())
      .toMap(name -> name, name -> name.length());
    System.out.println(resultMap);
    System.out.println("/" + "\n" + "Коллекция после работы со Стримом не изменилась:" + "\n" + persons + "\n" + "/");

    /* 1. Сортировка по возрасту
       2. Вывод значений
       3. Трансформация строк в числа.
       4. Подсчет сгенерированных значений и возврат результата
    */
    MyStream.of(persons).sorted(Comparator.naturalOrder()).forEach(System.out::println);
    List<String> numbers = Arrays.asList("1", "2", "3", "4", "5");
    System.out.println();
    int sum = MyStream.of(numbers).toInt().generate(() -> 10, 10).reduce(0, (n, n2) -> n + n2);
    System.out.println(sum);
  }
}

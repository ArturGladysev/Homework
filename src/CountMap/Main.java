package CountMap;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        CountMap<String> map_one = new CountMapOne<String>();
        map_one.add("one");
        map_one.add("two");
        map_one.add("two");
        map_one.add("three");
        map_one.add("three");
        map_one.add("three");
        map_one.add("four");
        map_one.add("four");
        map_one.add("four");
        map_one.add("four");
        map_one.add("five");
        map_one.add("five");
        map_one.add("five");
        map_one.add("five");
        map_one.add("five");
        System.out.println("Метод  size:");
        System.out.println(map_one.size());
        System.out.println("Метод  getCount:");
        System.out.println(map_one.getCount("one") + " " + map_one.getCount("two") + " " + map_one.getCount("four"));
        System.out.println("Метод  remove:");
      int count_five = map_one.remove("five");
        System.out.println("совпадений:" + " " + count_five + " " + "размер:" + " " +map_one.size());
       System.out.println("Метод  toMap:");
        Map<String, Integer> map = map_one.toMap();
        System.out.println(map.toString());
Map<String , Integer> hash_map = new HashMap<>();
map_one.toMap(hash_map);
        System.out.println("Метод  toMap(d):");
        System.out.println(hash_map);
        CountMap<String>  map_two = new CountMapOne<String>();
map_two.add("six");
        map_two.add("six");
        map_two.add("six");
        map_two.add("six");
        map_two.add("six");
        map_two.add("six");
        map_two.add("one");
        map_two.add("two");
   map_one.addAll(map_two);
    hash_map = map_one.toMap();
        System.out.println("Метод addAll:");
        System.out.println(hash_map);
    }



}

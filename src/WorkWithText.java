import java.io.FileReader;
import java.util.*;


public class WorkWithText {

    public static class Mysort implements Comparator<String> {
        @Override
        public int compare(String one, String two) {
              if(one.length() == two.length()) {
                  for (int i = 0; i < two.length(); ++i) {
                      if (one.charAt(i) > two.charAt(i)) {
                          return 1;
                      } else {
                          return -1;
                      }

                  }
                   return 0;
              }
              else if(one.length() > two.length()) {
                  return 1;
              }
                       else {
                  return -1;
              }
            }
        }


    public static void main(String[] args) {
        try {
            String f = "resourse/text";
            FileReader reader = new FileReader(f);
            Scanner scan = new Scanner(reader);
            HashSet<String> h_s = new HashSet<>();
            while (scan.hasNext()) {                       /*1.Считываем каждое слово и добавляем в Хэшсет, если оно уникально
                                                               Размер сета - кол-во уникальных слов                                                                                */
                h_s.add(scan.next());
            }
            TreeSet<String> set = new TreeSet<String>(new Mysort());   //2. Инициализируем Трисет компоратором, чтобы отсортировать
            for (String s : h_s) {                                   // слова сначала по длинне, а потом по тексту.
                set.add(s);
            }
            System.out.println("Количество уникальных слов:" + set.size());
            System.out.println("Cортровка уникальных слов:");
            System.out.println(set);
            reader.close();
            reader = new FileReader(f);
            scan.reset();
            scan = new Scanner(reader);
            String ss;
            HashMap<String, Integer> map = new HashMap<>();
            while (scan.hasNext()) {
                ss = scan.next();
                if (map.containsKey(ss)) {
                    Integer val = map.get(ss);
                    int val2 = val.intValue();
                    ++val2;
                    map.put(ss, val2);
                } else {
                    map.put(ss, 1);
                }
            }
            System.out.println("Подсчет слов в файле:");
            System.out.println(map);
            ArrayList<String> array = new ArrayList<>();
            reader.close();
            reader = new FileReader(f);
            scan.reset();
            scan = new Scanner(reader);
            while (scan.hasNext()) {
                ss = scan.nextLine();
                array.add(ss);
            }
            ArrayList<String> array2 = new ArrayList<>(array);

            Collections.reverse(array);
            System.out.println("Все строки файла в обратном порядке:");
            for (String word : array) {
                System.out.println(word);
            }
            Scanner scan2 = new Scanner(System.in);
            ArrayList<Integer> index = new ArrayList<>();
            System.out.println("Введите индексы строк файла до " + array2.size() + ">");
            ss = scan2.nextLine();
            String[] ss_m = ss.split(" ");

            for (int i = 0; i < ss_m.length; ++i) {
                index.add(Integer.parseInt(ss_m[i]));
            }
            System.out.println("Строки под индексами:");
            for (int j : index) {
                System.out.println(j + ". " + array2.get(j));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}

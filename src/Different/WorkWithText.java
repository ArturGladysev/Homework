package Different;

import java.io.FileReader;
import java.util.*;

/* Задача: добавить в программу текст из файла и провести с ним следующие операции:
 1. Подсчет уникальных слов.
 2. Сортировка уникальных слов.
 3. Подсчет дубликатов каждого слова в файле.
 4. Вывод строк файла в обратном порядке.
 5. Вывод строк файла по индексу.
(класс с методами реализовывать не нужно, важно научиться обрабатывать строки из файла).
 */

public class WorkWithText {

    public static void main(String[] args)
    {

        try
        {
            String path = "resource/text";
            FileReader reader = new FileReader(path);
            Scanner scanner = new Scanner(reader);
            HashSet<String> words = new HashSet<>();
            while(scanner.hasNext())
            {
                words.add(scanner.next());
            }
            TreeSet<String> uniqueWords = new TreeSet<>(new ComparatorImp());
            words.stream().forEach(word -> uniqueWords.add(word));
            System.out.println("Количество уникальных слов:" + uniqueWords.size() + "\n" + "Сортировка уникальных слов:" + "\n"+
              uniqueWords);
            reader.close();
            
            reader = new FileReader(path);
            scanner = new Scanner(reader);
            String word;
            HashMap<String, Integer> countedWords = new HashMap<>();
            while(scanner.hasNext())
            {
                word = scanner.next();
                if(countedWords.containsKey(word))
                {
                    Integer amount = countedWords.get(word).intValue();
                    countedWords.put(word, ++amount);
                }
                else
                {
                    countedWords.put(word, 1);
                }
            }
            System.out.println("\n"+ "Подсчет слов в файле:" + "\n" + countedWords);
            reader.close();

            reader = new FileReader(path);
            scanner = new Scanner(reader);
            ArrayList<String> lines = new ArrayList<>();
            while(scanner.hasNext())
            {
                word = scanner.nextLine();
                lines.add(word);
            }
            ArrayList<String> linesByFile = new ArrayList<>(lines);
            Collections.reverse(lines);
            System.out.println("\n"+ "Все строки файла в обратном порядке:");
            lines.stream().forEach(System.out::println);

            scanner = new Scanner(System.in);
            ArrayList<Integer> indexes = new ArrayList<>();
            System.out.println("\n" + "Введите индексы строк файла до " + linesByFile.size() + " >");
            word = scanner.nextLine();
            String[] splitIndexes = word.split(" ");
            for(int i = 0; i < splitIndexes.length; ++i)
            {
                indexes.add(Integer.parseInt(splitIndexes[i]));
            }
            System.out.println("\n"+ "Строки под индексами:");
            for(int index : indexes)
            {
                System.out.println(index + ". " + linesByFile.get(index));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static class ComparatorImp implements Comparator<String>
    {
        @Override
        public int compare(String firstValue, String secondValue)
        {
            if(firstValue.length() == secondValue.length())
            {
                for(int i = 0; i < secondValue.length(); ++i)
                {
                    if(firstValue.charAt(i) > secondValue.charAt(i))
                        return 1;
                    else
                        return -1;
                }
                return 0;
            }
            else
                return firstValue.length() > secondValue.length()? 1: -1;
        }
    }

}

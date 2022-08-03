package Converters;

/*
Задача: реализовать простую иерархию классов конвейеров для
преобразования температур из шкалы Цельсия в другие.
 */

public class Main
{
		public static void main(String[] args)
		{
				Converter kelvinConverter = Converter.getInstance('k');
				Converter fahrenheitConverter = Converter.getInstance('f');
				double temp = 100;
				System.out.println(fahrenheitConverter.convertValue(temp) + " F");
				System.out.println(kelvinConverter.convertValue(temp) + " K");
		}
}

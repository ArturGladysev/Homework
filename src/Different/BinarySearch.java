package Different;

import java.util.Scanner;


/* 1. Метод getSortArray возвращает отсортированный "пузырьком"
   массив заданного пользователем размера со случайными значениями < 1000.
   2. Метод testBinarySort ищет каждый элемент массива бинарным поиском и возвращает его индекс.
*/

public class BinarySearch
{
		public static void main(String[] args)
		{
				System.out.println("Введите размер массива >");
				Scanner scan = new Scanner(System.in);
				Integer[] array = getSortArray(scan.nextInt());
				for(int i = 0; i < array.length; ++i)
				{
						System.out.print("[" + i + "]" + array[i] + " ");
				}
				System.out.println();
				testBinarySort(array);
		}

		public static Integer[] getSortArray(int size)
		{
				Integer[] numbers = new Integer[size];
				for(int i = 0; i < size; ++i)
				{
						numbers[i] = (int)(Math.random() * 1000);
				}
				BubbleSort.sort(numbers);
				return numbers;
		}

		public static void testBinarySort(Integer[] numbers)
		{
				for(int value : numbers)
				{
						int result = binarySearch(numbers, value);
						System.out.println("Element:" + value + " >" + "Position:" + result);
				}
		}

		public static <T extends Comparable<? super T>> int binarySearch(T[] sortedArray, T targetValue)
		{
				int firstIndex = 0;
				int lastIndex = sortedArray.length - 1;
				while(firstIndex <= lastIndex)
				{
						int middleIndex = (firstIndex + lastIndex) / 2;
						T probableValue = sortedArray[middleIndex];
						int compareResult = probableValue.compareTo(targetValue);
						if(compareResult == 0)
								return middleIndex;
						else
								if(compareResult < 0)
										firstIndex = middleIndex + 1;
								else
										if(compareResult > 0)
												lastIndex = middleIndex - 1;
				}
				return -1;
		}
}


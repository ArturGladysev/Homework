package Different;

//Сортировка пузырьком

public class BubbleSort
{
    public static <T extends Comparable<? super T>> void sort(T[] array)
    {
        int compareResult;
        for(int i = 0; i < array.length - 1; ++i)
        {
            T firstValue = array[i];
            T secondValue = array[i + 1];
            compareResult = firstValue.compareTo(secondValue);
            if(compareResult > 0)
            {
                boolean keepOn = true;
                int result;
                for(int j = i + 1; keepOn == true && j > 0; --j)
                {
                    T oneValue = array[j - 1];
                    T twoValue = array[j];
                    result = oneValue.compareTo(twoValue);
                    if(result > 0)
                    {
                        array[j] = oneValue;
                        array[j - 1] = twoValue;
                        keepOn = true;
                    }
                    else
                        keepOn = false;
                }
            }
        }
    }
}

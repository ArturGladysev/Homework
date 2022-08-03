package MultiThread.SourceNumberFactorialCalculator;

import java.io.*;
import java.util.Scanner;

public class SourceNumberFactorialCalculator
{
    public static void calculateInSeparateTreads(String path)
    {
        Scanner scan = null;
        try
        {
            scan = new Scanner(new FileReader(path));
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        while(scan.hasNextInt())
        {
            int numb = scan.nextInt();
            CounterFactorial counter = new CounterFactorial(numb);
            Thread thread = new Thread(counter);
            thread.start();
        }
    }
}



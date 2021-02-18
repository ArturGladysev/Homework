package MultiThread;

import java.io.*;
import java.util.Scanner;


public class FactorialRunnable {

public static void countFactorial(String f) {
    FileReader  ff = null;
    try {
        ff = new FileReader(f);
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    Scanner scan  = new Scanner(ff);
       int numb = 0;
        while (scan.hasNextInt()) {
            numb = scan.nextInt();
            CounterFactorial counter = new CounterFactorial( numb);
            Thread thread = new Thread(counter);
            thread.start();
        }


}

}



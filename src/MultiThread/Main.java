package MultiThread;



import MultiThread.SourceNumberFactorialCalculator.SourceNumberFactorialCalculator;
import MultiThread.ThreadPool.FixedThreadPool;
import MultiThread.ThreadPool.MyRunnable;
import MultiThread.ThreadPool.ScalarThreadPool;

import java.util.*;

/* Данный класс объединяет в себе тестирование нескольких задач:
1. Реализация FixedThreadPool и ScalarThreadPool.
2. Реализация класса SourceNumberFactorialCalculator: класс принимает числа из
файла и рассчитывает для каждого из них факториал в отдельном потоке.
 */

public class Main
{
    public static void main(String[] args)
    {
        Runnable task1 = () -> {
            for(int i = 0; i < 10; ++i)
            {
                System.out.println("task1 print " + i);
                try
                {
                    Thread.sleep(1000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };
        Runnable task2 = () -> {
            for(char c = 'a'; c != 'z'+1; ++c)
            {
                System.out.println("task2 print " + c);
                try
                {
                    Thread.sleep(1000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };
        Runnable task3 = () -> {
            for(int i = 5; i < 50; i += 5)
            {
                System.out.println("task3 print " + i);
                try
                {
                    Thread.sleep(1000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };
        Runnable task4 = new Runnable()
        {
            String[] name = {"Alex", "Barbara", "Cindy", "Jon", "Eva"};

            @Override
            public void run()
            {
                for(int i = 0; i < name.length; ++i)
                {
                    System.out.println("task4 print " + name[i]);
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };

        System.out.println("FixedThreadPool - 1 , ScalarPoolThread - 2 , FactorialRunnable - 3" + "\n" + ":>");
        Scanner scan = new Scanner(System.in);
        int threadPoolVariantTest = scan.nextInt();
        switch(threadPoolVariantTest)
        {
            case 1:
            {
                FixedThreadPool fixedPool = new FixedThreadPool(3);
                fixedPool.start();
                fixedPool.execute(task1);
                fixedPool.execute(task3);
                fixedPool.execute(task2);
                fixedPool.execute(task4);
                try
                {
                    Thread.sleep(30000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                fixedPool.cancel();
                break;
            }
            case 2:
            {
                MyRunnable run1 = new MyRunnable(1);
                MyRunnable run2 = new MyRunnable(2);
                MyRunnable run3 = new MyRunnable(3);
                MyRunnable run5 = new MyRunnable(5);
                MyRunnable run11 = new MyRunnable(11);
                MyRunnable run500 = new MyRunnable(500);
                ScalarThreadPool scalarPoll = new ScalarThreadPool(2, 6);
                scalarPoll.start();
                scalarPoll.execute(run1);
                scalarPoll.execute(run2);
                try
                {
                    Thread.sleep(15000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                scalarPoll.execute(run3);
                scalarPoll.execute(run11);
                scalarPoll.execute(run5);
                scalarPoll.execute(run500);
                scalarPoll.execute(run1);
                scalarPoll.execute(run2);
                try
                {
                    Thread.sleep(3000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                scalarPoll.execute(run3);
                scalarPoll.execute(run500);
                scalarPoll.execute(run1);
                scalarPoll.execute(run2);
                scalarPoll.execute(run3);
                try
                {
                    Thread.sleep(5000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                scalarPoll.execute(new MyRunnable(500));
                scalarPoll.execute(new MyRunnable(1000));
                try
                {
                    Thread.sleep(10000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println("Отмена");
                scalarPoll.cancel();
                try
                {
                    Thread.sleep(30000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println("Отмена");
                scalarPoll.cancel();
                break;
            }
            case 3:
            {
                SourceNumberFactorialCalculator.calculateInSeparateTreads("resource/numbers");
                try
                {
                    Thread.sleep(10000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
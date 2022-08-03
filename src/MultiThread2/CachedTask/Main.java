package MultiThread2.CachedTask;


import MultiThread2.ExecutionManager.GetEntryException;
import java.util.concurrent.Callable;

public class Main
{
    public static void main(String[] args)
    {
        Callable<String> getRandomName = () -> {
            String[] names = {"Andrey", "Barbara", "Cindy", "Djo", "Eva"};
            int random = (int)(Math.random() * 5);
            return names[random];
        };

        Callable<String> generateException = () -> {
            throw new RuntimeException();
        };

        CachedTask<String> callableTask = new CachedTask<String>(getRandomName);

        CachedTask<String> exceptionTask = new CachedTask<String>(generateException);

        for(int i = 0; i < 10; ++i)
        {
            Thread thread = new Thread(() -> {
                System.out.println("Поток " + Thread.currentThread().getName() + " в работе");
                String name = callableTask.get();
                System.out.println("Результат get = " + name);
            });
            thread.start();
        }
        try
        {
            Thread.sleep(3000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        for(int i = 0; i < 5; ++i)
        {
            Thread thread = new Thread(() -> {
                System.out.println("Поток " + Thread.currentThread().getName() + " в работе");
                String name = callableTask.get();
                System.out.println("Результат get = " + name);
            });
            thread.start();
        }

        System.out.println("/");

        try
        {
            Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
                if(throwable instanceof GetEntryException)
                {
                    System.out.println(throwable.getMessage());
                    throwable.printStackTrace();
                }
            });

            for(int i = 0; i < 5; ++i)
            {
                Thread thread = new Thread(() -> {
                    System.out.println("Поток " + Thread.currentThread().getName() + " в работе");
                    String name = exceptionTask.get();
                    System.out.println("Результат get = " + name);
                });
                thread.start();

            }
        }
        catch(GetEntryException e)
        {
            e.printStackTrace();
        }
    }
}

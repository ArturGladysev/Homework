package MultiThread2;

import java.util.concurrent.Callable;

public class Main {                          //Для вызова CallableTask

    public static void main(String[] args) {

        Callable<String> task = new Callable() {
            @Override
            public String call() throws Exception {
                String[] names = {"Andrey", "Barbara", "Cindy", "Djo", "Eva"};
                int random = (int) (Math.random() * 5);
                return names[random];
            }
        };
        Task<String> callableTask = new Task<String>(task);


        Callable<String> taskException = new Callable() {
            @Override
            public String call() throws Exception {
                throw new RuntimeException();
            }
        };
        Task<String> callableException = new Task<String>(taskException);

        for (int i = 0; i < 10; ++i) {
            Thread thread = new Thread(() -> {
                System.out.println("Поток " + Thread.currentThread().getName() + " в работе");
                String name = callableTask.get();
                System.out.println("Результат get = " + name);
            });
            thread.start();

        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 5; ++i) {
            Thread thread = new Thread(() -> {
                System.out.println("Поток " + Thread.currentThread().getName() + " в работе");
                String name = callableTask.get();
                System.out.println("Результат get = " + name);
            });
            thread.start();

        }
        System.out.println("////////////");



    try {
        System.out.println("////////////");
        for (int i = 0; i < 5; ++i) {
            Thread thread = new Thread(() -> {
                System.out.println("Поток " + Thread.currentThread().getName() + " в работе");
                String name = callableException.get();
                System.out.println("Результат get = " + name);
            });
            thread.start();
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    if  (e instanceof GetEntryException) {
                        System.out.println(e.getMessage());
                    e.printStackTrace();
                    }
                }
            });

        }
    }catch (GetEntryException e) {e.printStackTrace();}
    }
}

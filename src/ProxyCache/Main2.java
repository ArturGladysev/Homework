package ProxyCache;

import java.util.concurrent.*;


public class Main2 {
   //Тестирование CachedInvocationHendlerConcurrent. Потоки запускаются одновременно и пытаются добавить одинаковые значения , в первом
 // случае в память, во втором в файл, но блокировка позволяет выполнять добавление последовательно

    public static void main(String[] args) {
        SumOfNumberImp sni = new SumOfNumberImp();
        SumOfNumber snimp = CachedInvocationHendlerConcurrent.getProxyInstance(sni, "E:/Artyr/studyjava/ProjectsServer/resourse/m1");

        MinImp m12 = new MinImp();
        Min min12 = CachedInvocationHendlerConcurrent.getProxyInstance(m12 , "E:/Artyr/studyjava/ProjectsServer/resourse/m1");

        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; ++i) {
                        System.out.println(snimp.sumOfNumber(10, i) + " - task1 " + Thread.currentThread().getName());
                try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        Runnable task2 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; ++i) {
                        System.out.println(snimp.sumOfNumber(10, i) + " - task2 " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        Runnable task3 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; ++i) {
                        System.out.println(snimp.sumOfNumber(10, i) + " - task3 " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        Runnable task4 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; ++i) {
                    System.out.println(min12.min(10, i) + " - task4 " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        Runnable task5 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; ++i) {
                    System.out.println(min12.min(10, i) + " - task5 " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(5);
        pool.submit(task4);
        pool.submit(task5);
         pool.submit(task1);
         pool.submit(task2);
        pool.submit(task3);

    }
}

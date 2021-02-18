package MultiThread;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; ++i) {
                    System.out.println("task1 print " + i);
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
                for (char c = 'a'; c < 'z'; ++c) {
                    System.out.println("task2 print " + c);
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
                for (int i = 5; i < 50; i += 5) {
                    System.out.println("task3 print " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Runnable task4 = new Runnable() {
            String[] name = {"Alex", "Barbara", "Cindy", "Djon", "Eva"};

            @Override
            public void run() {
                for (int i = 0; i < name.length; ++i) {
                    System.out.println("task4 print " + name[i]);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };


        System.out.println("FixedThreadPool - 1 , ScallarPoolThread - 2 , FactorialRunnable - 3");
        System.out.println(":>");
        Scanner scan = new Scanner(System.in);
        int sw = scan.nextInt();
        if (sw == 1) {
            FixedThreadPool fixedPool = new FixedThreadPool(3);
            fixedPool.start();
            fixedPool.execute(task1);
            fixedPool.execute(task3);
            fixedPool.execute(task2);
            fixedPool.execute(task4);
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            fixedPool.cancel();

        } else if (sw == 2) {
            MyRunnable run1 = new MyRunnable(1);
            MyRunnable run2 = new MyRunnable(2);
            MyRunnable run3 = new MyRunnable(3);
            MyRunnable run5 = new MyRunnable(5);
            MyRunnable run11 = new MyRunnable(11);
            MyRunnable run500 = new MyRunnable(500);
            ScallarPoolThread scallarPoll = new ScallarPoolThread(2, 6);
            scallarPoll.start();
            scallarPoll.execute(run1);
            scallarPoll.execute(run2);
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            scallarPoll.execute(run3);
            scallarPoll.execute(run11);
            scallarPoll.execute(run5);
            scallarPoll.execute(run500);
            scallarPoll.execute(run1);
            scallarPoll.execute(run2);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            scallarPoll.execute(run3);
            scallarPoll.execute(run500);
            scallarPoll.execute(run1);
            scallarPoll.execute(run2);
            scallarPoll.execute(run3);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            scallarPoll.execute(new MyRunnable(500));
            scallarPoll.execute(new MyRunnable(1000));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Отмена");
            scallarPoll.cancel();
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Отмена");
                scallarPoll.cancel();

        } else if (sw == 3) {
         FactorialRunnable.countFactorial("resourse/numbers");
try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
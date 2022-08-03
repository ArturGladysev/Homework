package MultiThread.ThreadPool;


import java.util.*;

/* Класс является отдаленным подобием ExecutorService фиксированного размера.
   Создается пул потоков threadData, поток запускается и входит в цикл, ожидая новую задачу
   из списка задач tasks, до того момента, пока не будет прерван.
*/

public class FixedThreadPool extends ThreadPool
{

    public class PoolThread extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                System.out.println("Thread running " + Thread.currentThread().getName());
                Runnable workRunnable;
                while(!Thread.currentThread().isInterrupted())
                {
                    workRunnable = getRunnable();
                    workRunnable.run();
                    Thread.sleep(2000);
                }
            }
            catch(InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            System.out.println("Поток пула" + Thread.currentThread().getName() + " завершил работу");
        }
    }

    public Runnable getRunnable() throws InterruptedException
    {
        Runnable runnable = null;
        boolean isGet = false;
        while(!isGet)
        {
            if(tasks.size() != 0 && isRun == true)
            {
                synchronized(tasks)
                {
                    if(tasks.size() != 0 && isRun == true)
                    {
                        runnable = tasks.getFirst();
                        tasks.removeFirst();
                        isGet = true;
                    }
                }
            }
            if(!isRun)
            {
                throw new InterruptedException();
            }
        }
        return runnable;
    }


    public FixedThreadPool(int size)
    {
        threadData = new ArrayList<>();
        for(int i = 0; i < size; ++i)
        {
            threadData.add(new PoolThread());
        }
    }

    @Override
    public void start()
    {
        for(Thread thread : threadData)
        {
            thread.start();
        }
    }

    @Override
    public void execute(Runnable runnable)
    {
        synchronized(tasks)
        {
            tasks.add(runnable);
        }
    }

    /* Метод отменяет все потоки: если поток выполняет задачу,
       то он закончит работу только по завершению задачи. */
    public void cancel()
    {
        isRun = false;
    }
}


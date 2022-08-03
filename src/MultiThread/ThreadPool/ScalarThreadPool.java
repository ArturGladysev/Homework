package MultiThread.ThreadPool;

import java.util.*;

/* Пул потоков с производным количеством потоков.
   Создается список потоков заданного размера. Если не будет задач в очереди,
   кол-во рабочих потоков будет постепенно сокращаться до минимального.
   В методе execute происходит добавление задачи в список задач, если все потоки заняты (флаг isTask), то
   один из отмененных потоков возвращается в работу.
   Класс PoolThread использует переменную isСancel для отмены, если поток был отменен, он предоставляет
   возможность поработать другим потокам, выходит во внешний цикл и проверяет - не нужно ли ему вернуться в работу.
 */

public class ScalarThreadPool extends ThreadPool
{
    private volatile int minSize;

    private volatile int maxSize;

    private volatile int workThreadSize;

    public class PoolThread extends Thread
    {
        public volatile boolean isCancel = false;

        public volatile boolean taskAtWork = false;

        @Override
        public void run()
        {
            System.out.println("Thread running " + Thread.currentThread().getName());
            Runnable workRunnable;
            try
            {
                while(!Thread.currentThread().isInterrupted() && isRun != false)
                {
                    while(!isCancel)
                    {
                        synchronized(this)
                        {
                            workRunnable = getRunnable();
                            if(workRunnable == null)
                            {
                                isCancel = true;
                                break;
                            }
                            taskAtWork = true;
                        }
                        workRunnable.run();
                        taskAtWork = false;
                        Thread.sleep(2000);
                    }
                    Thread.yield();
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
            System.out.println("Поток пула " + Thread.currentThread().getName() + " завершил работу");
        }
    }

    public ScalarThreadPool(int min, int max)
    {
        threadData = new ArrayList<>();
        for(int i = 0; i < max; ++i)
        {
            threadData.add(new PoolThread());
        }
        this.minSize = min;
        this.maxSize = max;
        this.workThreadSize = max;
    }

    @Override
    public void start()
    {
        for(Thread poolThread : threadData)
        {
            poolThread.start();
        }
    }

    @Override
    public void execute(Runnable runnable)
    {
        synchronized(tasks)
        {
            tasks.add(runnable);
        }
        if(workThreadSize < maxSize)
        {
            synchronized(threadData)
            {
                if(workThreadSize < maxSize)
                {
                    int threadInWork = 0;
                    for(int i = 0; i < maxSize; ++i)
                    {
                        PoolThread poolThread = (PoolThread)threadData.get(i);
                        if(poolThread.taskAtWork == true)
                        ++threadInWork;
                    }
                    System.out.println(threadInWork + " - Фактическое кол-во потоков в работе/Кол-во потоков в пуле - " + workThreadSize);
                    if(threadInWork == workThreadSize)
                    {
                        PoolThread poolThread = (PoolThread)threadData.get(0);
                        int index = 1;
                        while(poolThread.isCancel != true)
                        {
                            poolThread = (PoolThread)threadData.get(index);
                            ++index;
                        }
                        poolThread.isCancel = false;
                        ++workThreadSize;
                        System.out.println("Поток добавлен в execute");
                    }
                }
            }
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
            /*Отмена потока происходит в случае, если нет задач в списке, а все неотмененные потоки свободны при
             условии, что размер неотмененных потоков больше минимального*/
            else
                if(workThreadSize > minSize)
                {
                    synchronized(threadData)
                    {
                        if(workThreadSize > minSize)
                        {
                            int notWorkingThread = 0;
                            for(int i = 0; i < workThreadSize; ++i)
                            {
                                PoolThread poolThread = (PoolThread)threadData.get(i);
                                if(poolThread.taskAtWork == false)
                                {
                                    ++notWorkingThread;
                                }
                            }
                            if(notWorkingThread == workThreadSize)
                            {
                                --workThreadSize;
                                System.out.println("Поток отменен в getRunnable");
                                return null;
                            }
                        }
                    }
                }
                else
                    if(!isRun)
                    {
                        throw new InterruptedException();
                    }
        }
        return runnable;
    }

    //Отменяет потоки если кол-во потоков в работе равно минимальному
    public void cancel()
    {
        if(workThreadSize == minSize)
        {
            isRun = false;
        }
    }
}


package MultiThread2.ExecutionManager;

import java.util.List;


/* В данном классе создается поток для callback, который в фоновом режиме ожидает
   завершения всех остальных задач и потом выполняется
 */

public class ContextImp implements Context
{
    private List<StatusFutureTask> listFutureTask;

    int callbackIsDone = 0;

    public ContextImp(List<StatusFutureTask> listFutureTask)
    {
        this.listFutureTask = listFutureTask;
        OuterRunnable outRunnable = listFutureTask.get(listFutureTask.size() - 1).getOutRunnable();
        this.listFutureTask.remove(listFutureTask.size() - 1);
        OuterRunnable callback = new OuterRunnable(outRunnable)
        {
            boolean isRun = true;

            @Override
            public void run()
            {
                while(isRun)
                {
                    if(ContextImp.this.isFinishedTasks() == true)
                    {
                        System.out.println("Поток callback начал выполнение");
                        outRunnable.run();
                        callbackIsDone += 1;
                        System.out.println("Поток callback завершился");
                        isRun = false;
                    }
                }
                Thread.yield();
            }
        };
        Thread t = new Thread(callback);
        t.start();
    }

    //Подсчет исключений
    @Override
    public int getFailedTaskCount()
    {
        int exceptions = 0;
        for(StatusFutureTask myFutureTask : listFutureTask)
        {
            if(myFutureTask.getOutRunnable().isException())
            {
                ++exceptions;
            }
        }
        return exceptions;
    }

    // Подсчет выполненных задач
    @Override
    public int getCompletedTaskCount()
    {
        int completedTask = 0;
        for(StatusFutureTask myFutureTask : listFutureTask)
        {
            if(myFutureTask.getStateTask() != StatusFutureTask.StateTask.WORK)
            {
                if(myFutureTask.getStateTask() == StatusFutureTask.StateTask.COMPLETED)
                {
                    ++completedTask;
                }
            }
        }
        return completedTask + callbackIsDone;
    }

    //Подсчет отмененных задач
    @Override
    public int getInterruptedTaskCount()
    {
        int trueInterrupt = 0;
        for(StatusFutureTask myFutureTask : listFutureTask)
        {
            if(myFutureTask.isDone() == true)
            {
                if(myFutureTask.getStateTask() == StatusFutureTask.StateTask.CANCELED)
                {
                    ++trueInterrupt;
                }
            }
        }
        return trueInterrupt;
    }

    //Прерываем только те задачи, которые запущены
    @Override
    public void interrupt()
    {
        for(StatusFutureTask myFutureTask : listFutureTask)
        {
            if(myFutureTask.isDone() == false)
            {
                if(myFutureTask.getOutRunnable().isWork() == false)
                {
                    myFutureTask.cancel(false);
                }
            }
        }
    }

    private boolean isFinishedTasks()
    {
        for(StatusFutureTask myFutureTask : listFutureTask)
        {
            if(myFutureTask.isDone() != true)
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isFinished()
    {
        for(StatusFutureTask myFutureTask : listFutureTask)
        {
            if(myFutureTask.isDone() != true)
            {
                return false;
            }
        }
        if(callbackIsDone == 0)
        {
            return false;
        }
        return true;
    }
}
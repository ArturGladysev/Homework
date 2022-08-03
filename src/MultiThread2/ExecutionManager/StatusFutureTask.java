package MultiThread2.ExecutionManager;

import java.util.concurrent.FutureTask;

//Класс дает возможность отслеживания состояния потока

public class StatusFutureTask<T> extends FutureTask<T>
{
    public enum StateTask
    {
        NEW,
        COMPLETED,
        WORK,
        CANCELED,
    }

    private volatile StateTask stateTask = StateTask.NEW;

    public StateTask getStateTask()
    {
        return stateTask;
    }

    public OuterRunnable getOutRunnable()
    {
        return outRunnable;
    }

    private OuterRunnable outRunnable;

    public StatusFutureTask(OuterRunnable runnable, T result)
    {
        super(runnable, result);
        outRunnable = runnable;
    }

    //Проверка - отменено выполнение или нет и установка значения по результату
    @Override
    protected void done()
    {
        if(this.isCancelled() == false)
        {
            stateTask = StateTask.COMPLETED;
        }
        else
        {
            stateTask = StateTask.CANCELED;
        }
    }
}






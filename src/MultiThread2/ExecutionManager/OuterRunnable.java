package MultiThread2.ExecutionManager;

/*Класс-оболочка для Runnable, дающая возможность отследить - запустилась задача или нет,
 так как ExecuteService устанавливает статус потока NEW и не дает возможности проверить факт запуска.
 Класс хранит булеву переменную для возможности подсчета исключений.
 */
public class OuterRunnable implements Runnable
{
    private volatile boolean isException = false;

    public boolean isException()
    {
        return isException;
    }

    public boolean isWork()
    {
        return isWork;
    }

    private volatile boolean isWork = false;

    private Runnable task;

    public OuterRunnable(Runnable task)
    {
        this.task = task;
    }

    @Override
    public void run()
    {
        try
        {
            isWork = true;
            task.run();
        }
        catch(Exception e)
        {
            isException = true;
            e.printStackTrace();
            System.out.println("Исключение в потоке " + Thread.currentThread().getName());
        }
    }
}
package MultiThread2.ExecutionManager;

/* Задача: реализовать класс, наследуемый класс Context и переопределить методы
   для анализа задач в пуле потоков (отмененные, завершенные, прерванные вызовом исключения)
 */

public class Main
{
    public static void main(String[] args)
    {
        Runnable runnableException1 = () -> {
            throw new GetEntryException("Исключение в потоке " +
              Thread.currentThread().getName());
        };
        Runnable runnableException2 = () -> {
            throw new GetEntryException("Исключение в потоке " +
              Thread.currentThread().getName());
        };
        Runnable[] tasks = new Runnable[10];
        for(int i = 2; i < tasks.length; ++i)
        {
            tasks[i] = new PrintNumbers(10);
        }
        tasks[0] = runnableException1;
        tasks[1] = runnableException2;
        PrintNumbers callback = new PrintNumbers(11);
        ExecutionManager executionManager = new ExecutionManagerImp();
        Context context = executionManager.execute(callback, tasks);
        System.out.println("IsFinished " + context.isFinished());
        int finished = context.getCompletedTaskCount();
        System.out.println("завершенные " + finished);
        try
        {
            Thread.sleep(5000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        finished = context.getCompletedTaskCount();
        System.out.println("завершенные " + finished);
        context.interrupt();
        try
        {
            Thread.sleep(5000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        int interrupted = context.getInterruptedTaskCount();
        int completed = context.getCompletedTaskCount();
        System.out.println("IsFinished " + context.isFinished() + "\n" + " исключений: " + context.getFailedTaskCount()
          + " завершенные: " + completed + " отмененные: " + interrupted);
        try
        {
            Thread.sleep(10000);
        }
        catch(InterruptedException i)
        {
            i.printStackTrace();
        }
        interrupted = context.getInterruptedTaskCount();
        completed = context.getCompletedTaskCount();
        System.out.println("IsFinished " + context.isFinished() + "\n" + "исключений: " +
          context.getFailedTaskCount() + " завершенные: " + completed + " отмененные: " + interrupted);
    }
}
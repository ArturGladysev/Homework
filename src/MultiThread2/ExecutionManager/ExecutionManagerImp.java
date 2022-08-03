package MultiThread2.ExecutionManager;

import java.util.ArrayList;
import java.util.concurrent.*;

/* Класс оборачивает задачи в обертки, создает массив оберток,
   добавляет в конец списка задач callback (завершающую задачу) и вызывает выполнение задач.
 */

public class ExecutionManagerImp implements ExecutionManager
{
		private ExecutorService threadPool = Executors.newFixedThreadPool(3);

		private ArrayList<StatusFutureTask> futureTasks = new ArrayList<>();

    @Override
    public Context execute(Runnable callbackTask, Runnable... tasks)
    {
        OuterRunnable[] outerTasks = new OuterRunnable[tasks.length];
        for(int i = 0; i < tasks.length; ++i)
        {
            outerTasks[i] = new OuterRunnable(tasks[i]);
            StatusFutureTask futureTask = new StatusFutureTask(outerTasks[i], null);
            this.futureTasks.add(futureTask);
            threadPool.submit(futureTask);
        }
        OuterRunnable callback = new OuterRunnable(callbackTask);
        StatusFutureTask callbackFutureTask = new StatusFutureTask(callback, null);
        this.futureTasks.add(callbackFutureTask);
        return new ContextImp(this.futureTasks);
    }
}

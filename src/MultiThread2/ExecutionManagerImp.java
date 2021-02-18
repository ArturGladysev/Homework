package MultiThread2;

import java.util.ArrayList;
import java.util.concurrent.*;

public class ExecutionManagerImp implements ExecutionManager{
   private ExecutorService pool = Executors.newFixedThreadPool(3);
    private ArrayList<MyFutureTask> listTask = new ArrayList<>();

    //Класс использует ЭкзекьютСервис , оборачивает задачи в обертки, создает массив оберток и вызывает выполнение задач

    @Override
   public Context excute (Runnable callback, Runnable...tasks){


  OutRunnable[] tasksOut = new OutRunnable[tasks.length];

    for (int i = 0; i < tasks.length; ++i) {
        Runnable raunnable = tasks[i];
        tasksOut[i] = new OutRunnable(raunnable);
    }


    for (int i = 0; i < tasks.length; ++i) {

        MyFutureTask futureTask = new MyFutureTask(tasksOut[i], null);
        listTask.add(futureTask);
        pool.submit(futureTask);

    }

       OutRunnable callbackOut = new OutRunnable(callback);
        MyFutureTask futureTaskCallback = new MyFutureTask(callbackOut, null);
    listTask.add(futureTaskCallback);


    return new ContextImp(listTask);
    }


    }

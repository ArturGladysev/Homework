package MultiThread2;

import java.util.*;
import java.util.concurrent.*;

public class ContextImp implements Context {       //Хранится и обрабатывается лист ФьючерТасков
    private List<MyFutureTask> listFutureTask;
   int  callbackisDone = 0;


    public ContextImp(List<MyFutureTask> listFutureTask) {   //Создаем поток для callback, который в фоновом режиме ожидает завершения всех отсальных
        this.listFutureTask = listFutureTask;                        //задач и потом выполняется
this.listFutureTask.remove(listFutureTask.size()-1);
        OutRunnable outRunnable = listFutureTask.get(listFutureTask.size()-1).outRunnable;
OutRunnable callback = new OutRunnable(outRunnable) {
  boolean isRun = true;
    @Override
    public void run() {
      while(isRun) {
          if (ContextImp.this.isFinishedTasks() == true) {
              System.out.println("Поток callbak начал выполнение");
              outRunnable.run();
             callbackisDone +=1;
              System.out.println("Поток callbak завершился");
          isRun = false;
          }
      }
        Thread.yield();
    }
};
   Thread t = new Thread(callback);
t.start();
    }

     @Override
    public int getFailedTaskCount() {                            //Подсчет исключений
        int countExceptions = 0;
        for (MyFutureTask myFutureTask : listFutureTask) {
            if (myFutureTask.outRunnable.isException) {
                ++countExceptions;
            }
        }
        return countExceptions;
    }


    @Override
    public int getCompletedTaskCount() {                         // Подсчет выполненных задач
        int countComplete = 0;
        for (MyFutureTask myFutureTask : listFutureTask) {
            if (myFutureTask.getStateTask() != MyFutureTask.StateTask.WORK) {
                if (myFutureTask.getStateTask() == MyFutureTask.StateTask.COMPLETED) {
                    ++countComplete;
                }
            }
        }
        return countComplete + callbackisDone;
    }



    @Override
    public int getInterruptedTaskCount() {   //Подсчет отмененных задач
        int countInterraptTrue = 0;
        for (MyFutureTask myFutureTask : listFutureTask) {
            if (myFutureTask.isDone() == true) {
                if (myFutureTask.getStateTask() == MyFutureTask.StateTask.CANCELED) {
                    ++countInterraptTrue;
                }

            }
        }
        return countInterraptTrue;
    }

    @Override
    public void interrapt() {                          //Прерываем только те задачи, которые запущены
        for (MyFutureTask myFutureTask : listFutureTask) {
            if (myFutureTask.isDone() ==false) {
               if (myFutureTask.outRunnable.isWork == false) {
                   myFutureTask.cancel(false);
               }

               }
            }

        }

    private boolean isFinishedTasks() {
        for (MyFutureTask myFutureTask : listFutureTask) {
            if (myFutureTask.isDone() != true) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isFinished() {
        for (MyFutureTask myFutureTask : listFutureTask) {
            if (myFutureTask.isDone() != true) {
                return false;
            }
        }
        if (callbackisDone == 0) {return false;}
        return true;
    }
}
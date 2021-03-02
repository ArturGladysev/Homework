package MultiThread2;

import java.util.List;
//Хранится и обрабатывается лист ФьючерТасков
public class ContextImp implements Context {
    private List<MyFutureTask> listFutureTask;
   int  callbackisDone = 0;

    //Создаем поток для callback, который в фоновом режиме ожидает завершения всех отсальных
    //задач и потом выполняется

   public ContextImp(List<MyFutureTask> listFutureTask) {
        this.listFutureTask = listFutureTask;
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

    //Подсчет исключений
     @Override
    public int getFailedTaskCount() {
        int countExceptions = 0;
        for (MyFutureTask myFutureTask : listFutureTask) {
            if (myFutureTask.outRunnable.isException) {
                ++countExceptions;
            }
        }
        return countExceptions;
    }

    // Подсчет выполненных задач
    @Override
    public int getCompletedTaskCount() {
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


    //Подсчет отмененных задач
    @Override
    public int getInterruptedTaskCount() {
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

    //Прерываем только те задачи, которые запущены
    @Override
    public void interrapt() {
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
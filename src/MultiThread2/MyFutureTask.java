package MultiThread2;

import java.util.concurrent.FutureTask;
//Класс хранит состояния для отслеживания состояния потока
public class MyFutureTask<T> extends FutureTask<T> {

    public static enum StateTask {
        NEW,
        COMPLETED,
        WORK,
        CANCELED,

    }

    private volatile StateTask stateTask = StateTask.NEW;

    public StateTask getStateTask() {
        return stateTask;
    }

    //Обертка для задачи
   OutRunnable outRunnable;

    public MyFutureTask(OutRunnable runnable, T result) {
        super(runnable, result);
outRunnable =  runnable;
    }

    //Проверка - отменено выполнение или нет и установка значения по результату
    @Override
    protected void done() {

        if (this.isCancelled() == false) {
               stateTask = StateTask.COMPLETED;
           }
else { stateTask = StateTask.CANCELED;  }

    }



}






package MultiThread2;

import java.util.concurrent.FutureTask;

public class MyFutureTask<T> extends FutureTask<T> {

    public static enum StateTask {             //Класс хранит состояния для отслеживания состояния потока
        NEW,
        COMPLETED,
        WORK,
        CANCELED,

    }

    private volatile StateTask stateTask = StateTask.NEW;

    public StateTask getStateTask() {
        return stateTask;
    }


   OutRunnable outRunnable;                                      //Обертка для задачи

    public MyFutureTask(OutRunnable runnable, T result) {
        super(runnable, result);
outRunnable =  runnable;
    }

    @Override
    protected void done() {                        //Проверка - отменено выполнение или нет и установка значения по результату

        if (this.isCancelled() == false) {
               stateTask = StateTask.COMPLETED;
           }
else { stateTask = StateTask.CANCELED;  }

    }



}






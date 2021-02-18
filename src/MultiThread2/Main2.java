package MultiThread2;

public class Main2 {                             //Для запуска ExecutionManager
    public static void main(String[] args) {

    Runnable runnableException1 = () -> {throw new GetEntryException("Исключение в потоке " +
            Thread.currentThread().getName());};

        Runnable runnableException2 = () -> {throw new GetEntryException("Исключение в потоке " +
                Thread.currentThread().getName());};

 // Две задачи , вызывающие исключения, запускаются первыми, после, в массив добавляются задачи
     Runnable[] tasks= new Runnable[10];

  for(int i =2; i<tasks.length; ++i) {
      tasks[i] = new MyRunnable(10);
  }
  tasks[0] = runnableException1;
  tasks[1] = runnableException2;
  MyRunnable callback = new MyRunnable(11);

        ExecutionManager executionManager = new ExecutionManagerImp();
Context context = executionManager.excute(callback, tasks);
        System.out.println("IsFinished " + context.isFinished());
        int one = context.getCompletedTaskCount();
        System.out.println("завершенные "+one);

        try {
            Thread.sleep(5000);
       } catch (InterruptedException e) {
            e.printStackTrace(); }
        int one3 = context.getCompletedTaskCount();
        System.out.println("завершенные "+ one3);
        context.interrapt();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
      }

       int one5 = context.getInterruptedTaskCount();
        int one4 = context.getCompletedTaskCount();
        System.out.println("IsFinished " + context.isFinished());
        System.out.println("Исключений " + context.getFailedTaskCount());
        System.out.println("завершенные " +  one4 + " отмененные: " + one5);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException i) {
            i.printStackTrace();
        }
        int one6 = context.getInterruptedTaskCount();
        int one7 = context.getCompletedTaskCount();
        System.out.println("IsFinished " + context.isFinished());
        System.out.println("Исключений " + context.getFailedTaskCount());
        System.out.println("завершенные " +  one7 + " отмененные: " + one6);
    }


}
package MultiThread;


import java.util.*;

// Флаг для завершения работы пула - используется при отмене
//Лист пуловых потоков
//Лист задач
public class FixedThreadPool implements ThreadPool{

protected boolean isRun = true;
 protected volatile ArrayList<PoolThread> threadsData;
  protected   volatile LinkedList<Runnable> runnables = new LinkedList<>();

    // Класс для обработки задач, входит в цикл и ожидает новую задачу, пока
    //  не будет прерван
  public class PoolThread extends Thread {
      @Override
      public void run() {
       try {
          System.out.println("Thread running " + Thread.currentThread().getName());
        Runnable workRunnable;
          while(!Thread.currentThread().isInterrupted()){
               workRunnable = getRunnable();
               workRunnable.run();
               Thread.sleep(2000);
           }
              } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();

              } catch (Exception ex){
            ex.printStackTrace();

         }
          System.out.println("Поток пула"+ Thread.currentThread().getName() + " завершил работу");
          }

      }


    //Поток зацикливается до момента, пока не получит задачу из списка задач
  public Runnable getRunnable() throws InterruptedException {

      Runnable runnable = null;
      boolean isGet = false;
  while (!isGet) {
if (runnables.size() != 0 && isRun == true) {
    synchronized (runnables) {
        if (runnables.size() != 0 && isRun == true) {
            runnable = runnables.getFirst();
            runnables.removeFirst();
            isGet = true;

        }
    }
    }
   if (!isRun){
      throw new InterruptedException();
  }
  }
     return runnable;
  }


    //Создается список потоков фиксированного размера,
   public FixedThreadPool(int size) {
       threadsData = new ArrayList<PoolThread>();
 for(int i = 0; i<size; ++i) {
     threadsData.add( new PoolThread());
 }
   }

    // Запуск потоков
    @Override
    public void start(){
       for (Thread thread: threadsData) {
           thread.start();
         }

       }

    //Добавление задачи в список задач
    @Override
    public void execute(Runnable runnable) {
        synchronized (runnables) {
            runnables.add(runnable);
        }
   }
    //Отменяет все потоки, каждый поток закончит работу, только по заверршению задачи, если она
    //у него выполняется
    public void cancel () {
      isRun = false;
      }


}


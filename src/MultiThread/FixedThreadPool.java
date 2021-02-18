package MultiThread;


import java.util.*;


public class FixedThreadPool implements ThreadPool{

protected boolean isRun = true;                                            // Флаг для завершения работы пула - используется при отмене
 protected volatile ArrayList<PoolThread> threadsData;                      //Лист пуловых потоков
  protected   volatile LinkedList<Runnable> runnables = new LinkedList<>();            //Лист задач

  public class PoolThread extends Thread {                                 // Класс для обработки задач, входит в цикл и ожидает новую задачу, пока
      @Override                                                             //  не будет прерван
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



  public Runnable getRunnable() throws InterruptedException {                      //Поток зацикливается до момента, пока не получит задачу из списка задач

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



   public FixedThreadPool(int size) {                      //Создается список потоков фиксированного размера,
       threadsData = new ArrayList<PoolThread>();
 for(int i = 0; i<size; ++i) {
     threadsData.add( new PoolThread());
 }
   }


    @Override                                                  // Запуск потоков
    public void start(){
       for (Thread thread: threadsData) {
           thread.start();
         }

       }


    @Override
    public void execute(Runnable runnable) {                      //Добавление задачи в список задач
        synchronized (runnables) {
            runnables.add(runnable);
        }
   }

public void cancel () {                              //Отменяет все потоки, каждый поток закончит работу, только по заверршению задачи, если она
      isRun = false;                                  //у него выполняется
      }


}


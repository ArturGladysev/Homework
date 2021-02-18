package MultiThread;

import java.util.*;



public class ScallarPoolThread implements ThreadPool{

    protected volatile boolean isRun = true;
    protected volatile ArrayList<PoolThread> threadsData;
    protected   volatile LinkedList<Runnable> runnables = new LinkedList<>();
    private volatile int minSize;
    private volatile int maxSize;
    private volatile int workThreadSize;

    public class PoolThread extends Thread {        //Класс-поток использует переменную isСancel для отмены, выходит во внешний цикл и проверяет
        public volatile boolean isCancel = false;   // - не нужно ли ему выйти в работу, предоставляет возможность поработать другим потокам
       public volatile boolean isTask = false;
        @Override
        public void run() {
            System.out.println("Thread running " + Thread.currentThread().getName());
            Runnable workRunnable;
            try {
            while (!Thread.currentThread().isInterrupted() && isRun != false) {
                while (!isCancel) {
                  synchronized (this) {
                      workRunnable = getRunnable();             //При возвращении null, меняется значение флага отмены
                      if (workRunnable == null) {
                          isCancel = true;
                          break;
                      }
                      isTask = true;
                  }
                        workRunnable.run();
                      isTask = false;
                        Thread.sleep(2000);
                    }
                Thread.yield();
            }
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("Поток пула " + Thread.currentThread().getName() + " завершил работу");
        }
    }



    public ScallarPoolThread(int min, int max) {            //Создается список потоков максимального размера, если не будет задач в очереди,
        threadsData = new ArrayList<PoolThread>();          // кол-во рабочих потоков будет постепенно сокращаться до минимального.
        for(int i = 0; i<max; ++i) {
            threadsData.add(new PoolThread());
        }
        this.minSize = min;
        this.maxSize = max;
        this.workThreadSize = max;
    }


    @Override                                                   // Запуск потоков
    public void start() {
        for (PoolThread poolThread : threadsData) {
            poolThread.start();
        }

    }


   @Override
    public void execute(Runnable runnable) {        //Добавление задачи в список задач, если все потоки заняты - флаг isTask,то возаращается
                                                           // один из отмененных потоков в работу
       synchronized (runnables) {
           runnables.add(runnable);
       }
       if (workThreadSize < maxSize) {
           synchronized (threadsData) {
              if (workThreadSize < maxSize) {
                  int  countInWork = 0;
                  for (int i = 0; i < maxSize; ++i) {
                      PoolThread poolThread = threadsData.get(i);
                     boolean b = poolThread.isTask;
                     if (b == true) {
                          ++countInWork;
                      }
                  }
                  System.out.println(countInWork +"- Фактиеское кол-во тредов в работе/Кол-во тредов в пуле "+ workThreadSize);

                  if (countInWork == workThreadSize) {
                      PoolThread poolThread = threadsData.get(0);
                     int j = 1;
                      while(poolThread.isCancel != true) {
                           poolThread = threadsData.get(j);
                   ++j;
                      }
                      poolThread.isCancel = false;
                      ++workThreadSize;
                      System.out.println("Поток добавлен в execute");
                  }
              }
           }
       }
    }


        public Runnable getRunnable() throws InterruptedException {
            Runnable runnable = null;
            boolean isGet = false;
            while (!isGet) {                                           //Пока установлен флаг isGet, каждый поток будет остатьваться в методе и
                if (runnables.size() != 0 && isRun == true) {           // ждать очереди
                    synchronized (runnables) {                            //  минимум потоков - minSize"
                        if (runnables.size() != 0 && isRun == true) {
                            runnable = runnables.getFirst();
                            runnables.removeFirst();                            //Первая секция добавляет задачу из очереди в поток,если потоки все еще не должны завершать работу
                            isGet = true;                                  // и есть задача для добавления
                        }
                   }
                }
                else if(workThreadSize > minSize) {                      //Отменяет поток, если нет задач и все не отмененные потоки свободны при
                    synchronized (threadsData) {                          // условии, что размер не отмененных потоков больше минимального
                        if (workThreadSize > minSize) {
                            int countDontWorkThread = 0;
                            for (int i = 0; i < workThreadSize; ++i) {
                                PoolThread poolThread = threadsData.get(i);
                                if (poolThread.isTask == false) {
                                    ++countDontWorkThread;
                                }
                            }
                            if (countDontWorkThread == workThreadSize) {
                                System.out.println("Поток отменен в getRunnable");
                                --workThreadSize;
                                return null;
                            }
                        }
                    }
                }
                else if (!isRun){
                throw new InterruptedException();
                }
            }
            return runnable;
        }



        public void cancel () {                //Отменяет потоки если кол-во потоков в работе равно миниимальному
        if(workThreadSize == minSize) {
            isRun = false;
        }
        }



}


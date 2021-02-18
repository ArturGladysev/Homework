package MultiThread2;

public class OutRunnable implements Runnable {//Оболочка для таска, дающая возможность отследить - запустился таск или нет
    public  volatile boolean isException = false;        //Так как ЭкзекьютСервис устанавливает статус потока - NEW
    public  volatile boolean isWork = false;             // Так же хранит булеву переменную для возможности подсчета исключений
    private Runnable innerRun;


    public OutRunnable(Runnable innerRun) {
        this.innerRun = innerRun;
    }

    @Override
    public void run() {

        try {
            isWork = true;
            innerRun.run();
        } catch (Exception e) {
            isException = true;
            e.printStackTrace();
            System.out.println("Исключение в потоке"+ Thread.currentThread().getName());
        }
    }

}
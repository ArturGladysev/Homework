package MultiThread2;

//Оболочка для таска, дающая возможность отследить - запустился таск или нет
//Так как ЭкзекьютСервис устанавливает статус потока - NEW
// Так же хранит булеву переменную для возможности подсчета исключений
public class OutRunnable implements Runnable {
    public  volatile boolean isException = false;
    public  volatile boolean isWork = false;
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
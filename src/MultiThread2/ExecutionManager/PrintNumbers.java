package MultiThread2.ExecutionManager;

public class PrintNumbers implements Runnable {
    private int number;

    PrintNumbers(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        int sum = 0;
        for (int i = 0; i < 5; ++i) {
            sum += number;
            System.out.println("task" + Thread.currentThread().getName() + "(" + number + ") print " + sum);
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
                e.printStackTrace();
           }
        }
    }

}

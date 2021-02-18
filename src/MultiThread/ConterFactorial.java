package MultiThread;

class CounterFactorial implements Runnable {
    private int number;
    public CounterFactorial(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        int res = 1;
        for (int i = 1; i <= number; i++) {
            res = res * i;
        }
        System.out.println(Thread.currentThread().getName() + " factorial of " + number + " ="+ res);;
    }

}
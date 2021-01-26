package ProxyCache;

public class SumOfNumberImp implements SumOfNumber {

   @Override
    public int sumOfNumber(int one, int two) {
       System.out.println("Метод sumOfNumber(Class.Integer , Class.Integer)");
       try {
        Thread.sleep(4000);
    } catch(
    InterruptedException e)
    {
        e.printStackTrace();
    }
            return one +two;
}

  @Override
   public int sumOfNumber(int one, double two) {
      System.out.println("Метод sumOfNumber(Class.Integer , Class.Double)");
       try {
           Thread.sleep(4000);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       return one + (int)two;
   }

}

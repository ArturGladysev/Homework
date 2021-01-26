package ProxyCache;

public interface SumOfNumber {
  @Cache( maxSizeCacheValues = 6)
    int sumOfNumber(int one, int two);

  @Cache
  @ValidArgsUse(twoPar  = 2)
  int sumOfNumber(int one, double two);

  @Cache
  default Integer printNull() {
     Integer n = null;
      return  n;
  }

}

package CachedInvocationHandler.TestClasses;

import CachedInvocationHandler.ArgumentsIgnore;
import CachedInvocationHandler.Cache;

public interface Accumulator
{
    @Cache//(isSerializable = true)
    int addUp(int one, int two);

    @Cache//(isSerializable = true)
    @ArgumentsIgnore(twoArgument = false)
    int addUp(int one, double two);

    @Cache
    default Integer printNull()
    {
        Integer n = null;
        return n;
  }

}

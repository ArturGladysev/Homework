package CachedInvocationHandler.TestClasses;

import CachedInvocationHandler.ArgumentsIgnore;
import CachedInvocationHandler.Cache;

public interface StringConnector
{

    @Cache(isSerializable = true, fileName = "resource/cacheFiles/file")
    String concat(String one, String two);

    @Cache(isSerializable = true, fileName = "resource/cacheFiles/file")
    @ArgumentsIgnore(oneArgument = false, threeArgument = false)
    String concat(String one, String two, String three);
    default  void printNull() {
    System.out.println("Zero");
}
}

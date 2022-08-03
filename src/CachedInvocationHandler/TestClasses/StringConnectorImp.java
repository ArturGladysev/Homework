package CachedInvocationHandler.TestClasses;


public class StringConnectorImp implements StringConnector
{
    @Override
    public String concat(String one, String two)
    {
        System.out.println("Метод concat(Class.String , Class.String)");
        try
        {
            Thread.sleep(4000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        return one + two;
    }


    @Override
    public String concat(String one, String two, String three)
    {
        System.out.println("Метод concat(Class.String , Class.String , Class.String)");
        try
        {
            Thread.sleep(4000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        return one + two + three;
    }
}

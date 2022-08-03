package CachedInvocationHandler;

import CachedInvocationHandler.TestClasses.Accumulator;
import CachedInvocationHandler.TestClasses.AccumulatorImp;
import CachedInvocationHandler.TestClasses.StringConnector;
import CachedInvocationHandler.TestClasses.StringConnectorImp;
import org.junit.Test;

public class Main
{
    //Хэш значения хранятся в памяти
    @Test
    public void testing1()
    {
        AccumulatorImp accumulatorImp = new AccumulatorImp();
        Accumulator proxyAccumulator = CachedInvocationHandler.getProxyInstance(accumulatorImp);
        System.out.println(proxyAccumulator.addUp(10, 10));
        System.out.println(proxyAccumulator.addUp(200, 10));
        System.out.println(proxyAccumulator.addUp(10, 200));
        System.out.println(proxyAccumulator.addUp(10, 10));
        System.out.println(proxyAccumulator.addUp(10, 10));
        System.out.println(proxyAccumulator.addUp(10, 10));
        System.out.println(proxyAccumulator.addUp(10, 10));
        System.out.println(proxyAccumulator.addUp(40, 40));
        System.out.println(proxyAccumulator.printNull());
        System.out.println(proxyAccumulator.addUp(10, 10f));
        System.out.println(proxyAccumulator.printNull());
    }

    //Кэш значения хранятся в файле
    @Test
    public void testing2()
    {
        StringConnectorImp stringConnectorImp = new StringConnectorImp();
        StringConnector proxyStringConnector = CachedInvocationHandler.getProxyInstance(stringConnectorImp);
        System.out.println(proxyStringConnector.concat("one", "two", "three"));
        System.out.println(proxyStringConnector.concat("one", "two", "three"));
        proxyStringConnector.printNull();
        System.out.println(proxyStringConnector.concat("free", "four"));
        System.out.println(proxyStringConnector.concat("one", "two", "three"));
        System.out.println(proxyStringConnector.concat("free", "four"));
    }

    //Игнорирование второго аргумента при определении уникальности
    @Test
    public void testing3()
    {
        AccumulatorImp accumulatorImp = new AccumulatorImp();
        Accumulator proxyAccumulator = CachedInvocationHandler.getProxyInstance(accumulatorImp);
        System.out.println(proxyAccumulator.addUp(60, 40f));
        System.out.println(proxyAccumulator.addUp(60, 40f));
    }

    /* 1. Если в памяти допустимо хранить только 6 кэш значений, новые значения не добавляются,
       2. Проверка на возможность хранить/возвращать null значения.
       3 Для обоих методов указан один путь к файлу, но смена потока вывода не происходит: проверка на корректность записи\чтения.
       (Необходимо указать в кэш-аннотации для данных методов - isSerializable = true) */
    @Test
    public void testing4()
    {
        AccumulatorImp accumulatorImp = new AccumulatorImp();
        Accumulator proxyAccumulator = CachedInvocationHandler.getProxyInstance(accumulatorImp);
        System.out.println(proxyAccumulator.printNull());
        System.out.println(proxyAccumulator.printNull());
        System.out.println(proxyAccumulator.printNull());
        System.out.println(proxyAccumulator.addUp(10, 10));
        System.out.println(proxyAccumulator.addUp(20, 10));
        System.out.println(proxyAccumulator.addUp(0, 0));
        System.out.println(proxyAccumulator.addUp(30, 200));
        System.out.println(proxyAccumulator.addUp(40, 10));
        System.out.println(proxyAccumulator.printNull());
        System.out.println(proxyAccumulator.addUp(40, 70));
        System.out.println(proxyAccumulator.addUp(50, 70));
        System.out.println(proxyAccumulator.addUp(50, 10));
        System.out.println(proxyAccumulator.addUp(50, 10));
        System.out.println(proxyAccumulator.addUp(50, 10));
        System.out.println(proxyAccumulator.addUp(30, 200));
        System.out.println(proxyAccumulator.addUp(0, 0));
    }
}


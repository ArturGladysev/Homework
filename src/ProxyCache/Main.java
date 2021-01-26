package ProxyCache;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;




public class Main {

    @Test
    public void testing() {
        SumOfNumberImp s = new SumOfNumberImp();                                       //Хэш значения хранятся в памяти
        SumOfNumber sumOfNumberImp = CachedInvocationHendler.getProxyInstance(s);
        System.out.println(sumOfNumberImp.sumOfNumber(10, 10));
        System.out.println(sumOfNumberImp.sumOfNumber(200, 10));
        System.out.println(sumOfNumberImp.sumOfNumber(10, 200));
        System.out.println(sumOfNumberImp.sumOfNumber(10, 10));
        System.out.println(sumOfNumberImp.sumOfNumber(10, 10));
        System.out.println(sumOfNumberImp.sumOfNumber(10, 10));
        System.out.println(sumOfNumberImp.sumOfNumber(10, 10));
        System.out.println(sumOfNumberImp.sumOfNumber(40, 40));
        System.out.println(sumOfNumberImp.printNull());
        System.out.println(sumOfNumberImp.sumOfNumber(10, 10f));
    }
    // Метод concat

    @Test
    public void testing2() {
        ConcatImp concatImp = new ConcatImp();                                   //Кэш значния хранятся в файле
        Concat concat = CachedInvocationHendler.getProxyInstance(concatImp);
        System.out.println(concat.concat("one", "two", "three"));
        System.out.println(concat.concat("one", "two", "three"));
        System.out.println(concat.concat("free", "four"));
        concat.printNull();

        System.out.println(concat.concat("one", "two", "three"));
        System.out.println(concat.concat("free", "four"));
    }

    @Test
    public void testing3() {                                                      //Игнорирование второго аргумента при определении уникальности

        SumOfNumberImp sv = new SumOfNumberImp();
        SumOfNumber sumOfNumberImp2 = CachedInvocationHendler.getProxyInstance(sv);
        System.out.println(sumOfNumberImp2.sumOfNumber(60, 40f));
        System.out.println(sumOfNumberImp2.sumOfNumber(60, 40f));

    }

    @Test
    public void testing4() {                                                       //Устновление допустимого размера для значения Листа в кэше
        CreateListImp sv2 = new CreateListImp();
        CreateList createlist = CachedInvocationHendler.getProxyInstance(sv2);
        System.out.println(createlist.createList());
        System.out.println(createlist.createList());

    }
    @Test
    public void testing5() {
        SumOfNumberImp s5 = new SumOfNumberImp();                                      //1.Если в памяти допустимо хранить только 6 кэш значний,
        SumOfNumber sumOfNumberImp5 = CachedInvocationHendler.getProxyInstance(s5);    // новые значения не добавляются, 2.Проверка на возможность
        System.out.println(sumOfNumberImp5.printNull());                               // хранить/возвращать null значения. 3 Для обоих методов указан
        System.out.println(sumOfNumberImp5.printNull());                             // один путь к файлу, проверка на корректность записи\чтения.
        System.out.println(sumOfNumberImp5.printNull());          // (Необходимо указать в кэш-анннотации для данных методов - isSerializable = true)
        System.out.println(sumOfNumberImp5.sumOfNumber(10, 10));
        System.out.println(sumOfNumberImp5.sumOfNumber(20, 10));
        System.out.println(sumOfNumberImp5.sumOfNumber(0, 0));
        System.out.println(sumOfNumberImp5.sumOfNumber(30, 200));
        System.out.println(sumOfNumberImp5.sumOfNumber(40, 10));
        System.out.println(sumOfNumberImp5.printNull());
        System.out.println(sumOfNumberImp5.sumOfNumber(40, 70));
        System.out.println(sumOfNumberImp5.sumOfNumber(50, 70));
        System.out.println(sumOfNumberImp5.sumOfNumber(50, 10));
        System.out.println(sumOfNumberImp5.sumOfNumber(50, 10));
        System.out.println(sumOfNumberImp5.sumOfNumber(50, 10));
        System.out.println(sumOfNumberImp5.sumOfNumber(30, 200));
        System.out.println(sumOfNumberImp5.sumOfNumber(0, 0));
    }


}




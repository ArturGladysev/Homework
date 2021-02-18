package ProxyCache;
import java.lang.reflect.*;


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
        System.out.println(sumOfNumberImp.printNull());
    }


    @Test
    public void testing2() {
        ConcatImp concatImp = new ConcatImp();                                            //Кэш значния хранятся в файле
        Concat concat = CachedInvocationHendler.getProxyInstance(concatImp);
        System.out.println(concat.concat("one", "two", "three"));
        System.out.println(concat.concat("one", "two", "three"));
        concat.printNull();
        System.out.println(concat.concat("free", "four"));
        System.out.println(concat.concat("one", "two", "three"));
        System.out.println(concat.concat("free", "four"));
    }

    @Test
    public void testing3() {                                                   //Игнорирование второго аргумента при определении уникальности

        SumOfNumberImp sv = new SumOfNumberImp();
        SumOfNumber sumOfNumberImp2 = CachedInvocationHendler.getProxyInstance(sv);
        System.out.println(sumOfNumberImp2.sumOfNumber(60, 40f));
        System.out.println(sumOfNumberImp2.sumOfNumber(60, 40f));

    }


    @Test
    public void testing5() {
        SumOfNumberImp s5 = new SumOfNumberImp();                                      //1.Если в памяти допустимо хранить только 6 кэш значний,
        SumOfNumber sumOfNumberImp5 = CachedInvocationHendler.getProxyInstance(s5);    // новые значения не добавляются, 2.Проверка на возможность
        System.out.println(sumOfNumberImp5.printNull());                               // хранить/возвращать null значения. 3 Для обоих методов указан
        System.out.println(sumOfNumberImp5.printNull());                             // один путь к файлу, но переинсталяция потока вывода не происходит, проверка на корректность записи\чтения.
        System.out.println(sumOfNumberImp5.printNull());                           // (Необходимо указать в кэш-анннотации для данных методов - isSerializable = true)
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

    @Test
    public void testing6() {
        MinImp m1 = new MinImp();
   Min min = CachedInvocationHendler.getProxyInstance(m1 , "E:/Artyr/studyjava/ProjectsServer/resourse/m1");
        System.out.println(min.min(10, 12 ));
        System.out.println(min.min(10, 12 ));                 // При смене файла для записи создается архив для первого метода, для которого
        min.printNull();                                               // в анотации указано isAddZip = true
        System.out.println(min.min(20 ));                          // После переинсталяции потока прежним файлом, значения добавляются в
        System.out.println(min.min(10, 12 ));                 // конец файла
        System.out.println(min.min(20 ));
        System.out.println(min.min(10, 12 ));
    }

}


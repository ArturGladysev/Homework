package ProxyCache;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;


public class CachedInvocationHendler implements InvocationHandler {

    ObjectOutputStream writeFile = null;         // Поток для записи объектов в файл
    @ValidListSize
    private Object value = null;                 // Переменная получает значение метода в методе invoke
    private boolean fileEmpty = true;             // Флаг для гарантии, что при первом вызове Invoke не обратится к пустому файлу
    private final Map<Pair, Object> resultsWorkMetods = new HashMap<Pair, Object>(); // Хранит ключ - Pair(Метод+массив аргументов) / значение метода
    private final Object delegate;                                       // Объект , методы которого вызываются
 private String fileName = "E:/Artyr/studyjava/ProjectsServer/resourse/m2";
String metodName = "NonMethod";



    public CachedInvocationHendler(Object delegate) {
        this.delegate = delegate;
    }

    public void changePath(String name , Method method) {   //Метод меняет файл, если поток для записи используется впервые или имя файла для
        if (name != fileName || writeFile == null) {          //метода сменилось "через аннотацию"
             fileName = name;
            try {
                writeFile = new ObjectOutputStream(new FileOutputStream(fileName));
            } catch (IOException e) {
                System.out.println("Файл не найден, введите корректное имя файла");
                e.printStackTrace();
            }


        }
        metodName = method.getName();
    }


    public static <T> T getProxyInstance(Object delegate) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), delegate.getClass().getInterfaces(),
                new CachedInvocationHendler(delegate));
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      try {                                                //Если у метода нет аргументов, добавляем аргумент-метку, что нужно для корректной
        if (args == null) {                               //генерации ключа
 args = new Object[1];
  args[0] = (Object) new String("NotParam");
}
        if (!method.isAnnotationPresent(Cache.class)) {                //Проверка флага кэширования, при отсутствии - вызоы метода
            System.out.println("Метод без аннотации Cache");
            if (args[0].equals((Object)"NotParam" )) {
                return method.invoke(delegate);
            }
            else {return method.invoke(delegate, args);}
        }

        Pair<Method, Object> key = new Pair<Method, Object>(method, args);
        if (method.isAnnotationPresent(ValidArgsUse.class)){  //Проверка аргументов, по которым определяется уникальность хранимого значения
             validArgsUseWork(method, key);                       //Метод использует булев массив, хранимый в объекте Pair, его значения учавствуют
        }                                                               // в генерации ключа для хранения значения
        Cache cacheAnnotation = method.getAnnotation(Cache.class);
      if (!method.getName().equals(metodName)) {
          changePath(cacheAnnotation.fileName(), method);
      }
       boolean b = cacheAnnotation.isSerialisable();           //Проверка наличия аннотаций, если метод должен серилиазоваться, вызов
        if (b == true) {                                        // метода для записи/чтения в файл
            return cacheToFile(method, args, key);
        }


        for (Pair p : resultsWorkMetods.keySet()) {                      //Поиск ключа для данного метода с текущим значением в кэше
            if (p.equals(key)) {
                System.out.println("Вернулось кэш значение");
                return resultsWorkMetods.get(p);
            }
        }
       if (args[0].equals((Object)"NotParam" )) {                      // если ничего не найдено, вызов метода
value = method.invoke(delegate);
        }
        else {
           value = method.invoke(delegate, args);

       }
          validListSize(this);                                         //Проверка на то, является ли результат метода Листом, если да,
                                                 //вызов метода проверки и устновки допустимого размера листа, хранимого в
                                                                                            // кэше
        if (resultsWorkMetods.size() < cacheAnnotation.maxSizeCacheValues()) {
            resultsWorkMetods.put(key, value);
            System.out.println("Добавлено кэш значение");
        }

      } catch (IllegalAccessException e2) {
          System.out.println("Неправильные аргументы метода, укажите в соответствии с сигнатурой");
          e2.printStackTrace();
      } catch (Exception e4) {
          e4.printStackTrace();
        }
        return value;
      }



    public void validListSize(Object val) {                               //Установка допустимого размера Листа
        try {
            Field[] fields = val.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(ValidListSize.class)) {
                    Object lst = field.get(val);
                    if (lst instanceof List) {
                        List list = (List) lst;
                        Method getSize = list.getClass().getMethod("size");
                        getSize.setAccessible(true);
                        ValidListSize validAnnotation = field.getAnnotation(ValidListSize.class);
                        Object siz = getSize.invoke(list);
                        Integer size = (Integer) siz;
                        int maxsize = validAnnotation.maxSize();
                        if (size.intValue() > maxsize) {
                            Object[] values = new Object[maxsize];
                            for (int i = 0; i < maxsize; ++i) {
                                values[i] = list.get(i);
                            }
                            list = Arrays.asList(values);
                            field.set(val, list);
                        }
                    }
                }
            }
        } catch (NoSuchMethodException e1) {
            System.out.println("Метод не найден, проверьте наличие метода в классе");
            e1.printStackTrace();
        } catch (IllegalAccessException e2) {
            System.out.println("Неправильные аргументы метода, укажите в соответствии с сигнатурой");
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
            System.out.println("Вызванный рефлексей метод сгенирировал исключение");
        } catch (Exception e4) {
            e4.printStackTrace();
        }
    }


    public void validArgsUseWork(Method method, Pair<Method, Object> key) {   //Игнорирование аргументов для определения уникальности
       if (key.listArgs.get(0).equals((Object)"NotParam")) {                   //Добавляет в стоку генерируемого ключа - false или true
           return;                                                            // для каждого аргумента
       }
        ValidArgsUse validAnnotation = method.getAnnotation(ValidArgsUse.class);
        Integer[] validArgsParam = {validAnnotation.onePar(), validAnnotation.twoPar(), validAnnotation.threePar() };
        for (int i = 0; i < key.flagMetodUse.length; ++i) {
            if (validArgsParam [i] != 0) {
               if (validArgsParam [i] > key.flagMetodUse.length || validArgsParam [i]<0){ throw new IllegalArgumentException(); }

                key.flagMetodUse[validArgsParam[i].intValue()-1] = false;
            }
        }
    }


    public Object cacheToFile(Method method, Object[] args , Pair<Method, Object> key) {   //Кэширования в файл
         Object val;
        Map<String, Object> resultsWorkMetodsFile = new HashMap<>();
        ObjectInputStream readFile = null;
        try {
            if (fileEmpty == false) {
                 readFile = new ObjectInputStream(new FileInputStream(fileName));    // Файл хранит уникальный для метода ключ
                for (String line = (String) readFile.readObject(); ; line = (String) readFile.readObject()) {
                    val = readFile.readObject();                           //Чтение файла  и записть ключа/значения в Map
                    resultsWorkMetodsFile.put(line, val);
                }
            }
            } catch (IOException eio) {
            eio.printStackTrace();
            System.out.println("Достигнут конец файла");
            } catch (Exception e) {
            e.printStackTrace();
        }
                try {
                    Set<String> sk = resultsWorkMetodsFile.keySet();      //Поиск ключа, если true - возврат из кэша, false - записть в файл
                    for (String k : sk) {
            if (k.equals(key.toString())) {
                   String kk = k;
                System.out.println("Вернулось кэш значение из файла");
                        return resultsWorkMetodsFile.get(k);
            }
        }
                    if (args[0].equals((Object)"NotParam" )) {
                        value = method.invoke(delegate);
                    }
                   else {
                        value = method.invoke(delegate, args);
                    }
            writeFile.writeObject(key.toString());
            validListSize(this);
            if (value == null) {
                writeFile.writeObject(null);
            }
            else { writeFile.writeObject(value); }
            System.out.println("Добавлено кэш значение в файл");
         fileEmpty = false;
            return value;


                } catch (NotSerializableException ei) {
                    System.out.println("Файл не может быть сериализован");
                    ei.printStackTrace();

                }
         catch (IOException eio) {
                    System.out.println("Файл не неайден, введите корректное имя файла");
            eio.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
                return null;
    }


}
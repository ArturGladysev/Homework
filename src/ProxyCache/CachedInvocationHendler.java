package ProxyCache;



import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.zip.*;



public class CachedInvocationHendler implements InvocationHandler {

    // Поток для записи объектов в файл
    private ObjectOutputStream writeFile = null;
    // Хранит ключ - Pair(Метод+массив аргументов) / значение метода
    private final Map<Pair, Object> resultsWorkMetods = new HashMap<Pair, Object>();
    private final Object delegate;
 private String fileName = "resourse/cacheFiles/m";
 private String metodName = "NoName";
    private String prevMethodName = "NoName";
ZipHelper zipHelper = new ZipHelper();


    protected CachedInvocationHendler(Object delegate) {
        this.delegate = delegate;
        try {
            zipHelper.zipOut = new ZipOutputStream(new FileOutputStream(zipHelper.zip));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected CachedInvocationHendler(Object delegate, String fileName) {
        this.delegate = delegate;
        this.fileName = fileName;
        try {
            zipHelper.zipOut = new ZipOutputStream(new FileOutputStream(zipHelper.zip));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    //Метод меняет файл если поток для записи используется впервые или имя файла для
    //метода сменилось "через аннотацию"
    //Добавляет файл в архив при вызове метода с другим именем, если
    // установлен флаг isAddToZip в анотации Cache
    protected void changePath(String name, Method method, String prevZipName) {
        if (method.getName().equals(metodName) == false) {

            if (zipHelper.previousIsAddZip == true) {
                zipHelper.addtoZip(prevZipName , this.fileName);
            }
        }
        if (name.equals(fileName) == false || writeFile == null) {
            fileName = name;
            try {
                writeFile = new ObjectOutputStream(new FileOutputStream(fileName, true));
            } catch (IOException e) {
                System.out.println("Файл не найден, введите корректное имя файла");
                e.printStackTrace();
            }
        }
        metodName = method.getName();

    }

    protected static <T> T getProxyInstance(Object delegate , String fileName) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), delegate.getClass().getInterfaces(),
                new CachedInvocationHendler(delegate, fileName));
    }
//Статические методы возвращают новый прокси объект , один из контрукторов позволяет задать имя файла для записи на случай, если он не указан
   // в аннотации

    protected static <T> T getProxyInstance(Object delegate) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), delegate.getClass().getInterfaces(),
                new CachedInvocationHendler(delegate));
    }


    //Проверка аргументов, по которым определяется уникальность хранимого значения
    //Метод использует булев массив, хранимый в объекте Pair, его значения учавствуют
    // в генерации ключа для хранения значения
    //Проверка наличия аннотаций, если метод должен серилиазоваться, вызов
    // метода для записи/чтения в файл
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)  {
     Object value = null;
      try {
        if (!method.isAnnotationPresent(Cache.class)) {
            System.out.println("Метод без аннотации Cache");
           return method.invoke(delegate, args);
            }
        Pair<Method, Object> key = new Pair<Method, Object>(method, args);

        if (method.isAnnotationPresent(ValidArgsUse.class)){
             validArgsUseWork(method, key);
        }
        Cache cacheAnnotation = method.getAnnotation(Cache.class);

      boolean b = cacheAnnotation.isSerialisable();
        if (b == true) {
            return cacheToFile(method, args, key, cacheAnnotation );
        }
        value = addOrGetValue(method , args, key , cacheAnnotation);

      } catch (IllegalAccessException e2) {
          System.out.println("Неправильные аргументы метода, укажите в соответствии с сигнатурой");
          e2.printStackTrace();
      } catch (InvocationTargetException e3) {
          e3.printStackTrace();
          System.out.println("Вызванный рефлексей метод сгенирировал исключение");
      } catch (Exception e4) {
          e4.printStackTrace();
      }
        return value;
      }


    //Поиск ключа для данного метода с текущим значением в кэше
    // если ничего не найдено, добавление в кэш
      protected Object addOrGetValue(Method method, Object[] args, Pair<Method, Object> key, Cache cacheAnnotation) {
        Object value = null;
        try {
    for (Pair p : resultsWorkMetods.keySet()) {
        if (p.equals(key)) {
            System.out.println("Вернулось кэш значение");
            return resultsWorkMetods.get(p);
        }
    }
    if (resultsWorkMetods.size() < cacheAnnotation.maxSizeCacheValues()) {
            value = method.invoke(delegate, args);
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

//Игнорирование аргументов для определения уникальности
//Добавляет в строку генерируемого ключа - false или true
// для каждого аргумента
    protected void validArgsUseWork(Method method, Pair<Method, Object> key) {
       if (key.listArgs == null) {return;}
        ValidArgsUse validAnnotation = method.getAnnotation(ValidArgsUse.class);
        Integer[] validArgsParam = {validAnnotation.onePar(), validAnnotation.twoPar(), validAnnotation.threePar(), validAnnotation.fourPar()
        , validAnnotation.fivePar()};
        for (int i = 0; i < key.flagMetodUse.length; ++i) {
            if (validArgsParam [i] != 0) {
               if (validArgsParam [i] > key.flagMetodUse.length || validArgsParam [i]<0){ throw new IllegalArgumentException(); }

                key.flagMetodUse[validArgsParam[i].intValue()-1] = false;
            }
        }
    }


    //Кэширование в файл
    // Смена выходного потока, если файл для записи сменился и архивирование
    //Все три поля "previos.." запоминают информацию о предыдущем методе, чтобы корректно добавить
    // имя архив, запомнить флаг addToZip, и имя метода, чтобы узнать, что метод сменился
    // и необходимо добавить файл в архив.
    //....Поиск ключа, если true - возврат из кэша, false - записть в файл
    protected Object cacheToFile(Method method, Object[] args , Pair<Method, Object> key, Cache cacheAnnotation ) {
        Object value = null;
        Map<String, Object> resultsWorkMetodsFile = new HashMap<>();
        ObjectInputStream readFile = null;

        if (!method.getName().equals(metodName)) {
            changePath(cacheAnnotation.fileName(), method, zipHelper.previousZipName) ;
        }
        zipHelper.previousIsAddZip = cacheAnnotation.addToZip();
        zipHelper.previousZipName = cacheAnnotation.zipName();
        zipHelper.prevMethodName = method.getName();

try {
                File file = new File(fileName);
                if(file.length()!=0) {
                 readFile = new ObjectInputStream(new FileInputStream(fileName));
                for (String line = (String) readFile.readObject(); ; line = (String) readFile.readObject()) {
                    value = readFile.readObject();
                    resultsWorkMetodsFile.put(line, value);
                }
            }
            } catch (IOException eio) {
            System.out.println("Достигнут конец файла");
            } catch (Exception e) {
            e.printStackTrace();
        }
                try {
                    Set<String> sk = resultsWorkMetodsFile.keySet();
                    for (String k : sk) {
            if (k.equals(key.toString())) {
                   String kk = k;
                System.out.println("Вернулось кэш значение из файла");
                        return resultsWorkMetodsFile.get(k);
            }
        }
                        value = method.invoke(delegate, args);
            writeFile.writeObject(key.toString());
            if (value == null) {
                writeFile.writeObject(null);
            }
            else { writeFile.writeObject(value); }
            System.out.println("Добавлено кэш значение в файл");
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
        return value;
    }
}
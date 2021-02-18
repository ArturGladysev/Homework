package ProxyCache;



import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.zip.*;



public class CachedInvocationHendler implements InvocationHandler {

    protected ObjectOutputStream writeFile = null;                                           // Поток для записи объектов в файл
    protected boolean fileEmpty = true;                                     // Флаг для гарантии, что при первом вызове Invoke не обратится к пустому файлу
    protected final Map<Pair, Object> resultsWorkMetods = new HashMap<Pair, Object>(); // Хранит ключ - Pair(Метод+массив аргументов) / значение метода
    protected final Object delegate;                                                      // Объект , методы которого вызываются
 protected String fileName = "C/User/doc/text.txt";
 protected String metodName = "NoName";
    protected String zipName = "C/User/doc/zip.zip";
    protected String previousZipName = "NoName";
    protected boolean previousIsAddZip = false;
    protected File zip = new File(zipName);
   protected int countNames = 0;                                              //Счетчик для генерации уникальных имен внутри архива
    protected ZipOutputStream zipOut = null;
    protected String prevMethodName = "NoName";


    protected CachedInvocationHendler(Object delegate) {
        this.delegate = delegate;
        try {
            zipOut = new ZipOutputStream(new FileOutputStream(zip));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected CachedInvocationHendler(Object delegate, String fileName) {
        this.delegate = delegate;
        this.fileName = fileName;
        try {
            zipOut = new ZipOutputStream(new FileOutputStream(zip));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    protected void changePath(String name, Method method, String prevZipName) {   //Метод меняет файл если поток для записи используется впервые или имя файла для
        if (method.getName().equals(metodName) == false) {                     //метода сменилось "через аннотацию"
                                                                              //Добавляет файл в архив при вызове метода с другим именем, если
            if (previousIsAddZip == true) {                                         // установлен флаг isAddToZip в анотации Cache
                addtoZip(prevZipName);
            }
        }
        if (name.equals(fileName) == false || writeFile == null) {
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


    @Override
    public Object invoke(Object proxy, Method method, Object[] args)  {
     Object value = null;
      try {                                                                   //Если у метода нет аргументов, добавляем аргумент-метку, что нужно для корректной
        if (args == null) {                                                   //генерации значения
 args = new Object[1];
  args[0] = (Object) new String("NotParam");
}
        if (!method.isAnnotationPresent(Cache.class)) {                   //Проверка флага кэширования, при отсутствии - вызов метода
            System.out.println("Метод без аннотации Cache");
            if (args[0].equals((Object)"NotParam" )) {
                return method.invoke(delegate);
            }
            else {return method.invoke(delegate, args);}
        }

        Pair<Method, Object> key = new Pair<Method, Object>(method, args);
        if (method.isAnnotationPresent(ValidArgsUse.class)){                //Проверка аргументов, по которым определяется уникальность хранимого значения
             validArgsUseWork(method, key);                                //Метод использует булев массив, хранимый в объекте Pair, его значения учавствуют
        }                                                                   // в генерации ключа для хранения значения
        Cache cacheAnnotation = method.getAnnotation(Cache.class);


      boolean b = cacheAnnotation.isSerialisable();                           //Проверка наличия аннотаций, если метод должен серилиазоваться, вызов
        if (b == true) {                                                       // метода для записи/чтения в файл
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



      protected Object addOrGetValue(Method method, Object[] args, Pair<Method, Object> key, Cache cacheAnnotation) {
   Object value = null;
        try {
    for (Pair p : resultsWorkMetods.keySet()) {                      //Поиск ключа для данного метода с текущим значением в кэше
        if (p.equals(key)) {                                         // если ничего не найдено, добавление в кэш
            System.out.println("Вернулось кэш значение");
            return resultsWorkMetods.get(p);
        }
    }

    if (resultsWorkMetods.size() < cacheAnnotation.maxSizeCacheValues()) {
        if (args[0].equals((Object) "NotParam")) {
            value = method.invoke(delegate);
        }
        else {
            value = method.invoke(delegate, args);
        }
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



    protected void validArgsUseWork(Method method, Pair<Method, Object> key) {   //Игнорирование аргументов для определения уникальности
       if (key.listArgs.get(0).equals((Object)"NotParam")) {                   //Добавляет в строку генерируемого ключа - false или true
           return;                                                            // для каждого аргумента
       }
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


   protected void addtoZip(String preVzipName) {           //Создает новый архив, если этого требует аннотация нового метода, если архив для нового и
      try {                                                    //вызванного/ных метода/ов ранее одинаков, создает внутри новый файл

if (!preVzipName.equals(this.zipName)){
             zipName = preVzipName;
              zip = new File(preVzipName);
              zipOut = new ZipOutputStream(new FileOutputStream(zip));
          }
       ZipEntry e = new ZipEntry(prevMethodName + countNames +".txt");
       zipOut.putNextEntry(e);
       FileReader  fileReader = new FileReader(fileName);
       Scanner scanner = new Scanner(fileReader);
       while (scanner.hasNextLine()) {
           String line = scanner.nextLine();
           byte[] data = line.getBytes();
           zipOut.write(data);
       }
       zipOut.closeEntry();
       zipOut.close();
   scanner.close();
   fileReader.close();
      ++countNames;
          System.out.println("Добавлен в архив: " + zipName + " файл:" + prevMethodName + countNames +".txt");
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }

   }


    protected Object cacheToFile(Method method, Object[] args , Pair<Method, Object> key, Cache cacheAnnotation ) {   //Кэширование в файл
        if (!method.getName().equals(metodName)) {
            changePath(cacheAnnotation.fileName(), method, previousZipName) ; // Смена выходного потока, если файл для записи сменился и архивирование
        }
        previousIsAddZip = cacheAnnotation.addToZip();                    //Все три поля запоминают информацию о предыдущем методе, чтобы корректно добавить
        previousZipName = cacheAnnotation.zipName();                          // имя архив, запомнить флаг addToZip, и имя метода, чтобы узнать, что метод сменился
        prevMethodName = method.getName();                                      // и необходимо добавить файл в архив.

         Object value = null;
        Map<String, Object> resultsWorkMetodsFile = new HashMap<>();
        ObjectInputStream readFile = null;
try {
            if (fileEmpty == false) {
                 readFile = new ObjectInputStream(new FileInputStream(fileName));    // Файл хранит уникальный для метода ключ
                for (String line = (String) readFile.readObject(); ; line = (String) readFile.readObject()) {
                    value = readFile.readObject();                            //Чтение файла  и записть ключа/значения в Map
                    resultsWorkMetodsFile.put(line, value);
                }
            }
            } catch (IOException eio) {
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
            if (value == null) {
                writeFile.writeObject(null);
            }
            else { writeFile.writeObject(value); }
            System.out.println("Добавлено кэш значение в файл");
         fileEmpty = false;

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
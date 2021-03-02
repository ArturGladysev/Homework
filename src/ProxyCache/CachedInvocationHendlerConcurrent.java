package ProxyCache;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.zip.*;


public class CachedInvocationHendlerConcurrent implements InvocationHandler {
/* В классе используется ReentrantReadWriteLock , блокировка не дает другим потокам прочитать значения из файла и map
   пока один поток проверяет наличие кэш значения и/или добавляет значение, остальной код не отличается от CachedInvocationHendler*/


    private volatile ObjectOutputStream writeFile = null;
    private volatile String fileName = "E:resourse/cacheFiles/m5";
    private volatile String metodName = "NoName";
    private volatile String prevMethodName = "NoName";
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Map<Pair, Object> resultsWorkMetods = new HashMap<Pair, Object>();
    private final Object delegate;
    private volatile ZipHelper zipHelper = new ZipHelper();


    private CachedInvocationHendlerConcurrent(Object delegate) {
        this.delegate = delegate;
        try {
            zipHelper.zipOut = new ZipOutputStream(new FileOutputStream(zipHelper.zip));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private CachedInvocationHendlerConcurrent(Object delegate, String fileName) {
        this.delegate = delegate;
        this.fileName = fileName;
        try {
            zipHelper.zipOut = new ZipOutputStream(new FileOutputStream(zipHelper.zip));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static <T> T getProxyInstance(Object delegate , String fileName) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), delegate.getClass().getInterfaces(),
                new CachedInvocationHendlerConcurrent(delegate, fileName));
    }

    public static <T> T getProxyInstance(Object delegate) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), delegate.getClass().getInterfaces(),
                new CachedInvocationHendlerConcurrent(delegate));
    }



    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Object value = null;
        try {
        if (!method.isAnnotationPresent(Cache.class)) {
            System.out.println("Метод без аннотации Cache");
                return method.invoke(delegate, args);
            }
        } catch (IllegalAccessException e2) {
            System.out.println("Неправильные аргументы метода, укажите в соответствии с сигнатурой");
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
            System.out.println("Вызванный рефлексей метод сгенирировал исключение");
        } catch (Exception e4) {
            e4.printStackTrace();
        }
            Cache cacheAnnotation = method.getAnnotation(Cache.class);
            Pair<Method, Object> key = new Pair<Method, Object>(method, args);
            if (method.isAnnotationPresent(ValidArgsUse.class)) {
                validArgsUseWork(method, key);
            }

                boolean b = cacheAnnotation.isSerialisable();
                if (b == true) {
                    return cacheToFile(method, args, key, cacheAnnotation);
                }
            value = addOrGetValue(method, args, key, cacheAnnotation);

        return value;
    }
        
        


    private Object addOrGetValue(Method method, Object[] args, Pair<Method, Object> key, Cache cacheAnnotation) {
        Object value = null;
        //Первая блокировка при возврате\добавления значений в память
        readWriteLock.writeLock().lock();
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
    } finally {
                readWriteLock.writeLock().unlock();
            }

        return value;
    }



    private Object cacheToFile(Method method, Object[] args, Pair<Method, Object> key, Cache cacheAnnotation) {
        Object value = null;
        Map<String, Object> resultsWorkMetodsFile = new HashMap<>();
        ObjectInputStream readFile = null;
        //Блокировка позволяет пополнить архив, если это нужно, прочитать файл и\или
        // записать новое значение
        readWriteLock.writeLock().lock();
       try {
           if (!method.getName().equals(metodName)) {
               changePath(cacheAnnotation.fileName(), method, zipHelper.previousZipName);
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
                   } else {
                       writeFile.writeObject(value);
                   }
                   System.out.println("Добавлено кэш значение в файл");

           } catch (NotSerializableException ei) {
               System.out.println("Файл не может быть сериализован");
               ei.printStackTrace();
           } catch (IOException eio) {
               System.out.println("Файл не неайден, введите корректное имя файла");
               eio.printStackTrace();

           } catch (Exception e) {
               e.printStackTrace();
           }
       }finally {
           readWriteLock.writeLock().unlock();
       }
        return value;
    }






    private void changePath(String name, Method method, String prevZipName) {
        if (method.getName().equals(metodName) == false) {

            if (zipHelper.previousIsAddZip == true) {
                zipHelper.addtoZip(prevZipName , this.fileName);
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


    private void validArgsUseWork(Method method, Pair<Method, Object> key) {
        if (key.listArgs.get(0).equals((Object)"NotParam")) {
            return;
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

}
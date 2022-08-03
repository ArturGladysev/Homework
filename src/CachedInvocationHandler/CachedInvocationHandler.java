package CachedInvocationHandler;



import java.io.*;
import java.lang.reflect.*;
import java.util.*;

/*
 Задача: на основе InvocationHandler реализовать кэширование методов прокси класса:
 класс должен перехватывать и проксировать вызовы методов оригинального класса.
 Если метод уже вызывался ранее с определенными параметрами, то результат должен
 возвращаться из кэша. 1. Кэширование должно настраиваться через аннотацию (кешировать
 значения в файл или в Map). 2. Должна быть возможность игнорировать один или несколько
 аргументов метода при создании ключа кэширования.
 Описание методов:
 1. Статические методы возвращают новый прокси объект, один из конструкторов позволяет задать имя
    файла для записи если он не указан в аннотации.
 2. invoke анализирует переданный метод на предмет аннотаций, производит настройку аргументов по необходимости
    (игнорирование при генерации ключа в MethodsInfo) и в зависимости от значения параметра
    isSerializable кэширует значение либо в файл, либо в Map.
 3. changePath меняет файл если поток для записи используется впервые или имя файла для
    метода сменилось "через аннотацию".
 4. cacheToMap ищет ключ данного метода в кэше, если находит, возвращает
    результат метода с данными параметрами, если ничего не найдено, добавляет ключ и результат
    работы метода в кэш.
 5. Метод ignoreArguments позволяет игнорировать определенные аргументы
    при формировании ключа в MethodsInfo путем добавления
    в строку генерируемого ключа - false или true для каждого аргумента.
 6. cacheToFile загружает из файла данные о методах и их результаты работы с конкретными
    параметрами в Map, анализирует их. Если метод с данными параметрами выполнялся,
    результат возвращается из файла. В противном случае результат добавляется в файл.
 */

public class CachedInvocationHandler implements InvocationHandler
{
    private final Object delegate;

    private final Map<MethodInfo, Object> cache = new HashMap<MethodInfo, Object>();

    private ObjectOutputStream objectOutputStream = null;

    private String fileName = "resource/cacheFiles/file";

    private String methodName = "NoName";

    protected CachedInvocationHandler(Object delegate)
    {
        this.delegate = delegate;
    }

    protected CachedInvocationHandler(Object delegate, String fileName)
    {
        this.delegate = delegate;
        this.fileName = fileName;
    }

    protected void changePath(String fileName, Method method)
    {
        if(fileName.equals(this.fileName) == false || objectOutputStream == null)
        {
            this.fileName = fileName;
            try
            {
                objectOutputStream = new ObjectOutputStream(new FileOutputStream(this.fileName, true));
            }
            catch(IOException e)
            {
                System.out.println("Файл не найден, введите корректное имя файла");
                e.printStackTrace();
            }
        }
        methodName = method.getName();
    }

    protected static <T> T getProxyInstance(Object delegate, String fileName)
    {
        return (T)Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), delegate.getClass().getInterfaces(),
          new CachedInvocationHandler(delegate, fileName));
    }

    protected static <T> T getProxyInstance(Object delegate)
    {
        return (T)Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), delegate.getClass().getInterfaces(),
          new CachedInvocationHandler(delegate));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
    {
        try
        {
            if(!method.isAnnotationPresent(Cache.class))
            {
                System.out.println("Метод без аннотации Cache");
                return method.invoke(delegate, args);
            }
            MethodInfo methodInfo = new MethodInfo(method, args);
            if(method.isAnnotationPresent(ArgumentsIgnore.class))
            {
                ignoreArguments(method, methodInfo);
            }
            if(method.getAnnotation(Cache.class).isSerializable())
                return cacheToFile(methodInfo, args, method.getAnnotation(Cache.class));
            return cacheToMap(methodInfo, args, method.getAnnotation(Cache.class));
        }
        catch(IllegalAccessException el)
        {
            System.out.println("Неправильные аргументы метода, укажите в соответствии с сигнатурой");
            el.printStackTrace();
        }
        catch(InvocationTargetException et)
        {
            et.printStackTrace();
            System.out.println("Вызванный рефлексией метод сгенерировал исключение");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    protected Object cacheToMap(MethodInfo methodInfo, Object[] args, Cache cacheAnnotation)
    {
        Object value = null;
        try
        {
            for(MethodInfo cacheMethodInfo : cache.keySet())
            {
                if(cacheMethodInfo.equals(methodInfo))
                {
                    System.out.println("Вернулось кэш значение");
                    return cache.get(cacheMethodInfo);
                }
            }
            if(cache.size() < cacheAnnotation.maxCachedValues())
            {
                value = methodInfo.getMethod().invoke(delegate, args);
                cache.put(methodInfo, value);
                System.out.println("Добавлено кэш значение");
            }
        }
        catch(IllegalAccessException ei)
        {
            System.out.println("Неправильные аргументы метода, укажите в соответствии с сигнатурой");
            ei.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return value;
    }

    protected void ignoreArguments(Method method, MethodInfo methodInfo)
    {
        if(methodInfo.getArguments() == null)
        {
            return;
        }
        ArgumentsIgnore argumentsIgnoreAnnotation = method.getAnnotation(ArgumentsIgnore.class);
        Boolean[] argumentIgnore = {argumentsIgnoreAnnotation.oneArgument(), argumentsIgnoreAnnotation.twoArgument(),
          argumentsIgnoreAnnotation.threeArgument(), argumentsIgnoreAnnotation.fourArgument(), argumentsIgnoreAnnotation.fiveArgument()};
        for(int i = 0; i < methodInfo.getUsedArguments().length; ++i)
        {
            methodInfo.getUsedArguments()[i] = argumentIgnore[i];
        }
    }

    protected Object cacheToFile(MethodInfo methodInfo, Object[] args, Cache cacheAnnotation)
    {
        Object value = null;
        Map<String, Object> fileCache = new HashMap<>();
        ObjectInputStream source;
        if(!methodInfo.getMethod().getName().equals(methodName))
            changePath(cacheAnnotation.fileName(), methodInfo.getMethod());
        try
        {
            File file = new File(fileName);
            if(file.length() != 0)
            {
                source = new ObjectInputStream(new FileInputStream(file));
                for(String line = (String)source.readObject(); ; line = (String)source.readObject())
                {
                    value = source.readObject();
                    fileCache.put(line, value);
                }
            }
        }
        catch(IOException eio)
        {
            System.out.println("Достигнут конец файла");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            Set<String> keys = fileCache.keySet();
            for(String key : keys)
            {
                if(key.equals(methodInfo.toString()))
                {
                    System.out.println("Вернулось кэш значение из файла");
                    return fileCache.get(key);
                }
            }
            value = methodInfo.getMethod().invoke(delegate, args);
            objectOutputStream.writeObject(methodInfo.toString());
            if(value == null)
                objectOutputStream.writeObject(null);
            else
                objectOutputStream.writeObject(value);
            System.out.println("Добавлено кэш значение в файл");
        }
        catch(NotSerializableException ei)
        {
            System.out.println("Файл не может быть сериализован");
            ei.printStackTrace();
        }
        catch(IOException eio)
        {
            System.out.println("Файл не найден, введите корректное имя файла");
            eio.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return value;
    }
}
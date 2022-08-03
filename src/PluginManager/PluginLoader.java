package PluginManager;

import java.net.URL;
import java.net.URLClassLoader;


/*Загрузчик плагинов, оборачивает методы родителя, что делает его более гибким.
  Если системный загрузчик загрузил классы с такими же именами, которые используются скомпилированными классами плагинов,
  то загрузчик загрузит классы заново, так как делегировать загрузку некому (предполагается, что системный загрузчик
  не мог загрузить плагины ранее и пользователю это известно.
*/

public class PluginLoader extends URLClassLoader
{
    public PluginLoader(URL[] urls)
    {
        super(urls, null);
    }

    public Class<?> findLoadedPluginClass(String className)
    {
        System.out.println("Сработал метод findLoadedPluginClass");
        Class<?> foundClass = findLoadedClass(className);
        try
        {
            if(!checkFoundClass(foundClass))
                throw new IllegalArgumentException("The class does not implement the interface MyPlugin");
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return foundClass;
    }

    public Class<?> loadPluginClass(String className)
    {
        Class<?> foundClass = null;
        try
        {
            System.out.println("Сработал метод loadPluginClass");
            foundClass = loadClass(className);
            if(!checkFoundClass(foundClass))
                throw new IllegalArgumentException("The class does not implement the interface MyPlugin");
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return foundClass;
    }

    public boolean checkFoundClass(Class<?> foundClass) throws ClassNotFoundException
    {
        if(foundClass == null)
            throw new ClassNotFoundException();
        Class<?>[] interfaces = foundClass.getInterfaces();
        for(Class elem : interfaces)
        {
            if(elem.toString().equals("interface MyPlugin"))
                return true;
        }
        return false;
    }

    public void addURl(URL url)
    {
        super.addURL(url);
    }

    public URL[] getURl()
    {
        return super.getURLs();
    }
}

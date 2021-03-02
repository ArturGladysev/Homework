package PluginManager;

import java.net.URL;
import java.net.URLClassLoader;


//Загрузчик плагинов, оборачивает методы родителя, что делает его более гибким
public class PluginsLoader extends URLClassLoader {


    public PluginsLoader(URL[] urls ) {
        super(urls, null);
    }
//Если в системный загрузчик загрузил классы с такими же именами, которые используются скомпилированными классами плагинов,
// то загрузчик загрузит классы, так как делегировать загрузку некому.

    public Class<?> findingClass(String className) {
    System.out.println("Метод findingClass");
    return super.findLoadedClass(className);
 }



    public Class<?> loadingClass(String className) {
        try {
            System.out.println("Метод loadingClass");
            return super.loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    return null;
    }



    public void addURl(URL url) {
        super.addURL(url);
}

  public URL[] getURl() {
    return super.getURLs();
    }

}

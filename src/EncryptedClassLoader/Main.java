package EncryptedClassLoader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {


//Класс принимает в конструкторе дирректорию конкретного класса, который он зашивровывает и записывает в указанный пользователем файл
      // Пользовтаель может поменять дирректорию для искомого класса через метод класса, загрузчик также хранит имя класса,
        // который указан в дирректории файла


        File fromUrl = new File("resourse\\plugins\\classes");
        File from = new File("resourse\\plugins\\classes\\MyPlugin.class");
        String to = "resourse\\plugins\\sewUpClass";

       URL[] url = null;
        try {
            url = new URL[] {(from).toURI().toURL()};
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        EncryptedClassLoader eClassLoader = new EncryptedClassLoader(4, from, url );
        eClassLoader.SewUpClass(eClassLoader.getDir().toString(), to, eClassLoader.getKey());
       Class plugin = eClassLoader.findClass(to);
eClassLoader.changeDirAndClassname(new File("resourse\\plugins\\classes\\MyPluginImpTwo.class"));
     //Метод найдет и загрузит класс в том случае, если у класса нет интерфейсов, которые не были загружены системным загрузчиком класса
      // (пользовательские интерфейсы). Их необходимо загружать через системный загрузчик или сперва загружать интерфейс, а потом приводить.
        //к нему класс
       System.out.println(plugin.getName());
      to = "resourse\\plugins\\sewUpClassTwo";
        eClassLoader.SewUpClass(eClassLoader.getDir().toString(), to, eClassLoader.getKey());
      plugin = eClassLoader.findClass(to);
        System.out.println(plugin.getName());

    }
}
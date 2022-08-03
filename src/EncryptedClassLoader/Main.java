package EncryptedClassLoader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/*
Задача: реализовать класс, принимающий в конструкторе дирректорию конкретного класса, который он зашифровывает
и записывает в указанный пользователем файл. Данный класс должен уметь расшифоровать и загрузить зашифрованный класс.
П.С: Пользовтаель может поменять дирректорию для искомого класса через метод класса,
загрузчик также хранит имя класса, который указан в дирректории файла.
 */

public class Main
{
    public static void main(String[] args)
    {
        File from = new File("resource/plugins/classes/MyPlugin.class");
        String pathTo = "resource/plugins/sewUpClass";
        URL[] url = null;
        try
        {
            url = new URL[]{(from).toURI().toURL()};
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
        EncryptedClassLoader eClassLoader = new EncryptedClassLoader(4, from, url);
        eClassLoader.SewUpClass(pathTo);

        /*
        Метод найдет и загрузит класс в том случае, если у класса нет интерфейсов, которые не были загружены системным загрузчиком класса
        (пользовательские интерфейсы). Их необходимо загружать через системный загрузчик или сперва загружать интерфейс, а потом приводить
        к нему найденный класс.
        */
        Class<?> plugin = eClassLoader.findClass(pathTo);
        System.out.println(plugin.getName());

        eClassLoader.changeDirAndClassname(new File("resource/plugins/classes/MyPluginImpTwo.class"));
        pathTo = "resource/plugins/sewUpClassTwo";
        eClassLoader.SewUpClass(pathTo);
        plugin = eClassLoader.findClass(pathTo);
        System.out.println(plugin.getName());
    }
}
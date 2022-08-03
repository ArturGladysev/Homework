package PluginManager;

/*
Задача: реализовать PluginManager для загрузки плагинов в приложение: класс должен иметь экземпляр Classloader
для загрузки плагинов и хранить в HashSet имена уже загруженных классов.
Если плагин был загружен ранее, Classloader должен искать класс среди загруженных,
если нет, то производить загрузку по указанному в нем пути.
 */

public class Main
{
    public static void main(String[] args)
    {
        //В данной директории должны храниться классы *class, наследуемые интерфейс Plugin
        String pluginRootDirectory = "resource/plugins/classes";
        PluginManager pluginManager = new PluginManager(pluginRootDirectory);
        Plugin p1 = pluginManager.load("MyPluginImpTwo");
        Plugin p2 = pluginManager.load("MyPluginImpTwo");
        Plugin p3 = pluginManager.load("MyPluginImpThree");
        System.out.println("Имя полученного класса: " + p1.getPluginName());
        System.out.println("Имя загруженного класса: " + p2.getPluginName());
        System.out.println("Имя полученного класса: " + p3.getPluginName());
        p1.doUseful();
        p2.doUseful();
        p3.doUseful();
    }

}

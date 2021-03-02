package PluginManager;

public class Main {

    public static void main(String[] args) {
       String  pluginRootDirectory = "resourse\\plugins\\classes";
       //В данной директории должны храниться классы *class, наследуемые интерфейс Plugin

        PluginManager pluginManager = new PluginManager(pluginRootDirectory);

       Plugin p1 = pluginManager.load("MyPluginImpTwo");

        Plugin p2 = pluginManager.load("MyPluginImpTwo");

        Plugin p3 = pluginManager.load("MyPluginImpThree");

        System.out.println("Имя полученного класса: " + p1.getPluginName() );
        System.out.println("Имя загруженного класса: " + p2.getPluginName()  );
      System.out.println("Имя полученного класса: " + p3.getPluginName() );
        p1.doUsefull();
        p2.doUsefull();
p3.doUsefull();
    }



}

package PluginManager;



import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;

public class PluginManager {
    private HashSet<String> dataSet = new HashSet();
    private PluginsLoader pluginsLoader;
    private final String pluginRootDirectory;


    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
        try {
            this.pluginsLoader = new PluginsLoader(new URL[]{new File(pluginRootDirectory).toURI().toURL()});

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    // Класс использует ХэшСет для определения уникальности имени, если в Set есть плагин искомое имя, класс возвращается из загруженных,
    // В противном случае метод ищет класс по добавленным дирректориям. Метод проеряет, имплементирует ли найденный класс Plugin

    public Plugin load( String pluginClassName) {

        boolean isPiugin = false;
        if (dataSet.isEmpty() == false) {
            if (dataSet.contains(pluginClassName)) {
                Class pluginClass = pluginsLoader.findingClass(pluginClassName);
              Plugin findPlugin = new PluginImp(pluginClass);
                return findPlugin;

            }
        }
        Class pluginClazz = pluginsLoader.loadingClass(pluginClassName);
        Class<?>[] interfaces = pluginClazz.getInterfaces();
        String nameInterface = null;
        for (Class elem : interfaces) {
            nameInterface = elem.toString();
            if (nameInterface.equals("interface MyPlugin")) {
                isPiugin = true;
            }
        }
        if (isPiugin == false) {
            throw new IllegalArgumentException();
        }
        dataSet.add(pluginClassName);
        Plugin loadPlugin = new PluginImp(pluginClazz);
        return loadPlugin;
    }


    public void addURl(URL url) {
        pluginsLoader.addURl(url);
    }

    public URL[] getURL() {
        return pluginsLoader.getURl();

    }
//Добавить новые методы легко - путем оберачивания


}
package PluginManager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;


public class PluginManager
{
    private HashSet<String> PluginNames = new HashSet();

    private PluginLoader pluginLoader;

    private final String pluginRootDirectory;

    public PluginManager(String pluginRootDirectory)
    {
        this.pluginRootDirectory = pluginRootDirectory;
        try
        {
            this.pluginLoader = new PluginLoader(new URL[]{new File(pluginRootDirectory).toURI().toURL()});
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    public Plugin load(String pluginClassName)
    {
        Class pluginClass;
        if(!PluginNames.isEmpty())
        {
            if(PluginNames.contains(pluginClassName))
            {
                pluginClass = pluginLoader.findLoadedPluginClass(pluginClassName);
                return new PluginProxy(pluginClass);
            }
        }
        pluginClass = pluginLoader.loadPluginClass(pluginClassName);
        PluginNames.add(pluginClassName);
        return new PluginProxy(pluginClass);
    }

    public void addURl(URL url)
    {
        pluginLoader.addURl(url);
    }

    public URL[] getURL()
    {
        return pluginLoader.getURl();
    }
}
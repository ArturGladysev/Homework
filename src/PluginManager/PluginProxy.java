package PluginManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/*Класс является оберткой для классов, имплементирующих интерфейс Plugin,
  использует рефлексию для вызова метода.
*/

public class PluginProxy implements Plugin
{
    private Class<? extends Plugin> plugin;

    public PluginProxy(Class<? extends Plugin> plugin)
    {
        this.plugin = plugin;
    }

    public String getPluginName()
    {
        return plugin.getName();
    }

    public Class<? extends Plugin> getPluginClass()
    {
        return plugin;
    }

    @Override
    public void doUseful()
    {
        try
        {
            this.plugin.getMethod("doUsefull").invoke(this.plugin.getConstructor().newInstance(), null);
        }
        catch(NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch(IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch(InvocationTargetException e)
        {
            e.printStackTrace();
        }
        catch(InstantiationException e)
        {
            e.printStackTrace();
        }
    }
}
package PluginManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


//Класс хранит полученный плагин, имплементирующий интерфейс Plugin
// Использует рефлексию, чтобы вызывать метод. Это позволяет избежать необходимости загружать в систему интерфейсы для плагинов

public class PluginImp implements Plugin {
    private Class plugin;

    public PluginImp(Class plugin) {
        this.plugin = plugin;
    }

  public String getPluginName() {
        return plugin.getName();
  }

public Class<?> getPluginClass() {
        return plugin;
}
  
    @Override
    public void doUsefull() {
        Object plug = null;
        try {
            Method method = plugin.getMethod("doUsefull");
            plug = plugin.getConstructor().newInstance();
            method.invoke(plug, null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }
}
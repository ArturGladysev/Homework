package EncryptedClassLoader;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.security.CodeSource;

public class EncryptedClassLoader extends URLClassLoader
{
    private final int key;

    private File dir;

    private String className;

    public int getKey()
    {
        return key;
    }

    public File getDir()
    {
        return dir;
    }

    public void setDir(File dir)
    {
        this.dir = dir;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public EncryptedClassLoader(int key, File dir, URL[] url)
    {
        super(url, getSystemClassLoader());
        this.key = key;
        this.changeDirAndClassname(dir);
    }


    public void changeDirAndClassname(File dir)
    {
        this.dir = dir;

        //Извлечение имени класса из пути к файлу "для простоты"
        String[] splitPath = dir.getPath().split(("\\\\"));
        String classNameWithPostfix = splitPath[splitPath.length - 1];
        String[] splitClassName = classNameWithPostfix.split("\\.");
        String resultName = "";
        for(int i = 0; i < splitClassName.length - 1; ++i)
        {
            resultName += splitClassName[i];
        }
        this.setClassName(resultName);
    }

    //Метод зашифровывает класс (key используется дла шифрования)
    public void SewUpClass(String pathTo)
    {
        try
        {
            System.out.println(pathTo);
            FileInputStream in = new FileInputStream(dir.toString());
            FileOutputStream out = new FileOutputStream(pathTo);
            byte[] bytes = new byte[(int)(new File(dir.toString()).length())];
            in.read(bytes);
            for(int i = 0; i < bytes.length; ++i)
            {
                bytes[i] += key;
            }
            in.close();
            out.write(bytes);
            out.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    //Расшифровка класса
    public Class<?> findClass(String ClassPath)
    {
        Class<?> foundClass = decryptClass(ClassPath);
        return foundClass;
    }

    private Class<?> decryptClass(String path)
    {
        try
        {
            FileInputStream in = new FileInputStream(path);
            byte[] bytes = new byte[(int)(new File(path).length())];
            in.read(bytes);
            for(int i = 0; i < bytes.length; ++i)
            {
                bytes[i] -= this.getKey();
            }
            in.close();
            return super.defineClass(this.getClassName(), ByteBuffer.wrap(bytes), (CodeSource)null);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}


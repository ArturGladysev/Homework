package EncryptedClassLoader;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.security.CodeSource;

public class EncryptedClassLoader extends URLClassLoader {
    private final int key;
    private  File dir;
    private String className;

    public void setDir(File dir) {
        this.dir = dir;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public EncryptedClassLoader(int key, File dir, URL[] url) {
       super(url , getSystemClassLoader());
        this.key = key;
        this.changeDirAndClassname(dir);

    }

    public int getKey() {
        return key;
    }

    public File getDir() {
        return dir;
    }


  //Извлечение имени класс из пути к файлу "для простоты"
   public void changeDirAndClassname(File dir) {
       this.dir = dir;
       String[] words = dir.getPath().toString().split(("\\\\"));
  String newName = words[words.length-1];
       String[] name = newName.split("\\.");
 String resName = "";
       for(int i = 0; i< name.length-1; ++i) {
           resName += name[i];
       }
  this.setClassName(resName);

    }



   //Метод зашифровывает класс
    public void SewUpClass(String pathFrom, String pathTo, int key) {
        try {
            System.out.println(pathTo);

            FileInputStream in = new FileInputStream(pathFrom);
            FileOutputStream out = new FileOutputStream(pathTo);
            byte[] bytes = new byte[(int) (new File(pathFrom).length())];
            in.read(bytes);
            for (int i = 0; i < bytes.length; ++i) {
               bytes[i] += key;

            }
            in.close();
            out.write(bytes);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   //Расшифровка класса
    public Class<?> findClass( String ClassPath) {
        Class foundClass = decryptClass(ClassPath, this.key, this.getClassName());
return foundClass;
        }


        private Class<?> decryptClass(String path, int key, String nameClass){
            try {
                FileInputStream in = new FileInputStream(path);
                byte[] bytes = new byte[(int) (new File(path).length())];
                in.read(bytes);
                for (int i = 0; i < bytes.length; ++i) {
                    bytes[i] -= key;
                }
                in.close();
                return super.defineClass(nameClass, ByteBuffer.wrap(bytes), (CodeSource) null);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


package ProxyCache;

import java.io.*;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipHelper {
    public String zipName = "resourse/cacheFiles/zipFiles/zipAr.zip";
    public String previousZipName = "NoName";
    public boolean previousIsAddZip = false;
    public File zip = new File(zipName);
    //Счетчик для генерации уникальных имен внутри архива
    public int countNames = 0;
    public ZipOutputStream zipOut = null;
    public String prevMethodName = "NoName";

    //Создает новый архив, если этого требует аннотация нового метода, если архив для нового и
    //вызванного/ных метода/ов ранее одинаков, создает внутри новый файл
    protected void addtoZip(String preVzipName , String fileName) {
        try {
            if (!preVzipName.equals(this.zipName)){
                zipName = preVzipName;
                zip = new File(preVzipName);
                zipOut = new ZipOutputStream(new FileOutputStream(zip));
            }
            ZipEntry e = new ZipEntry(prevMethodName + countNames +".txt");
            zipOut.putNextEntry(e);
            FileReader fileReader = new FileReader(fileName);
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                byte[] data = line.getBytes();
                zipOut.write(data);
            }
            zipOut.closeEntry();
            zipOut.close();
            scanner.close();
            fileReader.close();
            ++countNames;
            System.out.println("Добавлен в архив: " + zipName + " файл:" + prevMethodName + countNames +".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

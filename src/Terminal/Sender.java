package Terminal;

import java.io.OutputStream;

//Класс хранит объект outPutStream и записывает в него информацию об исключениях
public class Sender
{
    private OutputStream source;

    public Sender(OutputStream out)
    {
        this.source = out;
    }

    public void sendExceptionInfo(String message)
    {
        byte[] b = message.getBytes();
        try
        {
            source.write(b);
            source.flush();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

package Terminal;

import java.io.OutputStream;          //Класс хранит объект outPutStream и записывает в этот поток информацию об исключениях

public class Sender {
   private OutputStream sender;

    public Sender(OutputStream out) {
this.sender = out;
    }

    public void sendGetMessageException(String message) {
    byte[] b = message.getBytes();
      try {
          sender.write(b);
     sender.flush();
      }catch (Exception e) {e.printStackTrace();}
      }


}

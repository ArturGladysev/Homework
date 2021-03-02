package Terminal;



import java.io.*;
import java.net.*;

//Сервер должен запускаться КАК ДРУГАЯ ЕДИНИЦА ТРАНСЛЯЦИИ (например, через командную строку)
// (В папке со скомпелированным сервером должны быть скомпелированные
//Sender и PinValidator - вспомогательные классы класса Server - папка -> resourse -> termibal -> server
public class Server {
private Sender sender;
    private FileOutputStream senderF;
    private ObjectOutputStream terminal_out;
   private  ObjectInputStream terminal_in;
 private final String pin_key = "7781";
private long balance = 10000l;
   private final PinValidator pin_validator = new PinValidator();

    //Метод для получения и отправки сообщений на терминал
   public void writeMessageToClient(String message) {
      try {
          terminal_out.writeObject(message);
      } catch (IOException e) { sender.sendGetMessageException(e.toString());
      }
  }

   public Long getBalance() {
      return balance;
  }
    //Пополнить бланс
public void upBalabce() {
    try {
        String ob = (String) terminal_in.readObject();
        Long l = Long.parseLong(ob);
        if (  l <= 0 || (l + balance) > Long.MAX_VALUE) {throw new IllegalArgumentException("Your amount is negative or too large, please enter the correct amount"); }
        else {
            balance+=l;
        }
    } catch (NumberFormatException eN)  {
        this.writeMessageToClient("Insert the number");
        sender.sendGetMessageException(eN.toString());
    }catch (IllegalArgumentException eP) {
        sender.sendGetMessageException(eP.toString());
        this.writeMessageToClient(eP.getMessage());
    } catch (IOException | ClassNotFoundException e) {sender.sendGetMessageException(e.toString()); }
}


    //Снять средства
  public void takeBalance() {
    try {
        String ob = (String) terminal_in.readObject();
        Long l = Long.parseLong(ob);
        if (l > balance || l < 100 || l < 0 || l > Long.MAX_VALUE) {throw new IllegalArgumentException("You entered too large or negative number"); }
   else if (l%100 != 0) {throw new IllegalArgumentException("The sum is not a multiple of 100, top up your balance"); }
    else {
        balance-=l;
      }
    } catch (NumberFormatException eN)  {
        this.writeMessageToClient("Insert the number");
        sender.sendGetMessageException(eN.toString());
    }
    catch (IllegalArgumentException eA) {
        sender.sendGetMessageException(eA.toString());
        this.writeMessageToClient(eA.getMessage());
    } catch (IOException | ClassNotFoundException e) {sender.sendGetMessageException(e.toString()); }
    }

    //Показать баланс
    public void showBalance() {
          String message_balance = "Current balance: " + Long.toString(balance);
          this.writeMessageToClient(message_balance);

   }
    //Валидация пин кода
public void testPin(Character ch) {
    try {
        pin_validator.validation(pin_key, ch);
        if (pin_validator.isvalid()) {
            this.writeMessageToClient("Validation");
        }

    if (pin_validator.isOverflow()) {
        throw new AccountIsLockedException("You exceeded the number of attempts");
    }
    } catch (IllegalArgumentException eA) {
        sender.sendGetMessageException(eA.toString());
        this.writeMessageToClient(eA.getMessage());
          }catch (AccountIsLockedException Ae ) {
        this.writeMessageToClient(Ae.getMessage());
        sender.sendGetMessageException(Ae.toString());

    }
 }


//Основной метод, отслеживает информацию с терминала, производит валидацию пин кода и
// обрабатывает запросы с терминала, вызывает методы сервера в зависимости от запроса
    public void go() {
      this.clientConnect();
 Object ob = null;
       try {
  while((ob = terminal_in.readObject())!= null) {
      System.out.println("Сервер получил объект " + ob.toString());
      Character ch = (Character) ob;
      if (!pin_validator.isvalid()) {
              this.testPin(ch);
      }

      System.out.println("Объект " + ob.toString());
      Character c = (Character) ob;
      if (c.equals('S')) {
          this.showBalance();
      } else if (c.equals('T')) {
          this.takeBalance();
      } else if (c.equals('U')) {
             this.upBalabce();
      }
  }

       } catch(IOException ex) {
               sender.sendGetMessageException(ex.toString());
            } catch(Exception e) { sender.sendGetMessageException(e.toString()); }

       }


    //Соединение с терминалом
    public static void main(String[] arg) {
        new Server().go();
    }

    public void clientConnect() {

        //В этот фай будет передоваться информация об исключениях
        try {
                senderF = new FileOutputStream("resourse/terminal/messages");
                sender = new Sender(senderF);
            ServerSocket server_sock = new ServerSocket(4242);
            Socket terminal_sock = server_sock.accept();
            terminal_out = new ObjectOutputStream(terminal_sock.getOutputStream());
            terminal_in = new ObjectInputStream(terminal_sock.getInputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
  

}
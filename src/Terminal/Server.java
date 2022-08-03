package Terminal;

import java.io.*;
import java.net.*;

/*Сервер должен запускаться как параллельная единица трансляции, например, через командную строку.
 В папке со скомпелированным сервером должны быть скомпелированные Sender и PinValidator -
 вспомогательные классы класса Server - папка -> resource -> terminal -> server.
 Информация об исключениях должна быть записана в файл.
 П.С. На самом деле - далеко не самое лучшее решение. Но на момент решения задачи знаниями более развитых
 технологий, чем общение посредством сокетов, я не обладал.
*/

public class Server

{
    private Sender sender;

    private FileOutputStream fileOutputStream;

    private ObjectOutputStream terminalObjectOutStream;

    private ObjectInputStream terminalObjectInStream;

    private final String pinCode = "7781";

    private long balance = 10000l;

    private final PinValidator pinValidator = new PinValidator();

    //Соединение с терминалом
    public static void main(String[] arg)
    {
        new Server().go();
    }

    //Метод для получения и отправки сообщений на терминал
    public void writeMessageToClient(String message)
    {
        try
        {
            terminalObjectOutStream.writeObject(message);
        }
        catch(IOException e)
        {
            sender.sendExceptionInfo(e.toString());
        }
    }

    public Long getBalance()
    {
        return balance;
    }


    public void upBalance()
    {
        try
        {
            String ob = (String)terminalObjectInStream.readObject();
            Long amount = Long.parseLong(ob);
            if(amount <= 0 || (amount + balance) > Long.MAX_VALUE)
            {
                throw new IllegalArgumentException("Your amount is negative or too large, please enter the correct amount");
            }
            else
            {
                balance += amount;
            }
        }
        catch(NumberFormatException en)
        {
            this.writeMessageToClient("Insert the number");
            sender.sendExceptionInfo(en.toString());
        }
        catch(IllegalArgumentException el)
        {
            sender.sendExceptionInfo(el.toString());
            this.writeMessageToClient(el.getMessage());
        }
        catch(IOException | ClassNotFoundException eio)
        {
            sender.sendExceptionInfo(eio.toString());
        }
    }

    //Снять средства
    public void takeAmount()
    {
        try
        {
            String ob = (String)terminalObjectInStream.readObject();
            Long amount = Long.parseLong(ob);
            if(amount > balance || amount < 100 || amount < 0 || amount > Long.MAX_VALUE)
            {
                throw new IllegalArgumentException("You entered too large or negative number");
            }
            else
                if(amount % 100 != 0)
                {
                    throw new IllegalArgumentException("The sum is not a multiple of 100, top up your balance");
                }
                else
                {
                    balance -= amount;
                }
        }
        catch(NumberFormatException en)
        {
            this.writeMessageToClient("Insert the number");
            sender.sendExceptionInfo(en.toString());
        }
        catch(IllegalArgumentException el)
        {
            sender.sendExceptionInfo(el.toString());
            this.writeMessageToClient(el.getMessage());
        }
        catch(IOException | ClassNotFoundException eio)
        {
            sender.sendExceptionInfo(eio.toString());
        }
    }

    public void showBalance()
    {
        String message_balance = "Current balance: " + balance;
        this.writeMessageToClient(message_balance);
    }

    public void testPin(Character ch)
    {
        try
        {
            pinValidator.validation(pinCode, ch);
            if(pinValidator.isValid())
            {
                this.writeMessageToClient("Validation");
            }
            if(pinValidator.isOverflow())
            {
                throw new AccountIsLockedException("You exceeded the number of attempts");
            }
        }
        catch(IllegalArgumentException el)
        {
            sender.sendExceptionInfo(el.toString());
            this.writeMessageToClient(el.getMessage());
        }
        catch(AccountIsLockedException ea)
        {
            this.writeMessageToClient(ea.getMessage());
            sender.sendExceptionInfo(ea.toString());
        }
    }


/*Основной метод отслеживает информацию с терминала, производит валидацию пин-кода,
  обрабатывает запросы с терминала, вызывает методы сервера в зависимости от запроса.
*/

    public void go()
    {
        this.clientConnect();
        Object ob;
        try
        {
            while((ob = terminalObjectInStream.readObject()) != null)
            {
                System.out.println("Сервер получил объект " + ob);
                Character ch = (Character)ob;
                if(!pinValidator.isValid())
                {
                    this.testPin(ch);
                }
                System.out.println("Объект " + ob);
                Character mark = (Character)ob;
                if(mark.equals('S'))
                {
                    this.showBalance();
                }
                else
                    if(mark.equals('T'))
                    {
                        this.takeAmount();
                    }
                    else
                        if(mark.equals('U'))
                        {
                            this.upBalance();
                        }
            }
        }
        catch(IOException ex)
        {
            sender.sendExceptionInfo(ex.toString());
        }
        catch(Exception e)
        {
            sender.sendExceptionInfo(e.toString());
        }
    }

    //В файл по переданному в fileOutputStream пути будет передаваться информация об исключениях
    public void clientConnect()
    {
        try
        {
            fileOutputStream = new FileOutputStream("resource/terminal/messages");
            sender = new Sender(fileOutputStream);
            ServerSocket serverSocket = new ServerSocket(4242);
            Socket terminalSocket = serverSocket.accept();
            terminalObjectOutStream = new ObjectOutputStream(terminalSocket.getOutputStream());
            terminalObjectInStream = new ObjectInputStream(terminalSocket.getInputStream());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
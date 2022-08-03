package Terminal;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/*Задача: написать простое клиент-серверное приложение с графическим терминалом.
 Приложение должно предлагать пользователю ввести пин-код для авторизации
 (пин-код можно просто хранить как строку сервера).
 После авторизации пользователь может работать со своим счетом:
 посмотреть, снять средства, положить средства.
 Использовать Spring нельзя, приложение должно быть работать через сокеты или сервлеты.
 */

public class Terminal
{
    public static void main(String[] args)
    {
        new Terminal().go();
    }

    private MessageReader messageReader;

    private ObjectOutputStream serverObjectOutStream;

    private ObjectInputStream serverObjectInStream;

    private JFrame frame;

    private JPanel panel;

    private JTextArea inputMessage;

    private JTextField outputMessage;

    private Box buttonBox;

    private JPanel buttonPanel;

    private JButton enterPin;

    private EnterPinListener enterPinListener = new EnterPinListener();

    //Запуск основных методов
    public void go()
    {
        connectServer();
        buildGUI();
    }

    //Метод для блокировки потока
    public void sleep()
    {
        try
        {
            Thread.sleep(10000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    //Настройка соединения с сервером
    public void connectServer()
    {
        try
        {
            Socket socket = new Socket("127.0.0.1", 4242);
            serverObjectOutStream = new ObjectOutputStream(socket.getOutputStream());
            serverObjectInStream = new ObjectInputStream(socket.getInputStream());
        }
        catch(Exception ex)
        {
            System.out.println("Not connected");
        }
    }

    //Построение графического интерфейса
    public void buildGUI()
    {
        frame = new JFrame("Терминал");
        JButton topUp = new JButton("Пополнить баланс");
        topUp.addActionListener(new TopUpListener());
        JButton takeOf = new JButton("Снять средства");
        takeOf.addActionListener(new TakeOfListener());
        JButton show = new JButton("Показать баланс");
        show.addActionListener(new ShowListener());
        enterPin = new JButton("Ввести цифру");
        enterPin.addActionListener(enterPinListener);
        panel = new JPanel();
        buttonBox = new Box(BoxLayout.Y_AXIS);
        inputMessage = new JTextArea(3, 10);
        outputMessage = new JTextField(30);
        inputMessage.setText("Введите пин код:");
        inputMessage.setEditable(false);
        outputMessage.setDocument(new LengthRestrictedDocument(1));
        buttonBox.add(inputMessage);
        buttonBox.add(outputMessage);
        buttonBox.add(enterPin);
        buttonPanel = new JPanel();
        panel.add(buttonBox);
        buttonPanel.add(topUp);
        buttonPanel.add(takeOf);
        buttonPanel.add(show);
        frame.getContentPane().add(panel);
        frame.setSize(200, 200);
        frame.pack();
        frame.setVisible(true);
        try
        {
            messageReader = new MessageReader();
            messageReader.run();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //Слушатели для кнопок
    public class EnterPinListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ev)
        {
            inputMessage.setText("Введите пин код: ");
            String s = outputMessage.getText();
            outputMessage.setText(" ");
            outputMessage.setDocument(new LengthRestrictedDocument(1));
            System.out.println(s);
            try
            {
                serverObjectOutStream.writeObject(s.charAt(0));
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public class TopUpListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ev)
        {
            inputMessage.setText(" ");
            String up = outputMessage.getText();
            outputMessage.setText(" ");
            outputMessage.setDocument(new LengthRestrictedDocument(16));
            try
            {
                serverObjectOutStream.writeObject('U');
                serverObjectOutStream.writeObject(up);
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public class TakeOfListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ev)
        {
            inputMessage.setText(" ");
            String take = outputMessage.getText();
            outputMessage.setText(" ");
            outputMessage.setDocument(new LengthRestrictedDocument(16));
            try
            {
                serverObjectOutStream.writeObject('T');
                serverObjectOutStream.writeObject(take);
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public class ShowListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ev)
        {
            outputMessage.setText(" ");
            outputMessage.setDocument(new LengthRestrictedDocument(16));
            try
            {
                serverObjectOutStream.writeObject('S');
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }



    //Метод для ограничения кол-ва символов в JTextField
    public final class LengthRestrictedDocument extends PlainDocument
    {
        private final int limit;

        public LengthRestrictedDocument(int limit)
        {
            this.limit = limit;
        }

        @Override
        public void insertString(int offs, String str, AttributeSet a)
          throws BadLocationException
        {
            if(str == null)
                return;
            if((getLength() + str.length()) <= limit)
            {
                super.insertString(offs, str, a);
            }
        }
    }

    // Поток для отслеживания сообщений с сервера
    public class MessageReader implements Runnable
    {
        Object object = null;

        @Override
        public void run()
        {
            try
            {
                while((object = serverObjectInStream.readObject()) != null)
                {
                    String st = (String)object;
                    System.out.println("Терминал получил объект " + st);
                    if(st.equals("Validation"))
                    {
                        enterPin.setVisible(false);
                        buttonBox.add(buttonPanel);
                        outputMessage.setDocument(new LengthRestrictedDocument(16));
                        inputMessage.setText(" ");
                        frame.pack();
                    }
                    else
                        if(st.equals("You exceeded the number of attempts"))
                        {
                            inputMessage.setText(st);
                            enterPin.removeActionListener(enterPinListener);
                            sleep();
                            enterPin.addActionListener(enterPinListener);
                        }
                        else
                            inputMessage.setText(st);
                }
            }
            catch(Exception e)
            {
            }
        }
    }
}
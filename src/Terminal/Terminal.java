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

public class Terminal {
    private ReaderMessage reader_message;
    private ObjectOutputStream server_out;
    private  ObjectInputStream server_in;
    private JFrame frame;
    private JPanel panel;
    private JTextArea in_message;
    private JTextField out_message;
    private Box buttonBox;
    private JPanel but;
    private JButton enter_pin;
    private EnterPinListener pin_listener = new EnterPinListener();

    //Запуск основных методов
    public void go() {
        connectServer();
        buildGUI();
    }
    //Метод для блокировки потока
public void mainSleep() {
        try {
            Thread.sleep(10000);
        }catch (InterruptedException e) {e.printStackTrace(); }
}

//Настройка соеденения с сервером
    public void connectServer() {
    try {
        Socket sock = new Socket("127.0.0.1", 4242);
        server_out = new ObjectOutputStream(sock.getOutputStream());
        server_in = new ObjectInputStream(sock.getInputStream());
    } catch (Exception ex) {
        System.out.println("Not connected");
    }
}

//Постороение графического интерфейса
    public void buildGUI() {
        frame = new JFrame("Терминал");

        JButton top_up = new JButton("Пополнить баланс");
        top_up.addActionListener(new TopUpListener());

        JButton take_of = new JButton("Cнять средства");
        take_of.addActionListener(new TakeOfListener());

        JButton show = new JButton("Показать баланс");
        show.addActionListener(new ShowListener());

        enter_pin = new JButton("Ввести цифру");
        enter_pin.addActionListener(pin_listener);

        panel = new JPanel();
        buttonBox = new Box(BoxLayout.Y_AXIS);
        in_message = new JTextArea(3, 10);
        out_message = new JTextField(30);
        in_message.setText("Введите пин код:");
        in_message.setEditable(false);
        out_message.setDocument(new LengthRestrictedDocument(1));
        buttonBox.add(in_message);
        buttonBox.add(out_message);
        buttonBox.add(enter_pin);
        but = new JPanel();
        panel.add(buttonBox);
        but.add(top_up);
        but.add(take_of);
        but.add(show);


        frame.getContentPane().add(panel);
        frame.setSize(200, 200);
        frame.pack();
        frame.setVisible(true);
        try {
             reader_message = new ReaderMessage();
            reader_message.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Слушатели для кнопок
    public class EnterPinListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            in_message.setText("Введите пин код: ");
            String s = out_message.getText();
            out_message.setText(" ");
            out_message.setDocument(new LengthRestrictedDocument(1) );
            System.out.println(s);
            try {

                server_out.writeObject(s.charAt(0));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class TopUpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            in_message.setText(" ");
            String up = out_message.getText();
            out_message.setText(" ");
            out_message.setDocument(new LengthRestrictedDocument(16) );
            try {
                server_out.writeObject('U');
                server_out.writeObject(up);
            } catch (IOException ex) {
                ex.printStackTrace();
                ;
            }
        }
    }

    public class TakeOfListener implements ActionListener {
        @Override
        public void actionPerformed( ActionEvent ev) {
            in_message.setText(" ");
            String take = out_message.getText();
            out_message.setText(" ");
            out_message.setDocument(new LengthRestrictedDocument(16) );
            try {
                server_out.writeObject('T');
                server_out.writeObject(take);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class ShowListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            out_message.setText(" ");
            out_message.setDocument(new LengthRestrictedDocument(16) );
            try {
                server_out.writeObject('S');
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        new Terminal().go();
    }

    //Метод для ограничения кол-ва символов в JTextfield
    public final class LengthRestrictedDocument extends PlainDocument {

        private final int limit;

        public LengthRestrictedDocument(int limit) {
            this.limit = limit;
        }
        @Override
        public void insertString(int offs, String str, AttributeSet a)
                throws BadLocationException {
            if (str == null)
                return;
            if ((getLength() + str.length()) <= limit) {
                super.insertString(offs, str, a);
            }
        }
    }

    // Поток для отслеживания сообщений с сервера
    public class ReaderMessage implements Runnable {
        Object ob = null;
        @Override
        public void run() {
            try {
                while ((ob = server_in.readObject()) != null) {
                   String st = (String) ob;
                    System.out.println("Терминал получил объект " + st);
               if (st.equals("Validation")) {
                   enter_pin.setVisible(false);
                   buttonBox.add(but);
                   out_message.setDocument(new LengthRestrictedDocument(16) );
                   in_message.setText(" ");
                   frame.pack();
               }
                 else if (st.equals("You exceeded the number of attempts")) {
                   in_message.setText(st);
                   enter_pin.removeActionListener(pin_listener);
                     mainSleep();
                   enter_pin.addActionListener(pin_listener);
                    }
               else in_message.setText(st);
                }
            } catch(Exception e){}

        }
    }
}
package Shapes;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Scanner;



/* Задача: реализовать иерархию классов Shapes:
 каждая фигура должна переопределять метод getArea и хранить имя своего класса.
 Позволяем пользовтелю ввести значения для фигур, инициализируем
 экземпляры класса Main (потомки класса: Shape). Создаем объект вложенного
 класса - наследника JPanel, используем поля созданных объектов для рисованя
 фигур.
*/

public class Main

{
    private static JFrame frame;

    private static Circle circle;

    private static Square square;

    private static Rectangle rectangle;

    private static Triangle triangle;

    public static void main(String[] args)
    {
        System.out.println("Необходимо ввести координаты фигур в диапазоне (x:600 , y:600) ~ " + "\n"
          + "Введите радиус круга >");
        Scanner scan = new Scanner(System.in);
        double r = scan.nextDouble();
        circle = new Circle(r);
        System.out.println("Введите длину и ширину прямоугольника >");
        double l = scan.nextInt();
        double w = scan.nextInt();
        rectangle = new Rectangle(l, w);
        System.out.println("Введите длину ребер квадрата >");
        double s = scan.nextInt();
        square = new Square(s);
        System.out.println("Введите координаты вершин треугольника >");
        Point a = new Point();
        Point b = new Point();
        Point c = new Point();
        a.x = scan.nextInt();
        a.y = scan.nextInt();
        b.x = scan.nextInt();
        b.y = scan.nextInt();
        c.x = scan.nextInt();
        c.y = scan.nextInt();
        triangle = new Triangle(a, b, c);
        DrawPanel drawPanel = new DrawPanel();
        frame = new JFrame();
        frame.add(drawPanel);
        frame.setSize(600, 600);
        frame.setVisible(true);
    }

    public static class DrawPanel extends JPanel
    {
        public void paintComponent(Graphics g)
        {
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, 600, 600);
            g.setColor(Color.GRAY);
            Graphics2D g2 = (Graphics2D)g;
            Ellipse2D.Double drawCircle = new Ellipse2D.Double(10, 200, circle.getRadius() / 2, circle.getRadius() / 2);
            g2.fill(drawCircle);
            g.setColor(Color.RED);
            g.fillRect(10, 10, (int)rectangle.getLength(), (int)rectangle.getWidth());
            g.setColor(Color.GREEN);
            g.fillRect(200, 10, (int)square.getSide(), (int)square.getSide());
            g.setColor(Color.BLACK);
            g.drawLine((int)triangle.getAPoint().getX(), (int)triangle.getAPoint().getY(), (int)triangle.getBPoint().getX(),
              (int)triangle.getBPoint().getY());
            g.drawLine((int)triangle.getBPoint().getX(), (int)triangle.getBPoint().getY(), (int)triangle.getCPoint().getX(),
              (int)triangle.getCPoint().getY());
            g.drawLine((int)triangle.getCPoint().getX(), (int)triangle.getCPoint().getY(), (int)triangle.getAPoint().getX(),
              (int)triangle.getAPoint().getY());
        }
    }
}

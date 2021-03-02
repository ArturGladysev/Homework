package Shapes;

//Класс: Квадрат - хранит длинну ребра и имя
public class Square extends Shape {
    private static final String NAME = "Square";
    private double side;

    public Square(double side) {
        setSide(side);
    }


    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public double getArea() {
        return side * side;
    }




    public double getSide() {
        return side;
}

    public void setSide(double side) {
        this.side = side;
    }



}
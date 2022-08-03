package Shapes;

public class Square extends Shape
{
    private double side;

    public Square(double side)
    {
        setName("Square");
        setSide(side);
    }

    @Override
    public double getArea()
    {
        return side * side;
    }

    public double getSide()
    {
        return side;
    }

    public void setSide(double side)
    {
        this.side = side;
    }
}
package Shapes;

public class Rectangle extends Shape
{
    private double length;

    private double width;

    public Rectangle(double length, double width)
    {
        setName("Rectangle");
        setLength(length);
        setWidth(width);
    }

    @Override
    public double getArea()
    {
        return length * width;
    }

    public void setLength(double length)
    {
        this.length = length;
    }

    public void setWidth(double width)
    {
        this.width = width;
    }

    public double getLength()
    {
        return length;
    }

    public double getWidth()
    {
        return width;
    }
}

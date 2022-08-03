package Shapes;


public class Circle extends Shape
{
    private double radius;

    public Circle(double radius)
    {
        setName("Circle");
        setRadius(radius);
    }

    @Override
    public double getArea()
    {
        return Math.PI * (radius * radius);
    }

    public double getRadius()
    {
        return radius;
    }

    public double setRadius(double radius)
    {
        return this.radius = radius;
    }
}

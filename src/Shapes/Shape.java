package Shapes;

public abstract class Shape
{
    private String name;

    public String getName()
    {
        return name;
    }

    protected void setName(String name)
    {
        this.setName(name);
    }

    public abstract double getArea();
}

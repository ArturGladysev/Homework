package Shapes;

import java.awt.*;

public class Triangle extends Shape
{
    private Point aPoint;

    private Point bPoint;

    private Point cPoint;

    public Triangle(Point aPoint, Point bPoint, Point cPoint)
    {
        setName("Triangle");
        setAPoint(aPoint);
        setBPoint(bPoint);
        setCPoint(cPoint);
    }

    @Override
    public double getArea()
    {
        double area = 0.5 * (aPoint.getX() - cPoint.getX()) * (bPoint.getY() - cPoint.getY()) - (bPoint.getX() - cPoint.getX())
          * (aPoint.getY() - cPoint.getY());
        return area;
    }

    public Point getAPoint()
    {
        return aPoint;
    }

    public void setAPoint(Point aPoint)
    {
        this.aPoint = aPoint;
    }

    public Point getBPoint()
    {
        return bPoint;
    }

    public void setBPoint(Point bPoint)
    {
        this.bPoint = bPoint;
    }

    public void setCPoint(Point cPoint)
    {
        this.cPoint = cPoint;
    }

    public Point getCPoint()
    {
        return cPoint;
    }
}
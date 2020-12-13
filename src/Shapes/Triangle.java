package Shapes;

import java.awt.*;                                            //Класс треугольник, хранит три вершины треугольника и имя

public class Triangle extends Shape {
    private Point a_point;
    private Point b_point;
    private Point c_point;
    private static final String NAME = "Triangle";

    public Triangle(Point a_point, Point b_point,  Point c_point) {
        setAPoint(a_point);
        setBPoint(b_point);
        setCPoint(c_point);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public double getArea() {
        double area = 0.5*(a_point.getX() - c_point.getX()) *(b_point.getY() - c_point.getY()) -(b_point.getX() -c_point.getX())
                * (a_point.getY() - c_point.getY());
        return area;
    }

    public Point getAPoint() {
        return a_point;
    }

    public void setAPoint(Point a_point) {
        this.a_point = a_point;
    }

    public Point getBPoint() {
        return b_point;
    }

    public void setBPoint(Point b_point) {
        this.b_point = b_point;
    }

    public void setCPoint(Point c_point) {
        this.c_point = c_point;
    }
    public Point getCPoint() {
        return c_point;
    }

}
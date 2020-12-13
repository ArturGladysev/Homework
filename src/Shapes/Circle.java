package Shapes;

public class Circle extends Shape {                                        //Класс: круг - хранит радиус и имя
    private static final String NAME = "Circle";
  private double radius;

   public Circle(double radius) {
       setRadius(radius);
   }

    @Override
    public String getName() {
        return NAME;
    }

        @Override
        public double getArea() {
            return Math.PI * (radius * radius);
        }

    public double getRadius() {
        return radius;
    }

    public double setRadius(double radius) {
            return this.radius = radius;
   }
}

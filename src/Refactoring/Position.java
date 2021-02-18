package Refactoring;

public class Position {
private int x = 0;
private int y = 0;
    private int max_x = 5;
    private int max_y = 5;

    public Position(int max_x, int max_y) {
        if(max_x > 600 || max_x < 0 || max_y >400 || max_y <0) {throw new IllegalArgumentException();}
        this.max_x = max_x;
        this.max_y = max_y;
    }

    public int getMax_X() {
        return max_x;
    }


    public int getMax_y() {
        return max_y;
    }


    public int getX() {
        return x;
    }

    public Position changeX(int x) {
      if((this.x + x) > max_x || (this.x + x) < 0) throw new TractorInDitchException("Трактор вышел за пределы допустимого диапазона");
        this.x += x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Position changeY(int y) {
        if((this.y + y) > max_y || (this.y + y) < 0) throw new TractorInDitchException("Трактор вышел за пределы допустимого диапазона");
        this.y += y;
    return this;
    }
}

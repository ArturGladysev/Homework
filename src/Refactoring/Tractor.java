package Refactoring;


public class Tractor {

    private Position position;
    private Orientation orientation = Orientation.NORTH;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Tractor(Position position, Orientation orientation) {
        this.position = position;
        this.orientation = orientation;
    }

    public void move(Command command) {
        command.excute(this);

    }

    public int getPositionX() {
        return position.getX();
    }

    public int getPositionY() {
        return position.getY();
    }

    public Orientation getOrientation() {
        return orientation;

    }



}

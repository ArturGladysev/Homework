package Refactoring;

public class Main {
    public static void main(String[] args) {
        Tractor tractor = new Tractor(new Position(10, 10), Orientation.NORTH);
        tractor.move(new MoveForward());
        System.out.println("Трактор сдвинулся " + tractor.getPositionX()+ " " + tractor.getPositionY());
        tractor.move(new MoveForward());
        System.out.println("Трактор сдвинулся " + tractor.getPositionX()+ " " + tractor.getPositionY());
        tractor.move(new MoveForward());
        System.out.println("Трактор сдвинулся " + tractor.getPositionX()+ " " + tractor.getPositionY());
        tractor.move(new TurnClockwise());
        System.out.println("Трактор повернул на " + tractor.getOrientation() );
        tractor.move(new MoveForward());
        System.out.println("Трактор сдвинулся " + tractor.getPositionX()+ " " + tractor.getPositionY());
        tractor.move(new TurnClockwise());
        System.out.println("Трактор повернул на " + tractor.getOrientation() );
        tractor.move(new MoveForward());
        System.out.println("Трактор сдвинулся " + tractor.getPositionX()+ " " + tractor.getPositionY());
        tractor.move(new TurnClockwise());
        System.out.println("Трактор повернул на " + tractor.getOrientation() );
    }

}

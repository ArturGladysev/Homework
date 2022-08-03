package Refactoring;

/* Задача заключалась в рефакторинге сильно связанного кода с
   неудачно подобранными именами переменных. Результат рефакторинга: классы
   взаимодействуют через абстракции, использованы паттерны проектирования:
   Command и State.
*/

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

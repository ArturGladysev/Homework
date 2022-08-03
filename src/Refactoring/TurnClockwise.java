package Refactoring;

public class TurnClockwise implements Command{
    @Override
    public void execute(Tractor tractor) {
        tractor.setOrientation(tractor.getOrientation().turn());
    }
}

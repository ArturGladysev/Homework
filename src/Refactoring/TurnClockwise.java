package Refactoring;

public class TurnClockwise implements Command{
    @Override
    public void excute(Tractor tractor) {
        tractor.setOrientation(tractor.getOrientation().turn());
    }
}

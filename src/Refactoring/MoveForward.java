package Refactoring;

public class MoveForward implements Command
{
    @Override
    public void execute(Tractor tractor)
    {
        tractor.setPosition(tractor.getOrientation().move(tractor.getPosition()));
    }
}


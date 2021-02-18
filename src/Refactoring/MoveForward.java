package Refactoring;

public class MoveForward implements Command{

            @Override
            public void excute(Tractor tractor) {
                tractor.setPosition(tractor.getOrientation().move(tractor.getPosition())) ;
            }
        }


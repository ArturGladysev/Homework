package MultiThread2;

import java.util.*;

public interface Context {

 int getCompletedTaskCount();

int getFailedTaskCount();

int getInterruptedTaskCount();

void interrapt();

boolean isFinished();


}

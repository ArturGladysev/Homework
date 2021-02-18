package MultiThread2;

import java.util.concurrent.*;

public interface ExecutionManager {

  Context excute (Runnable callback, Runnable...tasks);


}

package MultiThread2.ExecutionManager;

public interface Context
{
 int getCompletedTaskCount();

 int getFailedTaskCount();

 int getInterruptedTaskCount();

 void interrupt();

 boolean isFinished();
}

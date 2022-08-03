package MultiThread2.ExecutionManager;

public interface ExecutionManager
{
  Context execute(Runnable callback, Runnable... tasks);
}

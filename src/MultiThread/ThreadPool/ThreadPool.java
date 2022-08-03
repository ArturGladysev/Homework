package MultiThread.ThreadPool;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class ThreadPool
{
    protected volatile boolean isRun = true;

    protected volatile ArrayList<Thread> threadData;

    protected volatile LinkedList<Runnable> tasks = new LinkedList<>();

    public abstract void start();

    public abstract void execute(Runnable runnable);
}

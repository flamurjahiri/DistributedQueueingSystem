package queues.tasks;

public interface MyTask {
    boolean execute();

    boolean doWhenFail();
}

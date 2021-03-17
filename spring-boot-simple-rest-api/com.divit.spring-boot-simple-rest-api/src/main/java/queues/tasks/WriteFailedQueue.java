package queues.tasks;


import queues.databases.H2BaseRepository;
import queues.failedQueues.FailedQueueBO;
import queues.models.Queue;

import java.util.List;

public class WriteFailedQueue implements MyTask {


    @Override
    public boolean execute() {
        FailedQueueBO failedQueueBO = new FailedQueueBO();
        List<Queue> failedQueue = failedQueueBO.getCurrentlyFailedQueues();
        if (failedQueue == null) return false;
        failedQueueBO.add(failedQueue);
        for (int i = 0; i < failedQueue.size(); i++) {
            System.out.println(failedQueue.get(i).getId() + "id ");
            H2BaseRepository.delete(failedQueue.get(i).getId());
        }
        return true;
    }

    @Override
    public boolean doWhenFail() {
        return false;
    }
}

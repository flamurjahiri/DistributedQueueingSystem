package queues.tasks;

import queues.databases.H2BaseRepository;
import queues.finishedQueues.FinishedQueueBO;
import queues.models.Queue;

import java.util.List;

public class WriteFinishedQueue implements MyTask {
    @Override
    public boolean execute() {
        FinishedQueueBO finishedQueueBO = new FinishedQueueBO();
        List<Queue> finishedQueues = finishedQueueBO.getCurrentlyFinishedQueues();
        if (finishedQueues == null) return false;
        finishedQueueBO.add(finishedQueues);
        finishedQueues.forEach(queue -> H2BaseRepository.delete(queue.getId()));
        return true;
    }

    @Override
    public boolean doWhenFail() {
        return false;
    }
}

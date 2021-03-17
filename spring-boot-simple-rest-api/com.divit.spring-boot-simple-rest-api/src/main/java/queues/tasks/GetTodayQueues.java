package queues.tasks;

import queues.utils.Converter;
import queues.databases.H2BaseRepository;
import queues.models.Queue;
import queues.queue.business.QueueBO;


import java.util.List;

public class GetTodayQueues implements MyTask {
    @Override
    public boolean execute() {
        QueueBO queueBO = new QueueBO();
        List<Queue> queues = queueBO.getTodayQueues();
        for (int i = 0; i < queues.size(); i++) {
            H2BaseRepository.add(Converter.convertQueue(queues.get(i)));
            queueBO.delete(queues.get(i).getId());
        }
        return true;
    }

    @Override
    public boolean doWhenFail() {
        return false;
    }
}

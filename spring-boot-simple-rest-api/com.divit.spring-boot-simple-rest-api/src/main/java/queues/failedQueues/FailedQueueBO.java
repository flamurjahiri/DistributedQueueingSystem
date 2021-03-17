package queues.failedQueues;

import queues.databases.H2BaseRepository;
import queues.models.H2Queue;
import queues.models.Queue;
import queues.models.QueueType;
import queues.utils.Converter;

import java.util.ArrayList;
import java.util.List;

public class FailedQueueBO {
    FailedQueueRepo repository;

    public FailedQueueBO() {
        repository = new FailedQueueRepo();
    }

    public Queue add(Queue queue) {
        if(queue == null) return null;
        queue.setType(QueueType.FAILED);
        return repository.add(queue);
    }

    public boolean add(List<Queue> queues) {
        if(queues.isEmpty()) return false;
        queues.forEach(queue -> queue.setType(QueueType.FAILED));
        return repository.add(queues);
    }

    public List<Queue> getCurrentlyFailedQueues() {
        H2BaseRepository h2BaseRepository = new H2BaseRepository();
        List<H2Queue> h2Queues = H2BaseRepository.find("SELECT * FROM QUEUE WHERE type = 'FAILED'");
        if (h2Queues.isEmpty()) return new ArrayList<>();
        List<Queue> queues = new ArrayList<>();
        for (int i = 0; i < h2Queues.size(); i++) {
            queues.add(Converter.convertQueue(h2Queues.get(i)));
        }
        return queues;
    }
}

package queues.failedQueues;

import queues.databases.BaseRepository;
import queues.models.Queue;
import queues.utils.Mongo;

import java.util.List;

public class FailedQueueRepo extends BaseRepository<Queue> {

    public FailedQueueRepo() {
        super(Mongo.getCollection("FailedQueue", Queue.class));
    }

    public List<Queue> getFailedQueues(){
        return find();
    }
}

package queues.finishedQueues;

import queues.databases.BaseRepository;
import queues.models.Queue;
import queues.utils.Mongo;

import java.util.List;

public class FinishedQueueRepo extends BaseRepository<Queue> {
    public FinishedQueueRepo() {
        super(Mongo.getCollection("FinishedQueue", Queue.class));
    }

    public List<Queue> getFinishedQueues(){
        return find();
    }
}

package queues.queue.repository;

import queues.databases.BaseRepository;
import queues.models.Queue;
import queues.utils.Converter;
import queues.utils.Mongo;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.conversions.Bson;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class QueueRepository extends BaseRepository<Queue> {


    public QueueRepository() {
        super(Mongo.getCollection("Queues", Queue.class));
    }

    public List<Queue> getTodaysQueues() {
        Bson filter = Filters.and(
                Filters.gte("executionDate", new Date()),
                Filters.lte("executionDate", Converter.localDateToDate(LocalDateTime.now().plusDays(1)))
        );
        return find(filter, Sorts.descending("executionDate", "createdAt"));
    }

    public List<Queue> getQueues(){
        return find();
    }

}

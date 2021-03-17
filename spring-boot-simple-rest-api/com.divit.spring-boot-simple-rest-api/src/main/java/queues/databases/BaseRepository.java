package queues.databases;


import queues.models.BaseEntity;
import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseRepository<Entity extends BaseEntity> {
    protected MongoCollection<Entity> collection;

    public BaseRepository(MongoCollection<Entity> collection) {
        this.collection = collection;
    }

    public Entity add(Entity entity) {
        if (entity.getId() == null || entity.getId().isEmpty()) entity.setId(new ObjectId().toString());

        entity.setCreatedAt(new Date());
        return MongoUtils.add(collection, entity);
    }

    public boolean add(List<Entity> entities) {
        entities.forEach(entity -> {
            if (entity.getId() == null || entity.getId().isEmpty()) entity.setId(new ObjectId().toString());
            entity.setCreatedAt(new Date());
        });
        return MongoUtils.add(collection, entities);
    }

    public Entity update(String id, Entity entity) {
        entity.setUpdatedAt(new Date());
        return MongoUtils.update(collection, id, entity);
    }

    public Entity update(String id, Bson update) {
        return MongoUtils.update(collection, id, update);
    }

    public boolean delete(String id) {
        return MongoUtils.delete(collection, id);
    }

    public Entity getById(String id) {
        return MongoUtils.getById(collection, id);
    }

    public List<Entity> find(Bson filter, Bson sort) {
        if (sort == null) {
            return collection.find(filter).into(new ArrayList<>());
        }
        return collection.find(filter).sort(sort).into(new ArrayList<>());
    }

    public List<Entity> find(){
        return collection.find().into(new ArrayList<>());
    }
}

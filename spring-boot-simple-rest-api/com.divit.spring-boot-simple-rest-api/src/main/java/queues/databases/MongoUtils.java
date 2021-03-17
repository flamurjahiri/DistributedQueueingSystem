package queues.databases;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.conversions.Bson;

import java.util.List;

public class MongoUtils {

    public static <Entity> Entity add(MongoCollection<Entity> collection, Entity entity) {
        try {
            InsertOneResult insertOneResult = collection.insertOne(entity);
            if (insertOneResult.wasAcknowledged()) {
                return entity;
            } else {
                throw new Exception("Cannot insert");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static <Entity> boolean add(MongoCollection<Entity> collection, List<Entity> entities) {
        try {
            InsertManyResult insertManyResult = collection.insertMany(entities);
            return insertManyResult.wasAcknowledged();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static <Entity> Entity update(MongoCollection<Entity> collection, String id, Entity entity) {
        try {
            UpdateResult updateResult = collection.replaceOne(Filters.eq("_id", id), entity);
            if (updateResult.wasAcknowledged()) {
                return entity;
            } else {
                throw new Exception("Cannot update");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static <Entity> Entity update(MongoCollection<Entity> collection, String id, Bson updates) {
        return collection.findOneAndUpdate(Filters.eq("_id", id), updates);
    }


    public static <Entity> boolean delete(MongoCollection<Entity> collection, String id) {
        try {
            DeleteResult deleteResult = collection.deleteOne(Filters.eq("_id", id));
            if (deleteResult.wasAcknowledged()) {
                return true;
            } else {
                throw new Exception("Cannot update");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static <Entity> Entity getById(MongoCollection<Entity> collection, String id) {
        FindIterable<Entity> findIterable = collection.find(Filters.eq("_id", id));
        if (findIterable.iterator().hasNext()) {
            return findIterable.iterator().next();
        }
        return null;
    }


}

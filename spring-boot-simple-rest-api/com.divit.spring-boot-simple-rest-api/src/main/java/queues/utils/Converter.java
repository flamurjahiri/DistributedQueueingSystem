package queues.utils;

import queues.models.H2Queue;
import queues.models.Queue;
import queues.models.QueueType;
import queues.models.TaskType;
import queues.tasks.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Random;


public class Converter {
    public static Date localDateToDate(LocalDateTime localDateTime) {
        ZonedDateTime zdtTimeBegin = localDateTime.atZone(ZoneId.systemDefault());
        Date registerTimeBeginDate = Date.from(zdtTimeBegin.toInstant());

        ZonedDateTime zdtTimeEnd = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdtTimeEnd.toInstant());
    }

    public static H2Queue convertQueue(Queue q) {
        return new H2Queue(q.getId(), q.getExecutionDate(), q.getCity(), q.getType(), q.getCreatedAt(), q.getTask());
    }

    public static QueueType convertQueueType(String str) {
        switch (str) {
            case "NEW":
                return QueueType.NEW;
            case "PROCESSING":
                return QueueType.PROCESSING;
            case "FINISHED":
                return QueueType.FINISHED;
            case "RETRYING":
                return QueueType.RETRYING;
            case "FAILED":
                return QueueType.FAILED;
        }
        return null;
    }

    public static TaskType convertTaskType(String task) {

        return TaskType.valueOf(task);
       /* switch (task) {
            case "PrimeNumbersWritter":
                return PrimeNumberWritter;
            case "GetTodayQueues":
                return GetTodayQueues;
            case "WriteToFile":
                return TaskType.WriteToFile;
            case "ReadFromFile":
                return TaskType.ReadFromFile;
            case "WriteFinishedQueue":
                return TaskType.WriteFinishedQueue;
            case "WriteFailedQueue":
                return TaskType.WriteFailedQueue;
        }*/

    }

    public static TaskType randomTask() {
        TaskType[] taskTypes = TaskType.values();
        Random generator = new Random();
        return taskTypes[generator.nextInt(taskTypes.length)];
    }

    public static Date convertDate(String date) {
        if(date == null) return new Date();
        try {
            return new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Queue convertQueue(H2Queue queue) {
        Queue q = new Queue(queue.getExecutionDate(), queue.getCity(), queue.getType(), queue.getTasks());
        q.setCreatedAt(queue.getCreatedAt());
        q.setId(queue.getId());
        return q;
    }

    public static MyTask getTask(String task) {
        switch (task) {
            case "PrimeNumbersWritter":
                return new PrimeNumbersWritter();
            case "WriteToFile":
                return new WriteToFile();
            case "GetTodayQueues":
                return new GetTodayQueues();
            case "ReadFromFile":
                return new ReadFromFile();
            case "WriteFinishedQueue":
                return new WriteFinishedQueue();
            case "WriteFailedQueue":
                return new WriteFailedQueue();
        }
        return null;
    }

}

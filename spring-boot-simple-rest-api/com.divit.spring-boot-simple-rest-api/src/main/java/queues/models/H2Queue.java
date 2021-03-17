package queues.models;

import java.io.Serializable;
import java.util.Date;

public class H2Queue implements Serializable {
    String id;
    Date executionDate;
    String city;
    QueueType type;
    Date createdAt;
    TaskType task;

    public H2Queue() {
    }

    public H2Queue(String id, Date executionDate, String city, QueueType type, Date createdAt, TaskType task) {
        this.id = id;
        this.executionDate = executionDate;
        this.city = city;
        this.type = type;
        this.createdAt = createdAt;
        this.task = task;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public TaskType getTasks() {
        return task;
    }

    public void setTasks(TaskType task) {
        this.task = task;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public QueueType getType() {
        return type;
    }

    public void setType(QueueType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "QueueTest{" +
                "id='" + id + '\'' +
                ", executionDate=" + executionDate +
                ", city='" + city + '\'' +
                ", type=" + type +
                ", createdAt=" + createdAt +
                ", tasks=" + task +
                '}';
    }
}

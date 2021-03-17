package queues.models;

import java.io.Serializable;
import java.util.Date;

public class Queue extends BaseEntity implements Serializable {
    public TaskType task;
    private Date executionDate;
    private String city;
    private QueueType type;

    public Queue() {
    }

    public Queue(Date executionDate, String city, QueueType type, TaskType task) {
        this.executionDate = executionDate;
        this.city = city;
        this.type = type;
        this.task = task;
    }


    @Override
    public String toString() {
        return "Queue{" +
                "id=" + getId() +
                ", executionDate=" + executionDate +
                ", createdAt=" + getCreatedAt() +
                ", city='" + city + '\'' +
                ", type=" + type +
                ", task=" + task +
                '}';
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

    public TaskType getTask() {
        return task;
    }

    public void setTask(TaskType task) {
        this.task = task;
    }


}

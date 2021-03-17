package queues.executor;

import queues.models.Queue;
import queues.models.QueueType;
import queues.tasks.MyTask;
import queues.utils.Converter;
import queues.databases.H2BaseRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TaskExecutor {
    ScheduledExecutorService executorService;
    Queue queue;
    MyTask task;


    public TaskExecutor(Queue queue) {
        this.queue = queue;
        task = Converter.getTask(queue.getTask().name());
    }

    public void executeDaily(int targetHour, int targetMin, int targetSec) {
        executorService = Executors.newScheduledThreadPool(1);
        AtomicInteger c = new AtomicInteger();
        Runnable taskWrapper = () -> {
            boolean isExecuted = task.execute();
            while (!isExecuted && c.get() <= 3) {
                c.getAndIncrement();
                isExecuted = task.execute();
            }
            if (!isExecuted) {
                System.out.println(Thread.currentThread().getId() + " - Failed " + queue.toString());
                queue.setType(QueueType.FAILED);
                H2BaseRepository.update(queue.getId(), Converter.convertQueue(queue));
            } else {
                queue.setType(QueueType.FINISHED);
                System.out.println(Thread.currentThread().getId() + " - Executed succesfully " + queue.toString());
                H2BaseRepository.update(queue.getId(), Converter.convertQueue(queue));
            }

        };

        long delay = computeNextDelay(targetHour, targetMin, targetSec);
        executorService.scheduleAtFixedRate(taskWrapper, delay, 86400, TimeUnit.SECONDS);

    }

    public void executeOnce(int targetHour, int targetMin, int targetSec) {
        executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        AtomicInteger c = new AtomicInteger();
        Runnable runnable = () -> {
            boolean isExecuted = task.execute();
            while (!isExecuted && c.get() <= 3) {
                c.getAndIncrement();
                task.doWhenFail();
            }
            if (!isExecuted) {
                System.out.println(Thread.currentThread().getId() + " - Failed " + queue.toString());
                queue.setType(QueueType.FAILED);
                H2BaseRepository.update(queue.getId(), Converter.convertQueue(queue));
            } else {
                System.out.println(Thread.currentThread().getId() + " - Executed succesfully " + queue.toString());
                queue.setType(QueueType.FINISHED);
                H2BaseRepository.update(queue.getId(), Converter.convertQueue(queue));
            }

        };
        executorService.schedule(runnable, computeNextDelay(targetHour, targetMin, targetSec), TimeUnit.SECONDS);
    }

    private long computeNextDelay(int targetHour, int targetMin, int targetSec) {
        LocalDateTime localNow = LocalDateTime.now();
        ZoneId currentZone = ZoneId.systemDefault();
        ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
        ZonedDateTime zonedNextTarget = zonedNow.withHour(targetHour).withMinute(targetMin).withSecond(targetSec);
        if (zonedNow.compareTo(zonedNextTarget) > 0)
            zonedNextTarget = zonedNextTarget.plusDays(1);

        Duration duration = Duration.between(zonedNow, zonedNextTarget);
        return duration.getSeconds();
    }

    public void stop() {
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            Logger.getLogger(TaskExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void execute() {
        int hour = queue.getExecutionDate().getHours();
        int minutes = queue.getExecutionDate().getMinutes();
        int seconds = queue.getExecutionDate().getSeconds();
        switch (queue.getTask()) {
            case PrimeNumberWritter:
                executeOnce(hour, minutes, seconds);
                break;
            case GetTodayQueues:
                executeDaily(hour, minutes, seconds);
                break;
            case WriteToFile:
                executeOnce(hour, minutes, seconds);
                break;
            case ReadFromFile:
                executeOnce(hour, minutes, seconds);
                break;
            case WriteFinishedQueue:
                executeDaily(hour, minutes, seconds);
                break;
            case WriteFailedQueue:
                executeDaily(hour, minutes, seconds);
                break;
        }
    }
}

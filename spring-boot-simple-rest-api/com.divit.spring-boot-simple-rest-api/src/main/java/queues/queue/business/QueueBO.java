package queues.queue.business;

import queues.databases.H2BaseRepository;
import queues.models.Queue;
import queues.utils.Converter;
import queues.queue.repository.QueueRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

public class QueueBO {
    QueueRepository repository;
    H2BaseRepository h2BaseRepository;

    public QueueBO() {
        repository = new QueueRepository();
        h2BaseRepository = new H2BaseRepository();
    }

    @PostMapping("/addQueue")
    public Queue addQueue(@RequestBody Queue queues) {
        if (Converter.localDateToDate(LocalDateTime.now()).after(queues.getExecutionDate())) {
            return null;
        }
        if (queues.getExecutionDate().before(Converter.localDateToDate(LocalDateTime.now().plusDays(1)))) {
            return H2BaseRepository.add(Converter.convertQueue(queues));
        }
        return repository.add(queues);
    }

    public boolean delete(String id) {
        return repository.delete(id);
    }

    public Queue update(Queue queues) {
        if (Converter.localDateToDate(LocalDateTime.now()).after(queues.getExecutionDate())) {
            return null;
        }
        if (queues.getExecutionDate().before(Converter.localDateToDate(LocalDateTime.now().plusDays(1)))) {
            return H2BaseRepository.update(queues.getId(), Converter.convertQueue(queues));
        }
        return repository.update(queues.getId(), queues);
    }

    public Queue getById(String id) {
        return repository.getById(id);
    }

    public List<Queue> getTodayQueues() {
        return repository.getTodaysQueues();
    }


}

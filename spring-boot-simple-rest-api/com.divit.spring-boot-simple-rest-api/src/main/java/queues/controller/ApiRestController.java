package queues.controller;

import queues.databases.H2BaseRepository;
import queues.failedQueues.FailedQueueRepo;
import queues.models.Queue;
import queues.finishedQueues.FinishedQueueRepo;
import queues.queue.business.QueueBO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import queues.queue.repository.QueueRepository;

@RestController
@RequestMapping(value = "/api/v1")
public class ApiRestController {

    private final QueueBO queueBO = new QueueBO();

    @GetMapping(value = "/queues/{id}")
    public ResponseEntity<?> fetchCandidates(@PathVariable(value = "id") String id) {

        return ResponseEntity.ok(queueBO.getById(id));

    }
    @PostMapping(value = "/queues")
    public ResponseEntity<?> addQueue(@RequestBody Queue id) {

        return ResponseEntity.ok(queueBO.addQueue(id));

    }

    @GetMapping(value = "/finishedQueues")
    public ResponseEntity getFinishedQueues(){
        FinishedQueueRepo repo = new FinishedQueueRepo();
        return ResponseEntity.ok(repo.getFinishedQueues());
    }

    @GetMapping(value = "/todayQueues")
    public ResponseEntity getTodayQueues(){
        H2BaseRepository h2BaseRepository = new H2BaseRepository();
        return ResponseEntity.ok(h2BaseRepository.find("SELECT * FROM QUEUE"));
    }

    @GetMapping(value = "/failedQueues")
    public ResponseEntity failedQueues(){
        FailedQueueRepo repo = new FailedQueueRepo();
        return ResponseEntity.ok(repo.getFailedQueues());
    }

    @GetMapping(value = "/allQueues")
    public ResponseEntity getAllQueues(){
        QueueRepository repo = new QueueRepository();
        return ResponseEntity.ok(repo.getQueues());
    }
}

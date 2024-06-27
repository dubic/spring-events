package com.dubic.springevents.relational;

import com.dubic.springevents.custom.CustomThing;
import com.dubic.springevents.custom.PersistentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class EmploymentService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private int count = 0;

    private final EmploymentRepo employmentRepo;

    public EmploymentService(EmploymentRepo employmentRepo) {
        this.employmentRepo = employmentRepo;
    }

    //    @Async
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void personSaved(CustomThing customThing) {
        log.info("person saved: {}", customThing);
//        if (customThing.personId() == 1) {
//            throw new IllegalStateException("I cannot save for person id as 1");
//        }
        this.employmentRepo.save(new Employment(customThing.personId(), "Indeco salts", 0));
    }

    @ApplicationModuleListener
    public void on(PersistentEvent persistentEvent) throws InterruptedException {
        log.info("Thread is sleeping now...");
        Thread.sleep(10_000);
        log.info("Persistent event seen: {}", persistentEvent);
    }
}

package com.dubic.springevents.relational;

import com.dubic.springevents.custom.CustomThing;
import com.dubic.springevents.custom.PersistentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final PersonRepo personRepo;
    private final EmploymentRepo empRepo;
    private final ApplicationEventPublisher eventPublisher;

    public PersonService(PersonRepo personRepo, EmploymentRepo empRepo, ApplicationEventPublisher eventPublisher) {
        this.personRepo = personRepo;
        this.empRepo = empRepo;
        this.eventPublisher = eventPublisher;
    }

    private void clearPersonAndEmployment() {
        this.personRepo.deleteAll();
        ;
        this.empRepo.deleteAll();
        log.info("deleted persons and employment");
    }

    public void customEventSave() {
        clearPersonAndEmployment();
        Person person = this.personRepo.save(new Person(0, "Dubic Uzuegbu", 23));
        this.eventPublisher.publishEvent(new CustomThing("Just started application", person.id()));
    }

    public void asyncEventSave() {
        clearPersonAndEmployment();
        Person person = this.personRepo.save(new Person(0, "Dubic Uzuegbu", 23));
        this.eventPublisher.publishEvent(new CustomThing("Just started application", person.id()));
    }

    @Transactional
    public void transactionInEventSave() {
        Person person = this.personRepo.save(new Person(0, "Dubic Uzuegbu", 23));
        this.eventPublisher.publishEvent(new CustomThing("Just started application", person.id()));
    }

    @Transactional
    public void transactionInAsyncEventSave() {
        Person person = this.personRepo.save(new Person(0, "Dubic Uzuegbu", 23));
        this.eventPublisher.publishEvent(new CustomThing("Just started application", person.id()));
    }

    public void transactionInTwoAsyncEventSave() {
        Person person = this.personRepo.save(new Person(0, "Dubic Uzuegbu", 23));
        this.eventPublisher.publishEvent(new CustomThing("Just started application", person.id()));
    }

    @Transactional
    public void persistentEventSave() {
        this.eventPublisher.publishEvent(new PersistentEvent("Murtala Moha"));
    }
}

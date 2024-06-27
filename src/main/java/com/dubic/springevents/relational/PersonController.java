package com.dubic.springevents.relational;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final PersonRepo personRepo;
    private final EmploymentRepo employmentRepo;
    private final PersonService personService;

    public PersonController(PersonRepo personRepo, EmploymentRepo employmentRepo, PersonService personService) {
        this.personRepo = personRepo;
        this.employmentRepo = employmentRepo;
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<String> person() {
        log.info("Calling person...");
        return ResponseEntity.ok("A person");
    }

    @GetMapping("/secure")
    public ResponseEntity<String> securePerson() {
        log.info("Calling secure person...");
        return ResponseEntity.ok("A secure person");
    }

    @GetMapping("/save/custom/event")
    public ResponseEntity<Map<String, Object>> customEventSave() {
        log.info("saving person publishes an event that saves employee");
        this.personService.customEventSave();
        return ResponseEntity.ok(Map.of(
                "person", this.personRepo.findAll(),
                "employment", this.employmentRepo.findAll()
        ));
    }

    @GetMapping("/save/async/event")
    public ResponseEntity<Map<String, Object>> asyncEventSave() {
        log.info("saving person publishes an async event that saves employee");
        this.personService.asyncEventSave();
        return ResponseEntity.ok(Map.of(
                "person", this.personRepo.findAll(),
                "employment", this.employmentRepo.findAll()
        ));
    }

    @GetMapping("/save/transaction/event")
    public ResponseEntity<Map<String, Object>> transactionInEventSave() {
        log.info("saving person publishes an event that saves employee in the same transaction");
        try {
            this.personService.transactionInEventSave();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ResponseEntity.ok(Map.of(
                "person", this.personRepo.findAll(),
                "employment", this.employmentRepo.findAll()
        ));
    }

    @GetMapping("/save/transaction/async/event")
    public ResponseEntity<Map<String, Object>> transactionInAsyncEventSave() {
        log.info("saving person publishes an async event that saves employee in the same transaction");
        try {
            this.personService.transactionInAsyncEventSave();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ResponseEntity.ok(Map.of(
                "person", this.personRepo.findAll(),
                "employment", this.employmentRepo.findAll()
        ));
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> personAndEmployment() {
//        log.info("Calling person...");
        Map<String, Object> map = new HashMap<>();
        personRepo.findAll().forEach(person -> map.put("person", person));
        employmentRepo.findAll().forEach(employment -> map.put("employment", employment));

        return ResponseEntity.ok(map);
    }

    @GetMapping("/persistent")
    public ResponseEntity<Map<String, Object>> persistentEvent() {
        this.personService.persistentEventSave();
        return ResponseEntity.ok(Map.of("successful", true));
    }

}

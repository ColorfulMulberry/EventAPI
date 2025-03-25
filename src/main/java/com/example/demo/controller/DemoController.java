package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Event;
import com.example.demo.service.DemoService;

// @RequestMapping("/json")   add this to change the URI path
@RestController
public class DemoController {

    private DemoService demoService;

    @Autowired
    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping(path = "/retrieveEvent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> retrieveEvent(@RequestParam Integer id) {
        Optional<Event> event = demoService.getEvent(id);

        if (event.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body((Event) event.orElseThrow());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping(path = "/addEvent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> addEvent(
            @RequestParam("name") String name,
            @RequestParam(value = "desc", defaultValue = "No Description") String desc) {

        Optional<Event> event = demoService.addEvent(name, desc);

        if (event.isPresent())
            return ResponseEntity.status(HttpStatus.CREATED).body((Event) event.orElseThrow());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping(path = "/updateEvent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateEvent(
            @RequestParam("id") Integer id,
            @RequestParam(value = "name") Optional<String> name,
            @RequestParam(value = "desc") Optional<String> desc) {

        // no values to update
        if (!name.isPresent() && !desc.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // change body to created event

        Optional<Event> event = demoService.updateEvent(id, name, desc);

        if (event.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(event.orElseThrow());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // change body to created event
    }

}
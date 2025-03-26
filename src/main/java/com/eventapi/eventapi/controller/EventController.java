package com.eventapi.eventapi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eventapi.eventapi.model.Event;
import com.eventapi.eventapi.service.EventService;

// @RequestMapping("/json")   add this below @RestController to change the URL path
@RestController
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // search for an event given an event id, return its details in json format
    @GetMapping(path = "/retrieveEvent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> retrieveEvent(@RequestParam Integer id) {
        Optional<Event> event = eventService.getEvent(id);

        if (event.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body((Event) event.orElseThrow());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // add an event to the list of events
    // name and date of event are required, description optional
    @PostMapping(path = "/addEvent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> addEvent(
            @RequestParam("name") String name,
            @RequestParam(value = "desc", defaultValue = "No Description") String desc,
            @RequestParam("date") String date) {

        Optional<Event> event = eventService.addEvent(name, desc, date);

        if (event.isPresent())
            return ResponseEntity.status(HttpStatus.CREATED).body((Event) event.orElseThrow());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    // update an event given an event id
    // name, description, and date are optional
    @PutMapping(path = "/updateEvent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> updateEvent(
            @RequestParam("id") Integer id,
            @RequestParam(value = "name") Optional<String> name,
            @RequestParam(value = "desc") Optional<String> desc,
            @RequestParam(value = "date") Optional<String> date) {

        // no values to update, return code 400
        if (!name.isPresent() && !desc.isPresent() && !date.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // change body to created event

        Optional<Event> event = eventService.updateEvent(id, name, desc, date);

        if (event.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(event.orElseThrow());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // change body to created event
    }

    // delete an event given an event id
    @DeleteMapping(path = "/removeEvent")
    public ResponseEntity<Object> removeEvent(@RequestParam("id") Integer id) {
        boolean res = eventService.deleteEvent(id);

        if (res)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
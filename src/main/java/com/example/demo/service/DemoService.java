package com.example.demo.service;

import com.example.demo.model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class DemoService {
    private List<Event> events;
    private int curId = 1;

    // constructor with example events to start
    public DemoService() {
        events = new ArrayList<>();

        events.add(new Event(curId++, "Party", "Party at the lodge."));
        events.add(new Event(curId++, "April Fools Day", "Pranks planned for friends."));
        events.add(new Event(curId++, "Neighbour Birthday", "Attend neighbour's birthday party."));
    }

    // find an event in the list of events given an id
    public Optional<Event> getEvent(Integer id) {
        Optional<Event> optional = Optional.empty();
        for (Event event : events) {
            if (id == event.getId()) {
                optional = Optional.of(event);
                return optional;
            }
        }
        return optional;
    }

    // add an event to the list given an event name and description
    public Optional<Event> addEvent(String eventName, String description) {
        Optional<Event> optional = Optional.empty();

        // implement a date attribute and validate it here

        Event newEvent = new Event(curId++, eventName, description);
        events.add(newEvent);
        optional = Optional.of(newEvent);

        return optional;
    }

    // update an existing event given its id, its name and/or description
    public Optional<Event> updateEvent(Integer id, Optional<String> eventName, Optional<String> description) {
        Optional<Event> optional = Optional.empty();

        if (id <= 0 && id > curId) { // not in range of valid ids
            return optional;
        }

        // search for the id in event list
        for (Event event : events) {
            if (id == event.getId()) {
                if (eventName.isPresent())
                    event.setName(eventName.orElseThrow());
                if (description.isPresent())
                    event.setDescription(description.orElseThrow());

                optional = Optional.of(event);
                return optional;
            }
        }
        return optional;
    }
}

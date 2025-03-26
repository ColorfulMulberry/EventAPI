package com.eventapi.eventapi.service;

import com.eventapi.eventapi.model.Event;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class EventService {
    private List<Event> events;
    private int curId = 1;

    // constructor with example events to start
    public EventService() {
        events = new ArrayList<>();

        events.add(new Event(curId++, "Party", "Party at the lodge.", "2025-03-30"));
        events.add(new Event(curId++, "April Fools Day", "Pranks planned for friends.", "2025-04-01"));
        events.add(new Event(curId++, "Neighbour Birthday", "Attend neighbour's birthday party.", "2025-04-03"));
    }

    // find an event in the list of events given an id
    public Optional<Event> getEvent(Integer id) {
        Optional<Event> optional = Optional.empty();

        if (id <= 0 || id > curId) { // not in range of valid ids
            return optional;
        }

        for (Event event : events) {
            if (id == event.getId()) {
                optional = Optional.of(event);
                return optional;
            }
        }
        return optional;
    }

    // add an event to the list given an event name and description
    public Optional<Event> addEvent(String eventName, String description, String date) {
        Optional<Event> optional = Optional.empty();

        // validate the date follows ISO-8601 standard ( e.g. "2022-05-30" or
        // "2029-11-07+05:00" )
        try {
            LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            Event newEvent = new Event(curId++, eventName, description, date);
            events.add(newEvent);
            optional = Optional.of(newEvent);
        } catch (DateTimeParseException e) {
            return optional;
        }

        return optional;
    }

    // update an existing event given its id, its name and/or description
    public Optional<Event> updateEvent(Integer id, Optional<String> eventName, Optional<String> description,
            Optional<String> date) {
        Optional<Event> optional = Optional.empty();

        if (id <= 0 || id > curId) { // not in range of valid ids
            return optional;
        }

        // search for the id in event list
        for (Event event : events) {
            if (id == event.getId()) {
                if (eventName.isPresent())
                    event.setName(eventName.orElseThrow());
                if (description.isPresent())
                    event.setDescription(description.orElseThrow());
                if (date.isPresent()) {
                    try { // validate date
                        LocalDate.parse(date.orElseThrow(), DateTimeFormatter.ISO_DATE);
                        event.setDate(date.orElseThrow());
                    } catch (DateTimeParseException e) {
                        return optional;
                    }
                }

                optional = Optional.of(event);
                return optional;
            }
        }
        return optional;
    }

    // remove an existing event from the event list given its id
    public boolean deleteEvent(Integer id) {
        if (id <= 0 || id > curId) { // not in range of valid ids
            return false;
        }

        Iterator<Event> iterator = events.iterator();
        while (iterator.hasNext()) {
            Event event = iterator.next();
            if (id == event.getId()) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }
}

package com.example.eventmanagementdemo.dao.event;

import com.example.eventmanagementdemo.model.Event;

import java.util.ArrayList;
import java.util.List;

//Unused class
public class EventDAOImplTesting implements IEventDAO {

    /**
     * A static list of contacts to be used as a mock database.
     */
    public static final ArrayList<Event> eventList = new ArrayList<>();
    private static int autoIncrementedId = 0;

    public EventDAOImplTesting() {
        // Add some initial contacts to the mock database
        /*addEvent(new Event("Event1", "Host1", "23/10/2025", "Venue1", "12345"));
        addEvent(new Event("Event2", "Host2", "24/10/2025", "Venue2", "12345"));
        addEvent(new Event("Event3", "Host3", "25/10/2025", "Venue3", "12345"));*/

    }


    /**
     * Add a new Event to the database.
     *
     * @param event : The event to be added.
     */
    @Override
    public void addEvent(Event event) {
        event.setId(autoIncrementedId);
        eventList.add(event);
        autoIncrementedId++;
    }

    /**
     * Retrieve all the event from the database.
     *
     * @return All the events from the database.
     */
    @Override
    public List<Event> getEvents() {
        return eventList;
    }

    /**
     * @return
     */
    @Override
    public List<Event> getUpcomingEvents() {
        return null;
    }

    /**
     * Retrieve the event based on the id.
     *
     * @param id : Id of the event to be retrieved
     * @return Event that was requested.
     */
    @Override
    public Event getEvent(int id) {
       return eventList.stream().filter(event -> event.getId() == id).findFirst().orElse(null);
    }

    /**
     * Delete the event whose id was provided.
     *
     * @param event : event to be deleted.
     */
    @Override
    public void deleteEvent(Event event) {
        eventList.remove(event);
    }

    /**
     * Update the event based on the id provided in the event.
     *
     * @param event Event to be updated.
     */
    @Override
    public void updateEvent(Event event) {
        eventList.set(event.getId(), event);
    }
}

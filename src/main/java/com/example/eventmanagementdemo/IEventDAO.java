package com.example.eventmanagementdemo;

import java.util.List;

/**
 * Interface for the Event Data Access Object that handles
 * the CRUD operations for the Event class with the database.
 */
public interface IEventDAO {

    /**
     * Add a new Event to the database.
     * @param event: The event to be added.
     */
    public void addEvent(Event event);

    /**
     * Retrieve all the event from the database.
     * @return All the events from the database.
     */
    public List<Event> getEvents();

    /**
     * Retrieve the event based on the id.
     * @param id : Id of the event to be retrieved
     * @return Event that was requested.
     */
    public Event getEvent(int id);

    /**
     * Delete the event whose id was provided.
     * @param id : Id of the event to be deleted.
     */
    public void deleteEvent(int id);

    /**
     * Update the event based on the id provided in the event.
     * @param event Event to be updated.
     */
    public void updateEvent(Event event);


}

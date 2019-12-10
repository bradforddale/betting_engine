package event;

import utilities.ResultStatus;

import java.util.ArrayList;

public class EventData {
    private static EventData eventData;
    private ArrayList<Event> events = new ArrayList<Event>();

    public static EventData getInstance() {
        if (eventData == null){
            eventData = new EventData();
        }
        return eventData;
    }

    private EventData() {
        events = new ArrayList<Event>();
    }

    public ResultStatus create(Event event) {
        try {
            this.events.add(event);
            return ResultStatus.SUCCESSFUL;
        } catch (Exception e) {
            return ResultStatus.FAILED;
        }
    }

    public Event get(String id) throws EventNotFoundException {
        for (Event e: events) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        throw new EventNotFoundException();
    }

    public boolean validateEventId(String eventId) {
        try {
            return get(eventId) != null;
        } catch (EventNotFoundException e) {
            return false;
        }
    }

    public ArrayList<Event> getAll() {
        return this.events;
    }

    public void clear() {
        this.events = new ArrayList<Event>();
    }
}

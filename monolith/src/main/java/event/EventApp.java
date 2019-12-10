package event;

import utilities.*;

import java.time.LocalDateTime;

public class EventApp {
    private EventData eventData;
    private static EventApp eventApp;

    private EventApp(EventData eventData) {
        this.eventData = eventData;
    }

    public static EventApp getInstance() {
        if (eventApp == null){
            eventApp = new EventApp(DependencyInjector.EventData());
        }
        return eventApp;
    }

    public Result createEvent (String description, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            Event event = new Event(description, startTime, endTime);
            return _createEvent(event);
        } catch (DataInvalidException d) {
            return new Result(ResultStatus.FAILED, "Event creation failed due to invalid data", null);
        }
    }

    public Result createEvent (String description, LocalDateTime startTime, LocalDateTime endTime, String id) {
        try {
            Event event = new Event(description, startTime, endTime, id);
            return _createEvent(event);
        } catch (DataInvalidException d) {
            return new Result(ResultStatus.FAILED, "Event creation failed due to invalid data", null);
        }
    }

    private Result _createEvent(Event event) {
        ResultStatus resultStatus = eventData.create(event);
        return new Result(resultStatus, "Event created with id " + event.getId(), null);
    }

    public Result getEvent(String eventId) {
        try {
            Event foundEvent = eventData.get(eventId);
            return new Result(ResultStatus.SUCCESSFUL, "Event found", foundEvent);
        } catch(EventNotFoundException e) {
            return CommonResults.eventNotFoundResult(eventId, null);
        }
    }

    public Result validate (String eventId) {
        return new Result(ResultStatus.SUCCESSFUL, "Validate event " + eventId, eventData.validateEventId(eventId));
    }
}

package event;

import utilities.BettingUtilities;
import utilities.BettingValidation;
import utilities.DataInvalidException;

import java.time.LocalDateTime;

public class Event {
    private String id;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Event(String description, LocalDateTime startTime, LocalDateTime endTime) throws DataInvalidException {
        validateFields(description, startTime, endTime);
        this.id = BettingUtilities.generateId("EVENT");
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Event(String description, LocalDateTime startTime, LocalDateTime endTime, String id) throws DataInvalidException {
        validateFields(description, startTime, endTime);
        this.id = id;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void validateFields(String description, LocalDateTime startTime, LocalDateTime endTime) throws DataInvalidException {
        if (!(BettingValidation.validateDescription(description)
            && BettingValidation.validateStartTime(startTime)
            && BettingValidation.validateEndTime(endTime))) {
            throw new DataInvalidException();
        }
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public boolean equals (Object o) {
        if (this == o) {
            return true;
        } else if (getClass() == o.getClass()) {
            Event oEvent = (Event) o;
            return getId().equals(oEvent.getId());
        } else {
            return false;
        }
    }
}

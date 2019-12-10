package market;

import utilities.BettingUtilities;
import utilities.BettingValidation;
import utilities.DataInvalidException;

public class Market {
    private String id;
    private String eventId;
    private String description;

    public Market(String eventId, String description) throws DataInvalidException {
        validateFields(description);
        this.id = BettingUtilities.generateId("MARKET");
        this.eventId = eventId;
        this.description = description;
    }

    public Market(String eventId, String description, String id) throws DataInvalidException {
        validateFields(description);
        this.id = id;
        this.eventId = eventId;
        this.description = description;
    }

    private void validateFields(String description) throws DataInvalidException {
        if (!(BettingValidation.validateDescription(description))) {
            throw new DataInvalidException();
        }
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getEventId() {
        return eventId;
    }

    public String toString() {
        return "Market{" +
                "id='" + id + '\'' +
                ", eventId='" + eventId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public boolean equals (Object o) {
        if (this == o) {
            return true;
        } else if (getClass() == o.getClass()) {
            Market oMarket = (Market) o;
            return getId().equals(oMarket.getId());
        } else {
            return false;
        }
    }
}

package event;

import org.junit.Before;
import org.junit.Test;
import utilities.DataInvalidException;
import utilities.Result;
import utilities.ResultStatus;
import utilities.DependencyInjector;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class EventAppTest {
    private EventData eventData = DependencyInjector.EventData();
    private EventApp eventApp = DependencyInjector.EventApp();

    @Before
    public void setup() {
        eventData.clear();
    }

    @Test
    public void createEvent_returns_resultSuccess_for_validEvent() {
        Result expectedResult = new Result(ResultStatus.SUCCESSFUL, "Event created with id 1234", null);
        Result result = eventApp.createEvent("Soccer World Cup 2019", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "1234");
        assertEquals(expectedResult, result);
    }

    @Test
    public void createEvent_returns_resultFailure_for_invalidEvent() {
        Result expectedResult = new Result(ResultStatus.FAILED, "Event creation failed due to invalid data", null);
        Result result = eventApp.createEvent("", LocalDateTime.now(), LocalDateTime.now().plusDays(2));
        assertEquals(expectedResult, result);
    }

    @Test
    public void getEvent_returns_resultWithNullEvent_for_invalidEventId() {
        Result expectedResult = new Result(ResultStatus.FAILED, "Event not found for id -1", null);
        Result result = eventApp.getEvent("-1");
        assertEquals(expectedResult, result);
    }

    @Test
    public void getEvent_returns_resultWithEvent_for_validEventId() throws DataInvalidException {
        Event foundEvent = new Event("Soccer World Cup 2019", LocalDateTime.now(), LocalDateTime.now(), "EVENT_1234");
        eventApp.createEvent(foundEvent.getDescription(), foundEvent.getStartTime(), foundEvent.getEndTime(), foundEvent.getId());
        Result expectedResult = new Result(ResultStatus.SUCCESSFUL, "Event found", foundEvent);
        Result result = eventApp.getEvent("EVENT_1234");
        assertEquals(expectedResult, result);
    }

}


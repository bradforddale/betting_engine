package event;

import org.junit.Before;
import org.junit.Test;
import utilities.DataInvalidException;
import utilities.ResultStatus;
import utilities.DependencyInjector;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class EventDataTest {
    private EventData eventData = DependencyInjector.EventData();

    @Before
    public void setup () {
        eventData.clear();
    }

    @Test
    public void create_returns_resultStatusSuccessful_withValidEvent() throws DataInvalidException {
        Event validEvent = new Event("Soccer World Cup 2017", LocalDateTime.now(), LocalDateTime.now().plusDays(2));
        assertEquals(eventData.create(validEvent), ResultStatus.SUCCESSFUL);
    }

    @Test
    public void getAll_returns_allEvents() throws DataInvalidException {
        eventData.create(new Event("Soccer World Cup 2017", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "EVENT_9c1374f6-d9de-4526-8034-42e9b321980e"));
        eventData.create(new Event("Tennis World Cup 2019", LocalDateTime.now(), LocalDateTime.now().plusDays(2)));
        eventData.create(new Event("Soccer World Cup 2019", LocalDateTime.now(), LocalDateTime.now().plusDays(2)));
        assert (eventData.getAll().size() == 3);
    }

    @Test
    public void get_returns_event_for_validId() throws EventNotFoundException, DataInvalidException {
        Event initialEvent = new Event("Soccer World Cup 2017", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "EVENT_9c1374f6-d9de-4526-8034-42e9b321980e");
        eventData.create(initialEvent);
        String searchId = initialEvent.getId();
        assertEquals(initialEvent, eventData.get(searchId));
    }

    @Test
    public void get_throws_EventNotFoundException_for_invalidId() throws EventNotFoundException, DataInvalidException {
        Event initialEvent = new Event("Soccer World Cup 2017", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "EVENT_9c1374f6-d9de-4526-8034-42e9b321980e");
        eventData.create(initialEvent);
        String searchId = initialEvent.getId();
        try {
            eventData.get(searchId);
        } catch (EventNotFoundException e) {
            assert(true);
        } catch (Exception e) {
            assert (false);
        }
    }
}
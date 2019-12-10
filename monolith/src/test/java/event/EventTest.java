package event;

import org.junit.Test;
import utilities.DataInvalidException;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class EventTest {

    @Test
    public void eventObject_with_validFields_has_validId() throws DataInvalidException {
        Event event = new Event("Soccer World Cup 2020", LocalDateTime.now(), LocalDateTime.now().plusDays(2));
        assert(event.getId().length() == (36 + "EVENT".length() + 1));
        assert(event.getId().indexOf("EVENT") == 0);
    }

    @Test
    public void eventObject_with_invalidFields_throws_DataInvalidException () {
        try {
            Event event = new Event("", LocalDateTime.now(), LocalDateTime.now());
        } catch (DataInvalidException d) {
            assert (true);
        } catch (Exception e) {
            assert (false);
        }
    }

    @Test
    public void equals_with_sameEventId_returns_true () throws DataInvalidException {
        Event event = new Event("Soccer World Cup 2020", LocalDateTime.now(), LocalDateTime.now(), "1234");
        Event sameEvent = new Event("A copy 2020", LocalDateTime.now(), LocalDateTime.now(), "1234");
        assertEquals(event, sameEvent);
    }

    @Test
    public void equals_with_differentEventId_returns_false () throws DataInvalidException {
        Event event = new Event("Soccer World Cup 2020", LocalDateTime.now(), LocalDateTime.now(), "1234");
        Event differentEvent = new Event("A copy 2020", LocalDateTime.now(), LocalDateTime.now(), "1235");
        assert(!event.equals(differentEvent));
    }

}
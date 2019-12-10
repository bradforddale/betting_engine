package market;

import org.junit.Before;
import org.junit.Test;
import utilities.ResultStatus;

import static org.junit.Assert.*;

public class MarketDataTest {
    private MarketData marketData = new MarketData();

    @Before
    public void setup() {
        marketData.clear();
    }

    @Test
    public void create_returns_successfulResult() throws Exception {
        ResultStatus resultStatus = marketData.create(new Market("EVENT_9c1374f6-d9de-4526-8034-42e9b321980e", "Man Utd vs Liverpool"));
        assertEquals(ResultStatus.SUCCESSFUL, resultStatus);
    }

    @Test
    public void getMarketsForEvent_return_emptyArrayList_for_invalidEventId() throws Exception {
        String eventId = "EVENT_1234";
        assertEquals(marketData.getMarketsForEvent(eventId).size(), 0);
    }

    @Test
    public void getMarketsForEvent_return_nonEmptyArrayList_for_validEventId() throws Exception {
        String eventId = "EVENT_9c1374f6-d9de-4526-8034-42e9b321980e";
        marketData.create(new Market("EVENT_9c1374f6-d9de-4526-8034-42e9b321980e", "Man Utd vs Liverpool"));
        assertEquals(marketData.getMarketsForEvent(eventId).size(), 1);
    }
}
package market;

import org.junit.Test;
import utilities.DataInvalidException;

import static org.junit.Assert.assertEquals;

public class MarketTest {

    @Test
    public void marketObject_with_validFields__has_validId() throws DataInvalidException{
        Market event = new Market("1234", "Man Utd vs Liverpool");
        assert(event.getId().length() == (36 + "MARKET".length() + 1));
        assert(event.getId().indexOf("MARKET") == 0);
    }

    @Test
    public void marketObject_with_invalidFields_throws_DataInvalidException () {
        try {
            Market market = new Market("1234", "");
        } catch (DataInvalidException d) {
            assert (true);
        } catch (Exception e) {
            assert (false);
        }
    }

    @Test
    public void equals_with_sameMarketId_returns_true () throws DataInvalidException {
        Market market = new Market("1234", "Man Utd vs Liverpool", "MARKET_1");
        Market sameMarket = new Market("1234", "Man Utd vs Liverpool", "MARKET_1");
        assertEquals(market, sameMarket);
    }

    @Test
    public void equals_with_differentMarketId_returns_false () throws DataInvalidException {
        Market market = new Market("1234", "Man Utd vs Liverpool", "MARKET_1");
        Market differentMarket = new Market("1234", "Chelsea vs Liverpool", "MARKET_2");
        assert(!market.equals(differentMarket));
    }
}
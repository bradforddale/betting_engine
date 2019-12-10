package bet;

import org.junit.Test;
import utilities.DataInvalidException;

import static org.junit.Assert.assertEquals;

public class BetTest {
    @Test
    public void betObject_with_validFields_has_validId() throws DataInvalidException {
        Bet bet = new Bet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 20);
        assert(bet.getId().length() == (36 + "BET".length() + 1));
        assert(bet.getId().indexOf("BET") == 0);
    }

    @Test
    public void betObject_with_invalidFields_throws_DataInvalidException () {
        try {
            Bet bet = new Bet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", -1);
        } catch (DataInvalidException d) {
            assert (true);
        } catch (Exception e) {
            assert (false);
        }
    }

    @Test
    public void equals_with_sameBetId_returns_true () throws DataInvalidException {
        Bet bet = new Bet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 20, "BET_1234");
        Bet sameBet = new Bet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 20, "BET_1234");
        assertEquals(bet, sameBet);
    }

    @Test
    public void equals_with_differentBetId_returns_false () throws DataInvalidException {
        Bet bet = new Bet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 20, "BET_1234");
        Bet differentBet = new Bet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 20, "BET_1245");
        assert(!bet.equals(differentBet));
    }
}
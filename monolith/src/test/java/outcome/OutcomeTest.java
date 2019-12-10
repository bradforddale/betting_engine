package outcome;

import org.junit.Test;
import utilities.DataInvalidException;

import static org.junit.Assert.assertEquals;

public class OutcomeTest {

    @Test
    public void outcomeObject_with_validFields_has_validId() throws DataInvalidException {
        Outcome outcome = new Outcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Man Utd wins", 0.5);
        assert(outcome.getId().length() == (36 + "OUTCOME".length() + 1));
        assert(outcome.getId().indexOf("OUTCOME") == 0);
    }

    @Test
    public void outcomeObject_with_invalidFields_throws_DataInvalidException () {
        try {
            Outcome outcome = new Outcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Man Utd wins", 1.5);
        } catch (DataInvalidException d) {
            assert (true);
        } catch (Exception e) {
            assert (false);
        }
    }

    @Test
    public void equals_with_sameOutcomeId_returns_true () throws DataInvalidException {
        Outcome outcome = new Outcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Man Utd wins", 0.5, "OUTCOME_1234");
        Outcome sameOutcome = new Outcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Man Utd wins", 0.5, "OUTCOME_1234");
        assertEquals(outcome, sameOutcome);
    }

    @Test
    public void equals_with_differentOutcomeId_returns_false () throws DataInvalidException {
        Outcome outcome = new Outcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Man Utd wins", 0.5, "OUTCOME_1234");
        Outcome differentOutcome = new Outcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Man Utd wins", 0.5, "OUTCOME_1245");
        assert(!outcome.equals(differentOutcome));
    }

    @Test
    public void getDecimalsOdds_returns_correctResult () throws DataInvalidException {
        Outcome outcome = new Outcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Man Utd wins", 0.4, "OUTCOME_1234");
        assertEquals(2.5, outcome.getDecimalOdds(), 0);
    }
}
package outcome;

import org.junit.Before;
import org.junit.Test;
import utilities.DataInvalidException;
import utilities.DependencyInjector;
import utilities.ResultStatus;

import static org.junit.Assert.*;

public class OutcomeDataTest {
    private OutcomeData outcomeData = DependencyInjector.OutcomeData();

    @Before
    public void setup () {
        outcomeData.clear();
    }

    @Test
    public void create_returns_resultStatusSuccessful_with_anyOutcome() throws DataInvalidException {
        Outcome outcome = new Outcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Man Utd wins", 0.5);
        assertEquals(ResultStatus.SUCCESSFUL, outcomeData.create(outcome));
    }

    @Test
    public void get_returns_outcome_for_validId() throws OutcomeNotFoundException, DataInvalidException {
        outcomeData.create(new Outcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Man Utd wins", 0.5));
        Outcome outcome = outcomeData.getAll().get(0);
        String outcomeId = outcome.getId();
        assertEquals(outcome, outcomeData.get(outcomeId));
    }

    @Test
    public void get_throws_OutcomeNotFoundException_for_invalidId() {
        try {
            outcomeData.get("-1");
        } catch (OutcomeNotFoundException e) {
            assert(true);
        } catch (Exception e) {
            assert (false);
        }
    }

    @Test
    public void changeProbabilityForOutcome_changes_outcomeProbability_for_validId() throws DataInvalidException {
        String outcomeId = "OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5";
        outcomeData.create(new Outcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Man Utd wins", 0.5, outcomeId));
        try {
            outcomeData.changeProbabilityForOutcome(outcomeId, 0.7);
            assertEquals(0.7, outcomeData.getAll().get(0).getProbability(), 0);
        } catch (Exception o) {
            assert (false);
        }
    }

    @Test
    public void changeProbabilityForOutcome_get_throws_OutcomeNotFoundException_for_invalidId() {
        try {
            outcomeData.changeProbabilityForOutcome("-1", 0.7);
        } catch (OutcomeNotFoundException e) {
            assert(true);
        } catch (Exception e) {
            assert (false);
        }
    }

    @Test
    public void getOutcomesForMarket_return_emptyArrayList_for_invalidMarketId() throws Exception {
        outcomeData.create(new Outcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Man Utd wins", 0.5, "OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5"));
        assertEquals(0, outcomeData.getOutcomesForMarket("MARKET_1234").size());
    }

    @Test
    public void getOutcomesForMarket_return_nonEmptyArrayList_for_validEventId() throws Exception {
        outcomeData.create(new Outcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Man Utd wins", 0.5, "OUTCOME_123"));
        outcomeData.create(new Outcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Man Utd wins", 0.5, "OUTCOME_456"));
        assertEquals(2, outcomeData.getOutcomesForMarket("MARKET_ec264cd4-1eff-4810-8937-e338787a447c").size());
    }

}
package bet;

import org.junit.Before;
import org.junit.Test;
import outcome.OutcomeNotFoundException;
import utilities.DataInvalidException;
import utilities.DependencyInjector;
import utilities.ResultStatus;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class BetDataTest {
    private BetData betData = DependencyInjector.BetData();

    @Before
    public void setup() {
        betData.clear();
    }

    @Test
    public void create_return_successfulResult () throws DataInvalidException {
        ResultStatus resultStatus = betData.create(new Bet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 100));
        assertEquals(ResultStatus.SUCCESSFUL, resultStatus);
    }

    @Test
    public void get_returns_bet_for_validId() throws OutcomeNotFoundException, DataInvalidException, BetNotFoundException {
        Bet foundBet = new Bet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 100, "BET_1234");
        betData.create(foundBet);
        assertEquals(foundBet, betData.get("BET_1234"));
    }

    @Test
    public void get_throws_BetNotFoundException_for_invalidId() throws DataInvalidException {
        try {
            betData.create(new Bet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 100, "BET_1234"));
            betData.get("-1");
        } catch (BetNotFoundException e) {
            assert(true);
        } catch (Exception e) {
            assert (false);
        }
    }

    @Test
    public void getBetsForOutcome_return_emptyArrayList_for_invalidOutcomeId() throws Exception {
        betData.create(new Bet("OUTCOME_1", 100, "BET_1234"));
        betData.create(new Bet("OUTCOME_1", 100, "BET_1222"));
        betData.create(new Bet("OUTCOME_2", 100, "BET_1333"));

        ArrayList<Bet> expectedBets = new ArrayList<Bet>();

        assertEquals(0, betData.getBetsForOutcome("OUTCOME_0").size());
        assertEquals(expectedBets, betData.getBetsForOutcome("OUTCOME_0"));
    }

    @Test
    public void getBetsForOutcome_return_nonEmptyArrayList_for_validOutcomeId() throws Exception {
        betData.create(new Bet("OUTCOME_1", 100, "BET_1234"));
        betData.create(new Bet("OUTCOME_1", 100, "BET_1222"));
        betData.create(new Bet("OUTCOME_2", 100, "BET_1333"));

        ArrayList<Bet> expectedBets = new ArrayList<Bet>(Arrays.asList(new Bet("OUTCOME_1", 100, "BET_1234"), new Bet("OUTCOME_1", 100, "BET_1222")));
        ArrayList<Bet> resultBets = betData.getBetsForOutcome("OUTCOME_1");

        assertEquals(2, resultBets.size());
        assertEquals(expectedBets, resultBets);
    }

}
package bet;

import event.EventApp;
import event.EventData;
import market.MarketApp;
import market.MarketData;
import org.junit.Before;
import org.junit.Test;
import outcome.OutcomeApp;
import outcome.OutcomeData;
import utilities.CommonResults;
import utilities.Result;
import utilities.ResultStatus;
import utilities.DependencyInjector;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class BetAppTest {
    private EventData eventData = DependencyInjector.EventData();
    private MarketData marketData = DependencyInjector.MarketData();
    private OutcomeData outcomeData = DependencyInjector.OutcomeData();
    private BetData betData = DependencyInjector.BetData();
    private MarketApp marketApp = DependencyInjector.MarketApp();
    private EventApp eventApp = DependencyInjector.EventApp();
    private OutcomeApp outcomeApp = DependencyInjector.OutcomeApp();
    private BetApp betApp = DependencyInjector.BetApp();

    @Before
    public void setup() {
        eventData.clear();
        marketData.clear();
        outcomeData.clear();
        betData.clear();
        eventApp.createEvent("Soccer World Cup 2017", LocalDateTime.now(), LocalDateTime.now(), "EVENT_9c1374f6-d9de-4526-8034-42e9b321980e");
        marketApp.createMarket("EVENT_9c1374f6-d9de-4526-8034-42e9b321980e", "Man Utd vs Liverpool", "MARKET_ec264cd4-1eff-4810-8937-e338787a447c");
        outcomeApp.createOutcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Man Utd wins", 0.3, "OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5");
        outcomeApp.createOutcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Man Utd wins", 0.3, "OUTCOME_1234");
    }

    @Test
    public void placeBet_return_successfulResult_for_anyOutcome() {
        Result result = betApp.placeBet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 100);
        Result expectedResult = new Result(ResultStatus.SUCCESSFUL, "Bet placed", null);

        assertEquals(expectedResult, result);
        assertEquals(1, betData.getAll().size());
    }

    @Test
    public void getBet_returns_resultWithBet_for_validId() throws Exception {
        Bet bet = new Bet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 100, "BET_34caceed-5f75-4c03-b295-508e7c64f412");
        betApp.placeBet(bet.getOutcomeId(), bet.getAmount(), bet.getId());

        Result result = betApp.getBet("BET_34caceed-5f75-4c03-b295-508e7c64f412");
        Result expectedResult = new Result(ResultStatus.SUCCESSFUL, "Bet found", bet);

        assertEquals(expectedResult, result);
    }

    @Test
    public void getBet_returns_resultWithBetException_for_invalidId() throws Exception {
        betApp.placeBet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 100, "BET_34caceed-5f75-4c03-b295-508e7c64f412");

        Result expectedResult = CommonResults.betNotFoundResult("BET_1234", null);
        Result result = betApp.getBet("BET_1234");

        assertEquals(expectedResult, result);
    }

    @Test
    public void sumBetsForOutcome_returns_resultWith0_for_anInvalidOutcomeId () {
        betApp.placeBet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 5.5, "BET_34caceed-5f75-4c03-b295-508e7c64f412");
        betApp.placeBet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 4.5, "BET_1d487d68-4a1d-49fd-b1cd-1ff818fb0269");
        betApp.placeBet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 22, "BET_abb748d5-81df-4e60-a4aa-4938b7887538");

        Result expectedResult = new Result(ResultStatus.SUCCESSFUL, "Sum of bets calculated", 0.0);
        Result result = betApp.sumBetsForOutcome("1234");
        assertEquals(expectedResult, result);
    }

    @Test
    public void sumBetsForOutcome_returns_resultWithSumOfBets_for_aValidOutcomeId () {
        betApp.placeBet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 5.5, "BET_34caceed-5f75-4c03-b295-508e7c64f412");
        betApp.placeBet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 4.5, "BET_1d487d68-4a1d-49fd-b1cd-1ff818fb0269");
        betApp.placeBet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 22, "BET_abb748d5-81df-4e60-a4aa-4938b7887538");
        betApp.placeBet("OUTCOME_1234", 22, "BET_8824e720-e3a1-4009-a9d9-2ccf65c631cd");

        Result expectedResult = new Result(ResultStatus.SUCCESSFUL, "Sum of bets calculated", 32.0);
        Result result = betApp.sumBetsForOutcome("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5");

        assertEquals(expectedResult, result);
    }
}
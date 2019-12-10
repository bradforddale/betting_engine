package outcome;

import bet.BetApp;
import bet.BetData;
import event.EventApp;
import event.EventData;
import market.MarketApp;
import market.MarketData;
import org.junit.Before;
import org.junit.Test;
import utilities.*;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class OutcomeAppTest {
    private EventData eventData = DependencyInjector.EventData();
    private MarketData marketData = DependencyInjector.MarketData();
    private OutcomeData outcomeData = DependencyInjector.OutcomeData();
    private BetData betData = DependencyInjector.BetData();
    private EventApp eventApp = DependencyInjector.EventApp();
    private MarketApp marketApp = DependencyInjector.MarketApp();
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
    }

    @Test
    public void changeProbabilityForOutcome_returns_successfulResult_for_validId() throws Exception {

        outcomeData.create(new Outcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Man Utd wins ", 0.4, "OUTCOME_f8b48f37-7752-43e4-953e-5abcb5583b66"));
        outcomeApp.changeProbabilityForOutcome("OUTCOME_f8b48f37-7752-43e4-953e-5abcb5583b66", 0.7);
        assertEquals(0.7, outcomeData.get("OUTCOME_f8b48f37-7752-43e4-953e-5abcb5583b66").getProbability(), 0);
    }

    @Test
    public void changeProbabilityForOutcome_returns_failedResult_for_invalidId() throws Exception {
        outcomeData.create(new Outcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Man Utd wins ", 0.4, "OUTCOME_f8b48f37-7752-43e4-953e-5abcb5583b66"));
        Result expectedResult = CommonResults.outcomeNotFoundResult("-1", null);
        Result result = outcomeApp.changeProbabilityForOutcome("-1", 0.7);
        assertEquals(expectedResult, result);
    }

    @Test
    public void getPayoutFor_returns_resultWithPayout_for_validOutcomeId () throws DataInvalidException {
        setupDataForProfitCalculations();

        Result result = outcomeApp.getPayoutFor("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5");
        double expected = 32 * (1/0.4);
        assertEquals(ResultStatus.SUCCESSFUL, result.getResultStatus());
        assertEquals("Payout calculated", result.getResultMessage());
        assertEquals(expected, result.getResultObject());
    }

    @Test
    public void getPayoutFor_returns_resultWithZeroPayout_for_invalidOutcomeId () throws DataInvalidException {
        setupDataForProfitCalculations();

        Result result = outcomeApp.getPayoutFor("1234");
        Result expectedResult = CommonResults.outcomeNotFoundResult("1234", 0);
        assertEquals(expectedResult, result);
    }

    @Test
    public void getTakingsFor_returns_resultWithTakings_for_validOutcomeId() throws DataInvalidException {
        setupDataForProfitCalculations();
        Result result = outcomeApp.getTakingsFor("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5");
        Result expectedResult = new Result(ResultStatus.SUCCESSFUL, "Takings calculated",32.0);
        assertEquals(expectedResult, result);
    }

    @Test
    public void getTakingsFor_returns_resultWithZeroTakings_for_invalidOutcomeId() throws DataInvalidException {
        setupDataForProfitCalculations();
        Result result = outcomeApp.getTakingsFor("1234");
        Result expectedResult = CommonResults.outcomeNotFoundResult("1234", 0);
        assertEquals(expectedResult, result);
    }

    @Test
    public void getProfitForMarket_return_resultWithProfit_for_validMarketId_and_validWinningOutcomeId () throws DataInvalidException {
        setupDataForProfitCalculations();
        Result result = outcomeApp.getProfitForMarket("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5");
        Result expectedResult = new Result(ResultStatus.SUCCESSFUL, "Profit calculated", -20.0);
        assertEquals(expectedResult, result);
    }

    @Test
    public void getProfitForMarket_return_resultWithProfit_for_invalidMarketId_and_validWinningOutcomeId () throws DataInvalidException {
        setupDataForProfitCalculations();
        Result result = outcomeApp.getProfitForMarket("MARKET_1234", "OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5");
        Result expectedResult = new Result(ResultStatus.SUCCESSFUL, "Profit calculated", 0.0);
        assertEquals(expectedResult, result);
    }
    @Test
    public void getProfitForMarket_return_resultWithProfit_for_validMarketId_and_invalidWinningOutcomeId () throws DataInvalidException {
        setupDataForProfitCalculations();
        Result result = outcomeApp.getProfitForMarket("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "OUTCOME_1234");
        Result expectedResult = new Result(ResultStatus.SUCCESSFUL, "Profit calculated", 0.0);
        assertEquals(expectedResult, result);
    }
    @Test
    public void getProfitForMarket_return_resultWithProfit_for_invalidMarketId_and_invalidWinningOutcomeId () throws DataInvalidException {
        setupDataForProfitCalculations();
        Result result = outcomeApp.getProfitForMarket("MARKET_1234", "OUTCOME_1234");
        Result expectedResult = new Result(ResultStatus.SUCCESSFUL, "Profit calculated", 0.0);
        assertEquals(expectedResult, result);
    }

    @Test
    public void sumProbabilities_returnZero_for_invalidMarketId () throws DataInvalidException {
        setupDataForProfitCalculations();
        Result result = outcomeApp.sumProbabilities("-1");
        Result expectedResult = new Result(ResultStatus.SUCCESSFUL, "Sum of probabilities calculated", 0.0);
        assertEquals(expectedResult, result);
    }

    @Test
    public void sumProbabilities_returnSum_for_validMarketId () throws DataInvalidException {
        setupDataForProfitCalculations();
        Result result = outcomeApp.sumProbabilities("MARKET_ec264cd4-1eff-4810-8937-e338787a447c");
        Result expectedResult = new Result(ResultStatus.SUCCESSFUL, "Sum of probabilities calculated", 0.8);
        assertEquals(expectedResult, result);
    }

    private void setupDataForProfitCalculations () throws DataInvalidException {
        outcomeData.create(new Outcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Man Utd wins ", 0.4, "OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5"));

        outcomeData.create(new Outcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Liverpool wins", 0.4, "OUTCOME_e335c45a-9ab0-4108-aeab-8d6b6ec3bf91"));

        betApp.placeBet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 5.5, "BET_34caceed-5f75-4c03-b295-508e7c64f412");
        betApp.placeBet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 4.5, "BET_1d487d68-4a1d-49fd-b1cd-1ff818fb0269");
        betApp.placeBet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 22, "BET_abb748d5-81df-4e60-a4aa-4938b7887538");
        betApp.placeBet(                "OUTCOME_e335c45a-9ab0-4108-aeab-8d6b6ec3bf91", 60, "BET_30a05f78-2b1a-4ac1-92da-dfceca8b7b47");
    }
}
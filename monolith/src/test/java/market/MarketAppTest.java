package market;

import bet.BetApp;
import bet.BetData;
import event.Event;
import event.EventApp;
import event.EventData;
import org.junit.Before;
import org.junit.Test;
import outcome.OutcomeApp;
import outcome.OutcomeData;
import utilities.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class MarketAppTest {
    private EventData eventData = DependencyInjector.EventData();
    private MarketData marketData = DependencyInjector.MarketData();
    private OutcomeData outcomeData = DependencyInjector.OutcomeData();
    private BetData betData = DependencyInjector.BetData();
    private MarketApp marketApp = DependencyInjector.MarketApp();
    private EventApp eventApp = DependencyInjector.EventApp();
    private OutcomeApp outcomeApp = DependencyInjector.OutcomeApp();
    private BetApp betApp = DependencyInjector.BetApp();


    @Before
    public void setup () throws Exception {
        eventData.clear();
        marketData.clear();
        betData.clear();
        outcomeData.clear();
        eventApp.createEvent("Soccer World Cup 2017", LocalDateTime.now(), LocalDateTime.now(), "EVENT_9c1374f6-d9de-4526-8034-42e9b321980e");
    }

    @Test
    public void createMarket_return_resultFailure_for_invalidEventId() {
        Result result = marketApp.createMarket("-1", "Man Utd vs Liverpool");
        Result expectedResult = new Result(ResultStatus.FAILED, "Event not found for id -1", null);
        assertEquals(expectedResult, result);
        assertEquals(0, marketData.getAll().size());
    }

    @Test
    public void createMarket_return_resultFailure_for_invalidDescription() {
        eventApp.createEvent("Soccer World Cup 2017", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "EVENT_9c1374f6-d9de-4526-8034-42e9b321980e");
        Result result = marketApp.createMarket("EVENT_9c1374f6-d9de-4526-8034-42e9b321980e", "");
        assertEquals(ResultStatus.FAILED, result.getResultStatus());
        assertEquals("Market creation failed due to invalid data", result.getResultMessage());
        assertEquals(null, result.getResultObject());
        assertEquals(0, marketData.getAll().size());
    }

    @Test
    public void createMarket_return_resultSuccess_for_validEventId() {
        eventApp.createEvent("Soccer World Cup 2019", LocalDateTime.now(), LocalDateTime.now(), "EVENT_9c1374f6-d9de-4526-8034-42e9b321980e");
        Result result = marketApp.createMarket("EVENT_9c1374f6-d9de-4526-8034-42e9b321980e", "Man Utd vs Liverpool");
        assertEquals(ResultStatus.SUCCESSFUL, result.getResultStatus());
        assert(result.getResultMessage().indexOf("Market created with id ") == 0);
        assertEquals(null, result.getResultObject());
        assertEquals(1, marketData.getAll().size());
    }

    @Test
    public void getMarketsForEvent_return_resultWithEmptyArrayList_for_invalidEvent () {
        Result result = marketApp.getMarketsForEvent("-1");
        assertEquals(result.getResultStatus(), ResultStatus.SUCCESSFUL);
        assertEquals(result.getResultMessage(), "Markets not found for id -1");
        assertEquals(((ArrayList<Market>)result.getResultObject()).size(), 0);
    }

    @Test
    public void getMarketsForEvent_return_resultWithNonEmptyArrayList_for_validEvent () throws Exception {
        eventData.create(new Event("Soccer World Cup 2017", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "EVENT_9c1374f6-d9de-4526-8034-42e9b321980e"));
        eventData.create(new Event("Soccer World Cup 2019", LocalDateTime.now(), LocalDateTime.now(), "EVENT_235c8e0a-beb8-400e-be5a-e21cfde576ef"));
        marketApp.createMarket("EVENT_9c1374f6-d9de-4526-8034-42e9b321980e", "Man Utd vs Liverpool", "MARKET_1");
        marketApp.createMarket("EVENT_9c1374f6-d9de-4526-8034-42e9b321980e", "Chelsea vs Liverpool", "MARKET_2");
        marketApp.createMarket("EVENT_235c8e0a-beb8-400e-be5a-e21cfde576ef", "Man Utd vs Chelsea", "MARKET_3");

        ArrayList<Market> expectedMarkets = new ArrayList<Market>(Arrays.asList(new Market("EVENT_9c1374f6-d9de-4526-8034-42e9b321980e", "Man Utd vs Liverpool", "MARKET_1"), new Market("EVENT_9c1374f6-d9de-4526-8034-42e9b321980e", "Chelsea vs Liverpool", "MARKET_2")));
        Result expectedResult = new Result(ResultStatus.SUCCESSFUL, "Markets found", expectedMarkets);
        Result result = marketApp.getMarketsForEvent("EVENT_9c1374f6-d9de-4526-8034-42e9b321980e");

        assertEquals(expectedResult, result);
        assertEquals(2, ((ArrayList<Market>)result.getResultObject()).size());
    }

    @Test
    public void get_return_successfulResult_for_validId () throws Exception {
        Market market = new Market("EVENT_9c1374f6-d9de-4526-8034-42e9b321980e", "Man Utd vs Liverpool", "MARKET_e3a597cf-61e1-4725-b498-515f1f94c52f");
        marketApp.createMarket(market.getEventId(), market.getDescription(), market.getId());

        Result expectedResult = new Result(ResultStatus.SUCCESSFUL, "Market found", market);
        Result result = marketApp.getMarket("MARKET_e3a597cf-61e1-4725-b498-515f1f94c52f");

        assertEquals(expectedResult, result);
    }

    @Test
    public void get_return_failedResult_for_invalidId () throws Exception {
        Market market = new Market("EVENT_9c1374f6-d9de-4526-8034-42e9b321980e", "Man Utd vs Liverpool", "MARKET_e3a597cf-61e1-4725-b498-515f1f94c52f");
        marketApp.createMarket(market.getEventId(), market.getDescription(), market.getId());

        Result expectedResult = CommonResults.marketNotFoundResult("-1", null);
        Result result = marketApp.getMarket("-1");

        assertEquals(expectedResult, result);
    }

    @Test
    public void calculateProfits_returns_resultWithProfits_for_validMarket() throws DataInvalidException {
        setupDataForValidProfitCalculations();

        Result result = marketApp.calculateProfits("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5");
        Result expectedResult = new Result(ResultStatus.SUCCESSFUL, "Market profits calculated", -20.0);

        assertEquals(expectedResult, result);
    }

    @Test
    public void calculateProfits_returns_failedResult_for_invalidMarketId() throws DataInvalidException {
        setupDataForValidProfitCalculations();

        Result result = marketApp.calculateProfits("MARKET_1234", "OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5");
        Result expectedResult = new Result(ResultStatus.FAILED, "Market is not valid", null);

        assertEquals(expectedResult, result);
    }

    @Test
    public void calculateProfits_returns_failedResult_for_invalidMarket() throws DataInvalidException {
        setupDataForInvalidProfitCalculations();

        Result result = marketApp.calculateProfits("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5");
        Result expectedResult = new Result(ResultStatus.FAILED, "Market is not valid", null);

        assertEquals(expectedResult, result);
    }

    private void setupDataForValidProfitCalculations () {
        marketApp.createMarket("EVENT_9c1374f6-d9de-4526-8034-42e9b321980e", "Man Utd vs Liverpool", "MARKET_ec264cd4-1eff-4810-8937-e338787a447c");
        outcomeApp.createOutcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Man Utd wins ", 0.4, "OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5");
        outcomeApp.createOutcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Draw", 0.2, "4016525f-f459-4440-b087-abd8f9cf7e24");
        outcomeApp.createOutcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Liverpool wins", 0.4, "OUTCOME_e335c45a-9ab0-4108-aeab-8d6b6ec3bf91");
        setupBetsForProfitCalculation();
    }

    private void setupDataForInvalidProfitCalculations () {
        marketApp.createMarket("EVENT_9c1374f6-d9de-4526-8034-42e9b321980e", "Man Utd vs Liverpool", "MARKET_ec264cd4-1eff-4810-8937-e338787a447c");
        outcomeApp.createOutcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Man Utd wins ", 0.4, "OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5");
        outcomeApp.createOutcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "Liverpool wins", 0.4, "OUTCOME_e335c45a-9ab0-4108-aeab-8d6b6ec3bf91");
        setupBetsForProfitCalculation();
    }

    private void setupBetsForProfitCalculation () {
        betApp.placeBet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 5.5, "BET_34caceed-5f75-4c03-b295-508e7c64f412");
        betApp.placeBet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 4.5, "BET_1d487d68-4a1d-49fd-b1cd-1ff818fb0269");
        betApp.placeBet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 22, "BET_abb748d5-81df-4e60-a4aa-4938b7887538");
        betApp.placeBet(                "OUTCOME_e335c45a-9ab0-4108-aeab-8d6b6ec3bf91", 60, "BET_30a05f78-2b1a-4ac1-92da-dfceca8b7b47");
    }

}
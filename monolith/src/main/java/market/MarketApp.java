package market;

import utilities.*;

import java.util.ArrayList;

public class MarketApp {
    private static MarketApp marketApp;
    private MarketData marketData;

    public static MarketApp getInstance() {
        if (marketApp == null){
            marketApp = new MarketApp(DependencyInjector.MarketData());
        }
        return marketApp;
    }

    private MarketApp(MarketData marketData) {
        this.marketData = marketData;
    }

    public Result createMarket(String eventId, String description) {
        try {
            Market market = new Market(eventId, description);
            return _createMarket(market);
        } catch (DataInvalidException d) {
            return new Result(ResultStatus.FAILED, "Market creation failed due to invalid data", null);
        }
    }

    public Result createMarket(String eventId, String description, String id) {
        try {
            Market market = new Market(eventId, description, id);
            return _createMarket(market);
        } catch (DataInvalidException d) {
            return new Result(ResultStatus.FAILED, "Market creation failed due to invalid data", null);
        }
    }

    private Result _createMarket (Market market) {
        if (BettingValidation.validateEventId(market.getEventId())) {
            ResultStatus resultStatus = marketData.create(market);
            return new Result(resultStatus, "Market created with id " + market.getId(), null);
        } else {
            return CommonResults.eventNotFoundResult(market.getEventId(), null);
        }
    }

    public Result getMarket(String marketId) {
        try {
            Market market = marketData.get(marketId);
            return new Result(ResultStatus.SUCCESSFUL, "Market found", market);
        } catch (MarketNotFoundException m) {
            return CommonResults.marketNotFoundResult(marketId, null);
        }

    }

    public Result getMarketsForEvent(String eventId) {
        ArrayList<Market> marketsForEvent = marketData.getMarketsForEvent(eventId);
        if (marketsForEvent.size() == 0) {
            return new Result(ResultStatus.SUCCESSFUL, "Markets not found for id " + eventId, marketsForEvent);
        } else {
            return new Result(ResultStatus.SUCCESSFUL, "Markets found", marketsForEvent);
        }
    }

    public Result calculateProfits (String marketId, String winningOutcomeId) {
        if (isMarketValid(marketId)) {
            return new Result(ResultStatus.SUCCESSFUL, "Market profits calculated", (double)DependencyInjector.OutcomeApp().getProfitForMarket(marketId, winningOutcomeId).getResultObject());
        } else {
            return new Result(ResultStatus.FAILED, "Market is not valid", null);
        }
    }

    private boolean isMarketValid (String marketId) {
        Result sumResult = DependencyInjector.OutcomeApp().sumProbabilities(marketId);
        return sumResult.getResultStatus() == ResultStatus.SUCCESSFUL && (double) sumResult.getResultObject() >= 1;
    }

    public Result validate (String marketId) {
        return new Result(ResultStatus.SUCCESSFUL, "Validate market " + marketId, marketData.validateMarketId(marketId));
    }
}

package market;

import utilities.ResultStatus;

import java.util.ArrayList;

public class MarketData {
    private static MarketData marketData;
    private ArrayList<Market> markets;


    public static MarketData getInstance() {
        if (marketData == null){
            marketData = new MarketData();
        }
        return marketData;
    }

    public MarketData() {
        markets = new ArrayList<Market>();
    }

    public ResultStatus create (Market market) {
        try {
            markets.add(market);
            return ResultStatus.SUCCESSFUL;
        } catch (Exception e) {
            return ResultStatus.FAILED;
        }
    }

    public Market get (String marketId) throws MarketNotFoundException{
        for (Market m: markets) {
            if (m.getId().equals(marketId)) {
                return m;
            }
        }
        throw new MarketNotFoundException();
    }

    public ArrayList<Market> getMarketsForEvent(String eventId) {
        ArrayList<Market> marketsForEvent = new ArrayList<Market>();
        for (Market m : markets) {
            if (m.getEventId().equals(eventId)) {
                marketsForEvent.add(m);
            }
        }
        return marketsForEvent;
    }

    public boolean validateMarketId (String marketId) {
        try {
            return get(marketId) != null;
        } catch (MarketNotFoundException e) {
            return false;
        }
    }

    public ArrayList<Market> getAll() {
        return markets;
    }

    public void clear() {
        this.markets = new ArrayList<Market>();
    }
}

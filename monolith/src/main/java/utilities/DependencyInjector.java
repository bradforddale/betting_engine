package utilities;

import bet.BetApp;
import bet.BetData;
import event.EventApp;
import event.EventData;
import market.MarketApp;
import market.MarketData;
import outcome.OutcomeApp;
import outcome.OutcomeData;

public class DependencyInjector {
    public static EventApp EventApp() {
        return EventApp.getInstance();
    }

    public static MarketApp MarketApp() {
        return MarketApp.getInstance();
    }

    public static OutcomeApp OutcomeApp() {
        return OutcomeApp.getInstance();
    }

    public static BetApp BetApp() {
        return BetApp.getInstance();
    }

    public static EventData EventData() {
        return EventData.getInstance();
    }

    public static MarketData MarketData() {
        return MarketData.getInstance();
    }

    public static OutcomeData OutcomeData() {
        return OutcomeData.getInstance();
    }

    public static BetData BetData() {
        return BetData.getInstance();
    }

}

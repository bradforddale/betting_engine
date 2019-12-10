package utilities;

import java.time.LocalDateTime;

public class BettingValidation {
    public static boolean validateEventId(String eventId) {
        return (boolean) DependencyInjector.EventApp().validate(eventId).getResultObject();
    }
    public static boolean validateMarketId(String marketId) {
        return (boolean) DependencyInjector.MarketApp().validate(marketId).getResultObject();
    }
    public static boolean validateOutcomeId(String outcomeId) {
        return (boolean) DependencyInjector.OutcomeApp().validate(outcomeId).getResultObject();
    }

    public static boolean validateAmount (double amount) {
        return amount >= 0;
    }

    public static boolean validateProbability (double probability) {
        return probability >= 0 && probability <= 1;
    }

    public static boolean validateDescription (String description) {
        return description.length() > 0;
    }

//    The LocalDateTime object checks the date is valid
//    For now there's nothing else to check but will keep the methods here if that changes
    public static boolean validateStartTime (LocalDateTime startTime) {
        return true;
    }
    public static boolean validateEndTime (LocalDateTime endTime) {
        return true;
    }
}

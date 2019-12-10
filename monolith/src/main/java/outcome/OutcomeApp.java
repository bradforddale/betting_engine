package outcome;

import utilities.*;

public class OutcomeApp {
    private OutcomeData outcomeData;
    private static OutcomeApp outcomeApp;

    public static OutcomeApp getInstance() {
        if (outcomeApp == null){
            outcomeApp = new OutcomeApp(DependencyInjector.OutcomeData());
        }
        return outcomeApp;
    }

    private OutcomeApp(OutcomeData outcomeData) {
        this.outcomeData = outcomeData;
    }

    public Result createOutcome(String marketId, String description, double probability, String id) {
        try {
            Outcome outcome = new Outcome(marketId, description, probability, id);
            return _createOutcome(outcome);
        } catch (DataInvalidException d) {
            return new Result(ResultStatus.FAILED, "Outcome creation failed due to invalid data", null);
        }
    }

    public Result createOutcome(String marketId, String description, double probability) {
        try {
            Outcome outcome = new Outcome(marketId, description, probability);
            return _createOutcome(outcome);
        } catch (DataInvalidException d) {
            return new Result(ResultStatus.FAILED, "Outcome creation failed due to invalid data", null);
        }
    }

    private Result _createOutcome(Outcome outcome) {
        if (BettingValidation.validateMarketId(outcome.getMarketId())) {
            outcomeData.create(outcome);
            return new Result(ResultStatus.SUCCESSFUL, "Outcome created with id " + outcome.getId(), null);
        } else {
            return CommonResults.outcomeNotFoundResult(outcome.getId(), null);
        }
    }

    public Result changeProbabilityForOutcome(String outcomeId, double newProbability) {
        try {
            outcomeData.changeProbabilityForOutcome(outcomeId, newProbability);
            return new Result(ResultStatus.SUCCESSFUL, "Outcome probability changed", null);
        } catch (OutcomeNotFoundException o) {
            return CommonResults.outcomeNotFoundResult(outcomeId, null);
        } catch (Exception e) {
            return new Result(ResultStatus.FAILED, "Error occurred changing outcome probability: " + e.getMessage(), null);
        }
    }

    public Result validate (String outcomeId) {
        return new Result(ResultStatus.SUCCESSFUL, "Validate outcome " + outcomeId, outcomeData.validateOutcomeId(outcomeId));
    }

    public Result sumProbabilities(String marketId) {
        double sumProbabilities = 0.0;
        for (Outcome outcome: outcomeData.getOutcomesForMarket(marketId)) {
            sumProbabilities += outcome.getProbability();
        }
        return new Result(ResultStatus.SUCCESSFUL, "Sum of probabilities calculated", sumProbabilities);
    }

    public Result getProfitForMarket(String marketId, String winningOutcomeId) {
        return new Result(ResultStatus.SUCCESSFUL, "Profit calculated", calculateProfit(marketId, winningOutcomeId));
    }

    private double calculateProfit(String marketId, String winningOutcomeId) {
        double profit = 0;
        if (BettingValidation.validateOutcomeId(winningOutcomeId)) {
            for (Outcome o : outcomeData.getOutcomesForMarket(marketId)) {
                if (o.getId().equals(winningOutcomeId)) {
                    profit -= calculatePayout(o);
                } else {
                    profit += calculateTakings(o);
                }
            }
        }
        return profit;
    }

    public Result getPayoutFor(String outcomeId) {
        try {
            Outcome outcome = outcomeData.get(outcomeId);
            return new Result(ResultStatus.SUCCESSFUL, "Payout calculated", calculatePayout(outcome));
        } catch (OutcomeNotFoundException o) {
            return CommonResults.outcomeNotFoundResult(outcomeId, 0);
        }
    }

    private double calculatePayout(Outcome outcome) {
        return sumBets(outcome) * outcome.getDecimalOdds();
    }

    public Result getTakingsFor(String outcomeId) {
        try {
            Outcome outcome = outcomeData.get(outcomeId);
            return new Result(ResultStatus.SUCCESSFUL, "Takings calculated", calculateTakings(outcome));
        } catch (OutcomeNotFoundException o) {
            return CommonResults.outcomeNotFoundResult(outcomeId, 0);
        }
    }

    private double calculateTakings (Outcome outcome) {
        return sumBets(outcome);
    }

    private double sumBets (Outcome outcome) {
        return ((double) DependencyInjector.BetApp().sumBetsForOutcome(outcome.getId()).getResultObject());
    }


}

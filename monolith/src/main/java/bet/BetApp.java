package bet;

import utilities.*;

public class BetApp {
    private BetData betData;
    private static BetApp betApp;

    public static BetApp getInstance() {
        if (betApp == null){
            betApp = new BetApp(DependencyInjector.BetData());
        }
        return betApp;
    }

    private BetApp(BetData betData) {
        this.betData = betData;
    }

    public Result placeBet(String outcomeId, double amount) {
        try {
            Bet bet = new Bet(outcomeId, amount);
            return createBet(bet);
        } catch (DataInvalidException d) {
            return new Result(ResultStatus.FAILED, "Bet creation failed due to invalid data", null);
        }
    }

    public Result placeBet(String outcomeId, double amount, String id) {
        try {
            Bet bet = new Bet(outcomeId, amount, id);
            return createBet(bet);
        } catch (DataInvalidException d) {
            return new Result(ResultStatus.FAILED, "Bet creation failed due to invalid data", null);
        }
    }

    private Result createBet(Bet bet) {
       if (BettingValidation.validateOutcomeId(bet.getOutcomeId())) {
           betData.create(bet);
           return new Result(ResultStatus.SUCCESSFUL, "Bet placed", null);
       } else {
            return CommonResults.outcomeNotFoundResult(bet.getOutcomeId(), null);
       }
    }

    public Result getBet (String betId) {
        try {
            Bet bet = betData.get(betId);
            return new Result(ResultStatus.SUCCESSFUL, "Bet found", bet);
        } catch (BetNotFoundException b) {
            return CommonResults.betNotFoundResult(betId, null);
        }
    }

    public Result sumBetsForOutcome(String outcomeId) {
        double sum = 0;
        for (Bet b: betData.getBetsForOutcome(outcomeId)) {
            sum += b.getAmount();
        }
        return new Result(ResultStatus.SUCCESSFUL, "Sum of bets calculated", sum);
    }


}

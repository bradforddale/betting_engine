package bet;

import utilities.ResultStatus;

import java.util.ArrayList;

public class BetData {
    private ArrayList<Bet> bets;
    private static BetData betData;

    public static BetData getInstance() {
        if (betData == null){
            betData = new BetData();
        }
        return betData;
    }

    private BetData() {
        bets = new ArrayList<Bet>();
    }

    public ResultStatus create (Bet bet) {
        bets.add(bet);
        return ResultStatus.SUCCESSFUL;
    }

    public Bet get(String betId) throws  BetNotFoundException {
        for (Bet b: bets) {
            if (b.getId().equals(betId)) {
                return b;
            }
        }
        throw new BetNotFoundException();
    }

    public ArrayList<Bet> getBetsForOutcome(String outcomeId) {
        ArrayList<Bet> betsForOutcome = new ArrayList<Bet>();
        for (Bet b: bets) {
            if (b.getOutcomeId().equals(outcomeId)) {
                betsForOutcome.add(b);
            }
        }
        return betsForOutcome;
    }

    public ArrayList<Bet> getAll () {
        return bets;
    }

    public void clear() {
        this.bets = new ArrayList<Bet>();
    }
}

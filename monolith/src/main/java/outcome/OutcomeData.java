package outcome;

import utilities.DataInvalidException;
import utilities.ResultStatus;

import java.util.ArrayList;

public class OutcomeData {
    private ArrayList<Outcome> outcomes;
    private static OutcomeData outcomeData;

    public static OutcomeData getInstance() {
        if (outcomeData == null){
            outcomeData = new OutcomeData();
        }
        return outcomeData;
    }

    private OutcomeData () {
        outcomes = new ArrayList<Outcome>();
    }

    public ResultStatus create(Outcome outcome) {
        try {
            this.outcomes.add(outcome);
            return ResultStatus.SUCCESSFUL;
        } catch (Exception e) {
            return ResultStatus.FAILED;
        }
    }

    public Outcome get(String outcomeId) throws OutcomeNotFoundException {
        for (Outcome o: outcomes) {
            if (o.getId().equals(outcomeId)) {
                return o;
            }
        }
        throw new OutcomeNotFoundException();
    }

    public void changeProbabilityForOutcome(String outcomeId, double newProbability) throws OutcomeNotFoundException, DataInvalidException {
        get(outcomeId).setProbability(newProbability);
    }

    public boolean validateOutcomeId (String outcomeId) {
        try {
            return get(outcomeId) != null;
        } catch (OutcomeNotFoundException o) {
            return false;
        }
    }

    public ArrayList<Outcome> getOutcomesForMarket(String marketId) {
        ArrayList<Outcome> outcomesForMarket = new ArrayList<Outcome>();
        for (Outcome o: outcomes) {
            if (o.getMarketId().equals(marketId)) {
                outcomesForMarket.add(o);
            }
        }
        return outcomesForMarket;
    }

    public ArrayList<Outcome> getAll () {
        return outcomes;
    }

    public void clear() {
        this.outcomes = new ArrayList<Outcome>();
    }
}

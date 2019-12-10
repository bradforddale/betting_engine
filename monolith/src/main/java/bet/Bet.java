package bet;

import utilities.BettingUtilities;
import utilities.BettingValidation;
import utilities.DataInvalidException;

public class Bet {
    private String id;
    private double amount;
    private String outcomeId;

    public Bet( String outcomeId, double amount) throws DataInvalidException {
        validateFields(amount);
        this.id = BettingUtilities.generateId("BET");
        this.amount = amount;
        this.outcomeId = outcomeId;
    }

    public Bet(String outcomeId, double amount, String id) throws DataInvalidException {
        validateFields(amount);
        this.id = id;
        this.amount = amount;
        this.outcomeId = outcomeId;
    }

    private void validateFields(double amount) throws DataInvalidException {
        if (!(BettingValidation.validateAmount(amount))) {
            throw new DataInvalidException();
        }
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getOutcomeId() {
        return outcomeId;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (getClass() == o.getClass()) {
            Bet oBet = (Bet) o;
            return getId().equals(oBet.getId());
        } else {
            return false;
        }
    }

    public String toString() {
        return "Bet{" +
                "id='" + id + '\'' +
                ", betAmount=" + amount +
                ", outcomeId='" + outcomeId + '\'' +
                '}';
    }
}

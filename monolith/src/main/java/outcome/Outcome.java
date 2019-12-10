package outcome;

import utilities.BettingUtilities;
import utilities.BettingValidation;
import utilities.DataInvalidException;

public class Outcome {
    private String id;
    private String marketId;
    private String description;
    private double probability;

    public Outcome(String marketId, String description, double probability) throws DataInvalidException {
        validateFields(description, probability);
        this.id = BettingUtilities.generateId("OUTCOME");
        this.marketId = marketId;
        this.description = description;
        this.probability = probability;
    }

    public Outcome(String marketId, String description, double probability, String id) throws DataInvalidException {
        validateFields(description, probability);
        this.id = id;
        this.marketId = marketId;
        this.description = description;
        this.probability = probability;
    }

    private void validateFields(String description, double probability ) throws DataInvalidException {
        if (!(BettingValidation.validateDescription(description)
            && BettingValidation.validateProbability(probability))) {
            throw new DataInvalidException();
        }
    }

    public String getId() {
        return id;
    }

    public String getMarketId() {
        return marketId;
    }

    public String getDescription() {
        return description;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) throws DataInvalidException {
        if (BettingValidation.validateProbability(probability)) {
            this.probability = probability;
        } else {
            throw new DataInvalidException();
        }
    }

    public double getDecimalOdds () {
        return 1/this.probability;
    }

    public String toString() {
        return "Outcome{" +
                "id='" + id + '\'' +
                ", marketId='" + marketId + '\'' +
                ", description='" + description + '\'' +
                ", probability=" + probability +
                '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (getClass() == o.getClass()) {
            Outcome oOutcome = (Outcome) o;
            return getId().equals(oOutcome.getId());
        } else {
            return false;
        }
    }
}

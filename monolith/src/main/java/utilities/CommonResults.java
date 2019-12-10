package utilities;

public class CommonResults {

    public static Result outcomeNotFoundResult (String outcomeId, Object resultObject) {
        return new Result(ResultStatus.FAILED, "Outcome not found for id " + outcomeId, resultObject);
    }

    public static Result eventNotFoundResult (String eventId, Object resultObject) {
        return new Result(ResultStatus.FAILED, "Event not found for id " + eventId, resultObject);
    }

    public static Result marketNotFoundResult (String marketId, Object resultObject) {
        return new Result(ResultStatus.FAILED, "Market not found for id " + marketId, resultObject);
    }

    public static Result betNotFoundResult (String betId, Object resultObject) {
        return new Result(ResultStatus.FAILED, "Bet not found for id " + betId, resultObject);
    }
}

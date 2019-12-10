package utilities;

public class Result {
    private ResultStatus resultStatus;
    private String resultMessage;
    private Object resultObject;

    public Result(ResultStatus resultStatus, String resultMessage, Object resultObject) {
        this.resultStatus = resultStatus;
        this.resultMessage = resultMessage;
        this.resultObject = resultObject;
    }

    public ResultStatus getResultStatus() {
        return resultStatus;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public Object getResultObject() {
        return resultObject;
    }

    public String toString() {
        return "Result{" +
                "resultStatus=" + resultStatus +
                ", resultMessage='" + resultMessage + '\'' +
                ", resultObject=" + resultObject +
                '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (getClass() == o.getClass()) {
            Result oResult = (Result) o;
            return getResultStatus().equals(oResult.getResultStatus())
                    && getResultMessage().equals(oResult.getResultMessage())
                    && ((getResultObject() == null && oResult.getResultObject() == null) || (getResultObject().equals(oResult.getResultObject())));

        } else {
            return false;
        }
    }
}

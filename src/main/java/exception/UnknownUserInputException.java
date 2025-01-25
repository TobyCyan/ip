package exception;

public class UnknownUserInputException extends MeiException {
    public UnknownUserInputException(String[] errorResponses) {
        super(errorResponses);
    }
}

package exception;

public class EventNotEnoughInfoException extends MeiException {

    public EventNotEnoughInfoException(String[] errorResponses) {
        super(errorResponses);
    }

}

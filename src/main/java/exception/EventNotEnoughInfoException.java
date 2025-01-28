package exception;

/**
 * The Mei exception that is thrown when the event task given does not provide enough information to be interpreted.
 * echoErrorResponse should be called when this exception is caught.
 * This is classified as a task-related exception.
 * The error response is given based on the string array retrieved from the RESPONSE_MAP in the Response Manager
 * with the key of the same name excluding the exception word at the end.
 */
public class EventNotEnoughInfoException extends MeiException {

    public EventNotEnoughInfoException(String[] errorResponses) {
        super(errorResponses);
    }

}

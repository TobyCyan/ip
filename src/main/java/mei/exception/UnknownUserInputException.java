package mei.exception;

/**
 * The Mei exception that is thrown when the user gives an input undefined to Mei.
 * The input known to Mei are all specified in the redirectInput function within the Input Manager class.
 * echoErrorResponse should be called when this exception is caught.
 * The error response is given based on the string array retrieved from the RESPONSE_MAP in the Response Manager
 * with the key of the same name excluding the exception word at the end.
 */
public class UnknownUserInputException extends MeiException {
    public UnknownUserInputException(String[] errorResponses) {
        super(errorResponses);
    }
}

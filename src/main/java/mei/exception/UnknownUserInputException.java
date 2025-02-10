package mei.exception;

/**
 * Represents the Mei exception that is thrown when the user gives an input undefined to Mei.
 * The input known to Mei are all specified in the redirectInput function within the Input Manager class.
 * echoErrorResponse should be called when this exception is caught.
 */
public class UnknownUserInputException extends MeiException {
    private static final String[] ERROR_RESPONSES = new String[] {
        "Come again? I don't quite get what you are saying."
    };

    public UnknownUserInputException() {
        super(ERROR_RESPONSES);
    }
}

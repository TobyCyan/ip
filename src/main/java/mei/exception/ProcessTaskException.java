package mei.exception;

/**
 * Represents the base class for exceptions related to task processing.
 */
public class ProcessTaskException extends MeiException {
    public ProcessTaskException(String[] errorResponses) {
        super(errorResponses);
    }
}

package mei.exception;

import mei.manager.ResponseManager;

/**
 * Represents the parent class for all exceptions specific to Mei.
 * This class is a throwable and should be called using echoErrorResponse when caught.
 * This class also holds an array of error response strings.
 */
public class MeiException extends Throwable {
    private final String[] errorResponses;

    public MeiException(String[] errorResponses) {
        this.errorResponses = errorResponses;
    }

    /**
     * Prompts the error responses to the user when a Mei exception is thrown.
     * This method should be called whenever a Mei exception is caught by the try-catch clause.
     * The error response array is defined at the construction of any Mei exceptions.
     */
    public void echoErrorResponse() {
        ResponseManager.setInputsAsApplicationResponse(errorResponses);
    }

}

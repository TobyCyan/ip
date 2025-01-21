public class MeiException extends Throwable {
    private final String[] errorResponses;

    public MeiException(String[] errorResponses) {
        this.errorResponses = errorResponses;
    }
    public void echoErrorResponse() {
        ResponseManager.echoLines(errorResponses);
    }

}

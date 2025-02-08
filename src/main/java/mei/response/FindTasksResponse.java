package mei.response;

/**
 * Represents the response to return the found tasks back to the user.
 * This class handles the case where the find results is empty.
 */
public class FindTasksResponse extends Response {
    private static final String[] RESPONSES = new String[] {
        "Alright! Here are the tasks that I found from your list:",
        "Hope I didn't miss any!"
    };

    private static final String[] RESPONSES_NO_RESULT = new String[] {
        "Sorry! It seems like there isn't any tasks"
            + " that matched the description :("
    };

    private String[] foundTasks;

    public FindTasksResponse(String[] foundTasks) {
        this.foundTasks = foundTasks;
    }

    @Override
    public void formResponsesAndSet() {
        if (foundTasks.length == 0) {
            setInputsAsApplicationResponse(RESPONSES_NO_RESULT);
            return;
        }

        String[] formattedResponses = RESPONSES.clone();
        formattedResponses = concatResponses(formattedResponses, foundTasks);

        setInputsAsApplicationResponse(formattedResponses);
    }
}

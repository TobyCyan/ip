package mei.response;

/**
 * Represents the response to list all user tasks.
 */
public class ListTasksResponse extends Response {
    private static final String[] RESPONSES = new String[] {
        "Sure! Here are all your tasks!",
        "Enjoy :3"
    };

    private static final String[] RESPONSES_NO_TASK = new String[] {
        "I can't find any tasks for you :(",
        "Maybe start adding new tasks?"
    };

    private String[] tasksToBeDisplayed;

    public ListTasksResponse(String[] tasksToBeDisplayed) {
        this.tasksToBeDisplayed = tasksToBeDisplayed;
    }

    @Override
    public void formResponsesAndSet() {
        if (tasksToBeDisplayed == null) {
            setInputsAsApplicationResponse(RESPONSES_NO_TASK);
            return;
        }

        String[] formattedResponses = RESPONSES.clone();
        formattedResponses = concatResponses(formattedResponses, tasksToBeDisplayed);

        setInputsAsApplicationResponse(formattedResponses);
    }
}

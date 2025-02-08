package mei.response;

import mei.tasks.Task;

/**
 * Represents the response to unmark an existing user task.
 */
public class UnmarkTaskResponse extends Response {
    private static final String[] RESPONSES = new String[] {
        "It's alright to take things easy.",
        "I've unchecked this task for you to revisit next time!"
    };

    private Task taskToIncludeInResponses;

    public UnmarkTaskResponse(Task taskToIncludeInResponses) {
        this.taskToIncludeInResponses = taskToIncludeInResponses;
    }

    @Override
    public void formResponsesAndSet() {
        String[] formattedResponses = RESPONSES.clone();
        formattedResponses = appendTaskStringToResponseArrayAndReturn(formattedResponses,
                taskToIncludeInResponses.toString());

        setInputsAsApplicationResponse(formattedResponses);
    }
}

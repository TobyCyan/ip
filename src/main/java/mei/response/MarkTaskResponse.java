package mei.response;

import mei.task.Task;

/**
 * Represents the response to mark an existing user task.
 */
public class MarkTaskResponse extends Response {
    private static final String[] RESPONSES = new String[] {
        "You've completed this? That's amazing!",
        "I've noted down your achievement, congratulations!"
    };

    private Task taskToIncludeInResponses;

    public MarkTaskResponse(Task taskToIncludeInResponses) {
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

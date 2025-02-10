package mei.response;

import mei.task.Task;

/**
 * Represents the response to the user when deleting an existing task.
 */
public class DeleteTaskResponse extends Response {
    private static final String[] RESPONSES = new String[] {
        "Got it! I will erase this task from my list.",
        "The removed task is:\n",
        "The amount of tasks left for you is: "
    };
    private Task taskToIncludeInResponses;
    private int totalTasksToShow;

    /**
     * Initializes the delete task response.
     * The deleted task is to be included as part of the response to the user.
     *
     * @param taskToIncludeInResponses The deleted task to include in the responses.
     * @param totalTasksToShow The total amount of user tasks to show.
     */
    public DeleteTaskResponse(Task taskToIncludeInResponses, int totalTasksToShow) {
        this.taskToIncludeInResponses = taskToIncludeInResponses;
        this.totalTasksToShow = totalTasksToShow;
    }

    @Override
    public void formResponsesAndSet() {
        String[] formattedResponses = RESPONSES.clone();

        // Index variables where information should be appended to.
        int totalTaskStringIndex = RESPONSES.length - 1;
        int taskStringIndex = 1;

        // Append the newly updated information to the responses.
        formattedResponses[taskStringIndex] += taskToIncludeInResponses.toString();
        formattedResponses[totalTaskStringIndex] += totalTasksToShow;

        // Echo the responses.
        setInputsAsApplicationResponse(formattedResponses);
    }
}

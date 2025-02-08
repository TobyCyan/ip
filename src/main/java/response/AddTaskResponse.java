package response;

import mei.tasks.Task;

/**
 * Represents the response to the user when adding a new task.
 */
public class AddTaskResponse extends Response {
    private static final String[] RESPONSES = new String[] {
        "Task successfully added! Yay!",
        "Your added task is:\n",
        "The total tasks you currently have is: "
    };
    private Task taskToIncludeInResponses;
    private int totalTasksToShow;

    /**
     * Initializes the add task response.
     * The new task is processed to be included as part of the response to the user.
     *
     * @param taskToIncludeInResponses The task to include in the responses.
     * @param totalTasksToShow The total amount of user tasks to show.
     */
    public AddTaskResponse(Task taskToIncludeInResponses, int totalTasksToShow) {
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

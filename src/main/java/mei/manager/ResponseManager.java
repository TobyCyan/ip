package mei.manager;

import java.util.Arrays;
import java.util.HashMap;

import mei.tasks.Task;
import response.AddTaskResponse;
import response.DeleteTaskResponse;
import response.FindTasksResponse;
import response.ListTasksResponse;
import response.MarkTaskResponse;
import response.UnmarkTaskResponse;

/**
 * This is the manager responsible for managing Mei's responses.
 * Any calls to make Mei speak or make a response should be done via here.
 */
public class ResponseManager {
    private static final HashMap<String, String[]> RESPONSE_MAP = new HashMap<>();
    private final TaskManager taskManager;

    /**
     * Initializes a response map which maps the response type to its array of responses.
     * The response map is given the final modifier as no modifications should be made upon initialization.
     */
    public ResponseManager(TaskManager taskManager) {
        // Task manager.
        this.taskManager = taskManager;
    }

    /**
     * Makes a new add task response object.
     * and does all the necessary processing before setting it as the application response
     * to prompt back to the user.
     *
     * @param task The task successfully added to be echoed.
     */
    public void makeNewAddTaskResponse(Task task) {
        int totalTasks = taskManager.getTotalTasks();

        AddTaskResponse addTaskResponse = new AddTaskResponse(task, totalTasks);
        addTaskResponse.formResponsesAndSet();
    }

    /**
     * Makes a new delete task response object.
     * and does all the necessary processing before setting it as the application response
     * to prompt back to the user.
     *
     * @param deletedTask The deleted task.
     */
    public void makeDeleteTaskResponse(Task deletedTask) {
        int totalTasks = taskManager.getTotalTasks();

        DeleteTaskResponse deleteTaskResponse = new DeleteTaskResponse(deletedTask, totalTasks);
        deleteTaskResponse.formResponsesAndSet();
    }

    /**
     * Makes a new list tasks response object.
     * and does all the necessary processing before setting it as the application response
     * to prompt back to the user.
     * This considers the case where there is no task to list.
     *
     * @param tasksToBeDisplayed The list of valid tasks to be displayed to the user.
     */
    public void makeListTasksResponse(String[] tasksToBeDisplayed) {
        ListTasksResponse listTasksResponse = new ListTasksResponse(tasksToBeDisplayed);
        listTasksResponse.formResponsesAndSet();
    }

    /**
     * Makes a new mark task response object
     * and does all the necessary processing before setting it as the application response
     * to prompt back to the user.
     *
     * @param markedTask The task to be marked as completed.
     */
    public void makeMarkTaskResponse(Task markedTask) {
        MarkTaskResponse markTaskResponse = new MarkTaskResponse(markedTask);
        markTaskResponse.formResponsesAndSet();
    }

    /**
     * Makes a new unmark task response object
     * and does all the necessary processing before setting it as the application response
     * to prompt back to the user.
     *
     * @param unmarkedTask The task to be marked as incomplete.
     */
    public void makeUnmarkTaskResponse(Task unmarkedTask) {
        UnmarkTaskResponse unmarkTaskResponse = new UnmarkTaskResponse(unmarkedTask);
        unmarkTaskResponse.formResponsesAndSet();
    }

    /**
     * Makes a new find task response object
     * and does all the necessary processing before setting it as the application response
     * to prompt back to the user.
     * This considers the case where no tasks are found.
     *
     * @param foundTasks The list of found tasks to be displayed to the user.
     */
    public void makeFindTasksResponse(String[] foundTasks) {
        FindTasksResponse findTasksResponse = new FindTasksResponse(foundTasks);
        findTasksResponse.formResponsesAndSet();
    }

    /**
     * Appends a task as its string representation defined in its class to a response array
     * corresponding to the given array key.
     * This method also returns the final appended array.
     *
     * @param arrayKey The key to fetch the response array from the RESPONSE_MAP.
     * @param taskString The task represented as a string.
     * @return The resulting appended array.
     */
    public String[] appendTaskStringToResponseArrayAndReturn(String arrayKey, String taskString) {
        String[] taskResponses = getResponses(arrayKey);
        int unmarkTaskResponseLength = taskResponses.length + 1;
        taskResponses = Arrays.copyOf(taskResponses, unmarkTaskResponseLength);
        taskResponses[unmarkTaskResponseLength - 1] = taskString;
        return taskResponses;
    }

    /**
     * Concatenates 2 arrays, first followed by the second.
     * If the arrays are empty, an empty string array is returned.
     * However, we expect both the first and second arrays are always non-empty.
     *
     * @param first The first array.
     * @param second The second array.
     * @return The resulting array after concatenation.
     */
    public String[] concatResponses(String[] first, String[] second) {
        int firstLength = first.length;
        int secondLength = second.length;
        String[] result = new String[firstLength + secondLength];

        System.arraycopy(first, 0, result, 0, firstLength);
        System.arraycopy(second, 0, result, firstLength, firstLength + secondLength - firstLength);

        return result;
    }

    /**
     * Get the array of responses from the response map.
     *
     * @param key The hash key of the responses.
     * @return The array of responses corresponding to the given key.
     */
    public static String[] getResponses(String key) {
        return RESPONSE_MAP.get(key);
    }

}

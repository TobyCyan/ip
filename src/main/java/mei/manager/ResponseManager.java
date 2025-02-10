package mei.manager;

import mei.response.AddTaskResponse;
import mei.response.DeleteTaskResponse;
import mei.response.FindTasksResponse;
import mei.response.ListTasksResponse;
import mei.response.MarkTaskResponse;
import mei.response.UnmarkTaskResponse;
import mei.task.Task;

/**
 * This is the manager responsible for managing Mei's responses.
 * Any calls to make Mei speak or make a response should be done via here.
 */
public class ResponseManager {
    private final TaskManager taskManager;

    /**
     * Initializes a response map which maps the response type to its array of responses.
     * The response map is given the final modifier as no modifications should be made upon initialization.
     */
    public ResponseManager(TaskManager taskManager) {
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
        assert totalTasks >= 1 : "total tasks after adding a new task should be at least 1";

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

}

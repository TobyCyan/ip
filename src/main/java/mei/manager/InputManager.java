package mei.manager;

import mei.exception.EmptyTaskDescriptionException;
import mei.exception.MeiException;
import mei.exception.TaskIndexOutOfBoundsException;
import mei.exception.UnknownTaskTypeException;
import mei.exception.UnknownUserInputException;
import mei.tasks.Task;

/**
 *  Represents the manager for all user inputs towards the interaction with Mei.
 *  This class contains methods to interpret user input and redirect it to the appropriate managers.
 *  Acts as the middle-man between the user and other managers.
 */
public class InputManager {
    private final TaskManager taskManager;
    private final ResponseManager responseManager;

    /**
     * Initializes the input manager.
     * This manager is also connected to the task manager and response manager.
     *
     * @param taskManager The task manager.
     * @param responseManager The response manager.
     */
    public InputManager(TaskManager taskManager, ResponseManager responseManager) {
        this.taskManager = taskManager;
        this.responseManager = responseManager;
    }

    /**
     * Interprets and redirects all incoming inputs to their respective functions.
     * This should serve as a middle-man function between the user and the manager functions.
     * This method can only receive a limited number of commands based on the keyword
     * (i.e. the first word of the given input.)
     * and the command is assumed to be a task command if none of the defined cases match.
     *
     * @param input The user input to redirect.
     */
    public void redirectInput(String input) {
        String[] splitInput = input.split(" ", 2);
        String keyword = splitInput[0];

        switch (keyword) {
        case "list":
            redirectToListTasks();
            break;

        case "mark":
            redirectToMarkTaskOfIndex(splitInput[1]);
            break;

        case "unmark":
            redirectToUnMarkTaskOfIndex(splitInput[1]);
            break;

        case "delete":
            redirectToDeleteTaskOfIndex(splitInput[1]);
            break;

        case "find":
            redirectToFindTasks(splitInput);
            break;

        default:
            redirectToAddTask(splitInput);

        }
    }

    /**
     * Handles the task index and throws exception if it is problematic.
     * The task index is considered problematic if a task of that index does not exist.
     *
     * @param taskIndex The task index to handle.
     * @return true or false depending on whether the task index is problematic or not.
     */
    public boolean isTaskIndexProblematic(int taskIndex) {
        try {
            if (!taskManager.isTaskIndexValid(taskIndex)) {
                throw new TaskIndexOutOfBoundsException();
            }
        } catch (TaskIndexOutOfBoundsException e) {
            e.echoErrorResponse();
            return true;
        }
        return false;
    }

    private void redirectToListTasks() {
        String[] tasksToBeListed = taskManager.getTaskStringsToDisplay();
        responseManager.makeListTasksResponse(tasksToBeListed);
    }

    private void redirectToMarkTaskOfIndex(String taskIndexString) {
        int taskIndexToMark = Integer.parseInt(taskIndexString);
        if (isTaskIndexProblematic(taskIndexToMark)) {
            return;
        }

        Task markedTask = taskManager.markTask(taskIndexToMark);
        responseManager.makeMarkTaskResponse(markedTask);
    }

    private void redirectToUnMarkTaskOfIndex(String taskIndexString) {
        int taskIndexToUnmark = Integer.parseInt(taskIndexString);
        if (isTaskIndexProblematic(taskIndexToUnmark)) {
            return;
        }

        Task unmarkedTask = taskManager.unmarkTask(taskIndexToUnmark);
        responseManager.makeUnmarkTaskResponse(unmarkedTask);
    }

    private void redirectToDeleteTaskOfIndex(String taskIndexString) {
        int taskIndexToDelete = Integer.parseInt(taskIndexString);
        if (isTaskIndexProblematic(taskIndexToDelete)) {
            return;
        }

        Task deletedTask = taskManager.deleteTask(taskIndexToDelete);
        responseManager.makeDeleteTaskResponse(deletedTask);
    }

    private void redirectToFindTasks(String[] splitInput) {
        try {
            if (splitInput.length == 1) {
                throw new EmptyTaskDescriptionException();
            }
            String[] foundTasksAsStrings = taskManager.findTasksToDisplay(splitInput[1]);
            responseManager.makeFindTasksResponse(foundTasksAsStrings);

        } catch (EmptyTaskDescriptionException e) {
            e.echoErrorResponse();
        }
    }

    private void redirectToAddTask(String[] splitInput) {
        // Task types.
        try {
            String taskType = splitInput[0];

            // First word doesn't fit in any of the commands nor task types.
            if (!taskManager.isTaskTypeExist(taskType)) {
                throw new UnknownUserInputException();
            }

            // User input only has a single word. i.e. no description.
            if (splitInput.length == 1) {
                throw new EmptyTaskDescriptionException();
            }

            String description = splitInput[1];
            Task addedTask = taskManager.processAddTask(taskType, description);

            // No new task is created, which means task type is unknown
            // or task description does not contain enough information to create a new task.
            if (addedTask == null) {
                throw new UnknownTaskTypeException();
            } else {
                responseManager.makeNewAddTaskResponse(addedTask);
            }

        } catch (MeiException e) {
            e.echoErrorResponse();
        }
    }
}

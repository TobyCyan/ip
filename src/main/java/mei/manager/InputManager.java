package mei.manager;

import mei.exception.*;
import mei.tasks.Task;

/**
 *  Represents the manager for all user inputs towards the interaction with Mei.
 *  This class contains methods to interpret user input and redirect it to the appropriate managers.
 *  Acts as the middle-man between the user and other managers.
 */
public class InputManager {
    private final TaskManager taskManager;
    private final ResponseManager responseManager;
    
    public InputManager(TaskManager taskManager, ResponseManager responseManager) {
        this.taskManager = taskManager;
        this.responseManager = responseManager;
    }

    /**
     * Interprets and redirects all incoming inputs to their respective functions.
     * This should serve as a middle-man function between the user and the manager functions.
     * This method can only receive a limited number of commands based on the keyword (i.e. the first word of the given input.)
     * and the command is assumed to be a task command if none of the defined cases match.
     *
     * @param input The user input to redirect.
     */
    public void redirectInput(String input) {
        String[] splitInput = input.split(" ", 2);
        String keyword = splitInput[0];

        switch (keyword) {
        case "list":
            String[] tasksToBeListed = taskManager.getTaskStringsToDisplay();
            if (tasksToBeListed == null) {
                responseManager.noTaskResponse();
                break;
            } else {
                responseManager.listTasksResponse(tasksToBeListed);
            }
            break;

        case "mark":
            int taskIndexToMark = Integer.parseInt(splitInput[1]);
            if (isTaskIndexProblematic(taskIndexToMark)) {
                break;
            }
            Task markedTask = taskManager.markTask(taskIndexToMark);

            assert markedTask != null : "marked task should never be null";

            responseManager.markTaskResponse(markedTask);
            break;

        case "unmark":
            int taskIndexToUnmark = Integer.parseInt(splitInput[1]);
            if (isTaskIndexProblematic(taskIndexToUnmark)) {
                break;
            }
            Task unmarkedTask = taskManager.unmarkTask(taskIndexToUnmark);

            assert unmarkedTask != null : "unmarked task should never be null";

            responseManager.unmarkTaskResponse(unmarkedTask);
            break;

        case "delete":
            int taskIndexToDelete = Integer.parseInt(splitInput[1]);
            if (isTaskIndexProblematic(taskIndexToDelete)) {
                break;
            }
            Task deletedTask = taskManager.deleteTask(taskIndexToDelete);

            assert deletedTask != null : "deleted task should never be null";

            responseManager.deleteTaskResponse(deletedTask);
            break;

        case "find":
            try {
                if (splitInput.length == 1) {
                    throw new EmptyTaskDescriptionException(ResponseManager.getResponses("EmptyTaskSearchDescription"));
                }
                String[] foundTasksAsStrings = taskManager.findTasksToDisplay(splitInput[1]);
                responseManager.findTasksResponse(foundTasksAsStrings);

            } catch (MeiException e) {
                e.echoErrorResponse();
            }
            break;

        default:
            // Task types.
            try {
                // First word doesn't fit in any of the commands nor task types.
                if (!taskManager.isTaskTypeExist(splitInput[0])) {
                    throw new UnknownUserInputException(ResponseManager.getResponses("UnknownUserInput"));
                }

                // User input only has a single word. i.e. no description.
                if (splitInput.length == 1) {
                    throw new EmptyTaskDescriptionException(ResponseManager.getResponses("EmptyTaskDescription"));
                }

                Task addedTask = taskManager.processAddTask(keyword, splitInput[1]);
                // No new task is created, which means task type is unknown or task description does not contain enough information to create a new task.
                if (addedTask == null) {
                    throw new UnknownTaskTypeException(ResponseManager.getResponses("UnknownTaskType"));
                } else {
                    responseManager.addTaskResponse(addedTask);
                }
            } catch (MeiException e) {
                e.echoErrorResponse();
            }
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
                throw new TaskIndexOutOfBoundsException(ResponseManager.getResponses("TaskIndexOutOfBounds"));
            }
        } catch (MeiException e) {
            e.echoErrorResponse();
            return true;
        }
        return false;
    }
}

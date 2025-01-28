package manager;

import exception.*;
import tasks.Task;

public class InputManager {
    private TaskManager taskManager;
    private ResponseManager responseManager;
    
    public InputManager(TaskManager taskManager, ResponseManager responseManager) {
        this.taskManager = taskManager;
        this.responseManager = responseManager;
    }

    /**
     * A function to redirect all incoming inputs to their respective functions.
     * This should serve as a middle-man function between the user and the manager functions
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
            responseManager.markTaskResponse(markedTask);
            break;

        case "unmark":
            int taskIndexToUnmark = Integer.parseInt(splitInput[1]);
            if (isTaskIndexProblematic(taskIndexToUnmark)) {
                break;
            }
            Task unmarkedTask = taskManager.unmarkTask(taskIndexToUnmark);
            responseManager.unmarkTaskResponse(unmarkedTask);
            break;

        case "delete":
            int taskIndexToDelete = Integer.parseInt(splitInput[1]);
            if (isTaskIndexProblematic(taskIndexToDelete)) {
                break;
            }
            Task deletedTask = taskManager.deleteTask(taskIndexToDelete);
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

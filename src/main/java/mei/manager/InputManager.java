package mei.manager;

import mei.exception.EmptyMostRecentReversedInputException;
import mei.exception.EmptyTaskDescriptionException;
import mei.exception.MeiException;
import mei.exception.TaskIndexOutOfBoundsException;
import mei.exception.UnknownTaskTypeException;
import mei.exception.UnknownUserInputException;
import mei.task.Task;

/**
 *  Represents the manager for all user inputs towards the interaction with Mei.
 *  This class contains methods to interpret user input and redirect it to the appropriate managers.
 *  Acts as the middle-man between the user and other managers.
 */
public class InputManager {
    private static String mostRecentReversedInput;
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
    public void redirectInput(String input, boolean isUndoCommand) {
        String[] splitInput = input.split(" ", 2);
        String keyword = splitInput[0];
        boolean shouldUpdateMostRecentReversedInput = false;

        switch (keyword) {
        case "list":
            redirectToListTasks();
            break;

        case "mark":
            shouldUpdateMostRecentReversedInput = isSuccessRedirectToMarkTaskOfIndex(splitInput[1]);
            break;

        case "unmark":
            shouldUpdateMostRecentReversedInput = isSuccessRedirectToUnMarkTaskOfIndex(splitInput[1]);
            break;

        case "delete":
            shouldUpdateMostRecentReversedInput = isSuccessRedirectToDeleteTaskOfIndex(splitInput[1]);
            break;

        case "find":
            redirectToFindTasks(splitInput);
            break;

        case "undo":
            redirectToUndo();
            break;

        default:
            shouldUpdateMostRecentReversedInput = isSuccessRedirectToAddTask(splitInput);
        }

        // Reset the most recent reversed input if undo-ing.
        if (isUndoCommand) {
            mostRecentReversedInput = null;
            return;
        }

        if (shouldUpdateMostRecentReversedInput) {
            mostRecentReversedInput = reverseInput(splitInput);
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

    private boolean isSuccessRedirectToMarkTaskOfIndex(String taskIndexString) {
        int taskIndexToMark = Integer.parseInt(taskIndexString);
        if (isTaskIndexProblematic(taskIndexToMark)) {
            return false;
        }

        Task markedTask = taskManager.markTask(taskIndexToMark);
        responseManager.makeMarkTaskResponse(markedTask);
        return true;
    }

    private boolean isSuccessRedirectToUnMarkTaskOfIndex(String taskIndexString) {
        int taskIndexToUnmark = Integer.parseInt(taskIndexString);
        if (isTaskIndexProblematic(taskIndexToUnmark)) {
            return false;
        }

        Task unmarkedTask = taskManager.unmarkTask(taskIndexToUnmark);
        responseManager.makeUnmarkTaskResponse(unmarkedTask);
        return true;
    }

    private boolean isSuccessRedirectToDeleteTaskOfIndex(String taskIndexString) {
        int taskIndexToDelete = Integer.parseInt(taskIndexString);
        if (isTaskIndexProblematic(taskIndexToDelete)) {
            return false;
        }

        Task deletedTask = taskManager.deleteTask(taskIndexToDelete);
        responseManager.makeDeleteTaskResponse(deletedTask);
        return true;
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

    private boolean isSuccessRedirectToAddTask(String[] splitInput) {
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
            Task addedTask = taskManager.processAddTask(String.join(" ", splitInput), taskType, description);

            // No new task is created, which means task type is unknown
            // or task description does not contain enough information to create a new task.
            if (addedTask == null) {
                throw new UnknownTaskTypeException();
            } else {
                responseManager.makeNewAddTaskResponse(addedTask);
                return true;
            }

        } catch (MeiException e) {
            e.echoErrorResponse();
        }

        return false;
    }

    private void redirectToUndo() {
        try {
            if (mostRecentReversedInput == null) {
                throw new EmptyMostRecentReversedInputException();
            }
            assert !mostRecentReversedInput.isEmpty()
                    : "most recent reversed input must be initialized.";

            redirectInput(mostRecentReversedInput, true);

        } catch (EmptyMostRecentReversedInputException e) {
            e.echoErrorResponse();
        }
    }

    /**
     * Reverses the input to the opposite command.
     * e.g. add -> delete, mark -> unmark and vice versa.
     * This method assumes that only reversible inputs are passed in.
     * The input is also mutated here.
     *
     * @param splitInput The split input to reverse.
     * @return The reversed input command.
     */
    public String reverseInput(String[] splitInput) {
        String command = splitInput[0];
        String reversedCommand;

        switch (command) {
        case "mark":
            reversedCommand = convertToUnmarkCommand(splitInput[1]);
            break;

        case "unmark":
            reversedCommand = convertToMarkCommand(splitInput[1]);
            break;

        case "delete":
            reversedCommand = convertToAddCommand(splitInput);
            break;

        default:
            // case add.
            reversedCommand = convertToDeleteCommand(splitInput);
        }

        return reversedCommand;
    }

    private String convertToDeleteCommand(String[] splitInput) {
        String deleteCommand = "delete";
        String taskIndexToDelete = String.valueOf(taskManager.getTotalTasks());
        return deleteCommand + " " + taskIndexToDelete;
    }

    private String convertToAddCommand(String[] splitInput) {
        return taskManager.getMostRecentDeletedTaskAddCommand();
    }

    private String convertToMarkCommand(String taskIndexString) {
        String markCommand = "mark";
        return markCommand + " " + taskIndexString;
    }

    private String convertToUnmarkCommand(String taskIndexString) {
        String unmarkCommand = "unmark";
        return unmarkCommand + " " + taskIndexString;
    }

}

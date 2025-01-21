import java.util.Arrays;
import java.util.HashMap;

/**
 * This is the manager responsible for managing Mei's responses.
 * Any calls to make Mei speak or make a response should be done via here.
 */
public class ResponseManager {
    private final HashMap<String, String[]> responseMap = new HashMap<>();
    private static TaskManager taskManager = null;
    private static ResponseManager instance = null;

    /**
     * The constructor.
     * Initializes a response map which maps the response type to its array of responses.
     * The response map is given the final modifier as no modifications should be made upon initialization.
     * Made private to prevent direct initialization.
     * Should call getInstance instead.
     */
    private ResponseManager() {
        // Basic greetings.
        responseMap.put("Greeting", new String[] {"Hello! My name is Mei!", "What can I do for you?"});
        responseMap.put("Exit", new String[] {"See you next time! :)"});

        // Listing user tasks.
        responseMap.put("ListTasks", new String[] {"Sure! Here are all your tasks!", "Enjoy :3"});
        responseMap.put("NoTask", new String[] {"I can't find any tasks for you :(", "Maybe start adding new tasks?"});

        // Adding new task.
        responseMap.put("AddTask", new String[] {"Certainly! Your new task is on the way!"});
        responseMap.put("AddTaskSuccess", new String[] {"Task successfully added! Yay!", "Your added task is:\n", "The total tasks you currently have is: "});

        // Marking and unmarking existing tasks.
        responseMap.put("MarkTask", new String[] {"You've completed this? That's amazing!", "I've noted down your achievement, congratulations!"});
        responseMap.put("UnmarkTask", new String[] {"It's alright to take things easy.", "I've unchecked this task for you to revisit next time!"});

        // Exception responses.
        responseMap.put("UnknownTaskType", new String[] {"Oops! I think you may have entered an unknown task type! Please try again!", "The accepted tasks are todo, deadline, and event :))"});
        responseMap.put("EmptyTaskDescription", new String[] {"Remember to add a description to your tasks, okay?"});
        responseMap.put("TaskIndexOutOfBounds", new String[] {"Hmm..? This task number doesn't seem to be on the list...", "Can you repeat with a valid one? :3"});
    }

    /**
     * @@author TobyCyan-reused.
     * Reused from geeksforgeeks.org/singleton-class-java
     * with minor modifications.
     * Function to get the singleton instance of this class.
     * @return The single instance of Response Manager.
     */
    public static ResponseManager getInstance() {
        if (instance == null) {
            instance = new ResponseManager();
            taskManager = TaskManager.getInstance();
        }
        return instance;
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
                noTaskResponse();
                break;
            } else {
                listTasksResponse(tasksToBeListed);
            }
            break;

        case "mark":
            int taskIndexToMark = Integer.parseInt(splitInput[1]);
            if (isTaskIndexProblematic(taskIndexToMark)) {
                break;
            }
            Task markedTask = taskManager.markTask(taskIndexToMark);
            markTaskResponse(markedTask);
            break;

        case "unmark":
            int taskIndexToUnmark = Integer.parseInt(splitInput[1]);
            if (isTaskIndexProblematic(taskIndexToUnmark)) {
                break;
            }
            Task unmarkedTask = taskManager.unmarkTask(taskIndexToUnmark);
            unmarkTaskResponse(unmarkedTask);
            break;

        default:
            // Task types.
            try {
                // User input only has a single word. i.e. no description.
                if (splitInput.length == 1) {
                    throw new EmptyTaskDescriptionException(getResponses("EmptyTaskDescription"));
                }

                Task addedTask = taskManager.processTask(keyword, splitInput[1]);
                // No new task is created, which means task type is unknown.
                if (addedTask == null) {
                    throw new UnknownTaskTypeException(getResponses("UnknownTaskType"));
                } else {
                    addTaskResponse(addedTask);
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
                throw new TaskIndexOutOfBoundsException(getResponses("TaskIndexOutOfBounds"));
            }
        } catch (MeiException e) {
            e.echoErrorResponse();
            return true;
        }
        return false;
    }

    /**
     * A function that simply echos what is passed in.
     * This function ensures that every response echoed is wrapped in divider lines.
     * @param input The input string to echo.
     */
    public void echo(String input) {
        dividerLine();
        System.out.println(input);
        dividerLine();
    }

    /**
     * A function that echos everything in the given input array.
     * This function ensures that all the responses echoed are wrapped in the desired format.
     * @param inputs The input array to echo.
     */
    public static void echoLines(String[] inputs) {
        dividerLine();
        for (String input : inputs) {
            System.out.println(input);
        }
        dividerLine();
    }

    /**
     * Greets the user, pretty straight forward.
     */
    public void greetUser() {
        echoLines(getResponses("Greeting"));
    }

    /**
     * Bids farewell to the user and exits.
     * This should be the final response of the interaction.
     */
    public void exitChat() {
        echoLines(getResponses("Exit"));
    }

    /**
     * Prompts to the user after a task is successfully added.
     * Important to update the index variables where the information should be appended in the response array.
     * @param task The task successfully added to be echoed.
     */
    public void addTaskResponse(Task task) {
        String[] addTaskSuccessResponse = getResponses("AddTaskSuccess");
        int totalTasks = taskManager.getTotalTasks();

        // Index variables where information should be appended to.
        int totalTaskStringIndex = addTaskSuccessResponse.length - 1;
        int taskStringIndex = 1;

        // Cache the original string.
        String initialAddedTaskString = addTaskSuccessResponse[taskStringIndex];
        String initialTotalTaskString = addTaskSuccessResponse[totalTaskStringIndex];

        // Append the newly updated information to the responses.
        addTaskSuccessResponse[taskStringIndex] += task.toString();
        addTaskSuccessResponse[totalTaskStringIndex] += totalTasks;

        // Echo the responses.
        echoLines(addTaskSuccessResponse);

        // Reset the added task string back to default without the newly added task.
        addTaskSuccessResponse[taskStringIndex] = initialAddedTaskString;
        addTaskSuccessResponse[totalTaskStringIndex] = initialTotalTaskString;
    }

    /**
     * Prompts to the user if the task type given is unknown to Mei.
     */
    public void unknownTaskTypeResponse() {
        echoLines(getResponses("UnknownTaskType"));
    }

    /**
     * Prompts to the user their list of tasks.
     * @param tasksToBeDisplayed The list of valid tasks to be displayed to the user.
     */
    public void listTasksResponse(String[] tasksToBeDisplayed) {
        String[] listTasksResponses = getResponses("ListTasks");
        listTasksResponses = concatResponses(listTasksResponses, tasksToBeDisplayed);
        echoLines(listTasksResponses);
    }

    /**
     * Concatenates 2 arrays, first followed by the second.
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
     * Lets the user know if there is no task to be found.
     */
    public void noTaskResponse() {
        echoLines(getResponses("NoTask"));
    }

    /**
     * Prompts to the user that the given task has been marked as completed.
     * @param markedTask The task to be marked as completed.
     */
    public void markTaskResponse(Task markedTask) {
        String[] markTaskResponses = getResponses("MarkTask");
        int markTaskResponseLength = markTaskResponses.length + 1;
        markTaskResponses = Arrays.copyOf(markTaskResponses, markTaskResponseLength);
        markTaskResponses[markTaskResponseLength - 1] = markedTask.toString();

        echoLines(markTaskResponses);
    }

    /**
     * Prompts to the user that the given task has been marked as incomplete.
     * @param unmarkedTask The task to be marked as incomplete.
     */
    public void unmarkTaskResponse(Task unmarkedTask) {
        String[] unmarkTaskResponses = getResponses("UnmarkTask");
        int unmarkTaskResponseLength = unmarkTaskResponses.length + 1;
        unmarkTaskResponses = Arrays.copyOf(unmarkTaskResponses, unmarkTaskResponseLength);
        unmarkTaskResponses[unmarkTaskResponseLength - 1] = unmarkedTask.toString();

        echoLines(unmarkTaskResponses);
    }

    /**
     * A divider line that groups the responses and makes everything neater.
     * Currently, we shall use a hard-coded method of calling this before and after every single response function calls.
     */
    private static void dividerLine() {
        System.out.println("_________________________________");
    }

    /**
     * Get the array of responses from the response map.
     * @param key The hash key of the responses.
     * @return The array of responses corresponding to the given key.
     */
    public String[] getResponses(String key) {
        return responseMap.get(key);
    }

}

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
        responseMap.put("Greeting", new String[] {"Hello! My name is Mei!", "What can I do for you?"});
        responseMap.put("Exit", new String[] {"See you next time! :)"});

        responseMap.put("AddTask", new String[] {"Certainly! Your new task is on the way!"});
        responseMap.put("AddTaskSuccess", new String[] {"Task successfully added! Yay!", "Your added task is: "});

        responseMap.put("NoTask", new String[] {"I can't find any tasks for you :(", "Maybe start adding new tasks?"});
    }

    /**
     * Code adapted from geeksforgeeks.org/singleton-class-java
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
        switch (input) {
        case "list":
            taskManager.displayTasks();
            break;

        default:
            boolean isAddTaskSuccess = taskManager.addTask(input);
            if (isAddTaskSuccess) {
                addTaskResponse(input);
            }
            else {
                // Add Task Failed Exception.
            }
        }
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
    public void echoLines(String[] inputs) {
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
        echoLines(responseMap.get("Greeting"));
    }

    /**
     * Bids farewell to the user and exits.
     * This should be the final response of the interaction.
     */
    public void exitChat() {
        echoLines(responseMap.get("Exit"));
    }

    /**
     * Prompts to the user after a task is successfully added.
     * Assumption: The added task is always appended at the final string in the response array.
     * @param task The task successfully added to be echoed.
     */
    public void addTaskResponse(String task) {
        String[] addTaskSuccessResponse = responseMap.get("AddTaskSuccess");
        int lastElementIndex = addTaskSuccessResponse.length - 1;
        // Cache the original string.
        String initialAddedTaskString = addTaskSuccessResponse[lastElementIndex];
        addTaskSuccessResponse[lastElementIndex] += task;

        echoLines(addTaskSuccessResponse);

        // Reset the added task string back to default without the newly added task.
        addTaskSuccessResponse[lastElementIndex] = initialAddedTaskString;
    }

    /**
     * Lets the user know if there is no task to be found.
     */
    public void noTaskResponse() {
        echoLines(responseMap.get("NoTask"));
    }

    /**
     * A divider line that groups the responses and makes everything neater.
     * Currently, we shall use a hard-coded method of calling this before and after every single response function calls.
     */
    private void dividerLine() {
        System.out.println("_________________________________");
    }

}

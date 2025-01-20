import java.util.HashMap;

/**
 * This is the manager responsible for managing Mei's responses.
 * Any calls to make Mei speak or make a response should be done via here.
 */
public class ResponseManager {
    private final HashMap<String, String[]> responseMap = new HashMap<>();

    /**
     * The constructor.
     * Initializes a response map which maps the response type to its array of responses.
     * The response map is given the final modifier as no modifications should be made upon initialization.
     */
    public ResponseManager() {
        responseMap.put("Greeting", new String[] {"Hello! My name is Mei!", "What can I do for you?"});
        responseMap.put("Exit", new String[] {"See you next time! :)"});
    }

    /**
     * A function to redirect all incoming inputs to their respective functions.
     * This should serve as a middle-man function between the user and the manager functions
     * @param input The user input to redirect.
     */
    public void redirectInput(String input) {
        echo(input);
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
     * This function ensures that all the responses echoed are wrapped in divider lines together.
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
     * A divider line that groups the responses and makes everything neater.
     * Currently, we shall use a hard-coded method of calling this before and after every single response function calls.
     */
    private void dividerLine() {
        System.out.println("_________________________________");
    }


}

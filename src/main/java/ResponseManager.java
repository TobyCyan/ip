/**
 * This is the manager responsible for managing Mei's responses.
 */
public class ResponseManager {

    /**
     * Greets the user, pretty straight forward.
     */
    public void greetUser() {
        dividerLine();
        System.out.println("Hello! My name is Mei!");
        System.out.println("What can I do for you?");
        dividerLine();
    }

    /**
     * Bids farewell to the user and exits.
     * This should be the final response of the interaction.
     */
    public void exitChat() {
        dividerLine();
        System.out.println("See you next time! :)");
        dividerLine();
    }

    /**
     * A divider line that groups the responses and makes everything neater.
     * Currently, we shall use a hard-coded method of calling this before and after every single response function calls.
     */
    private void dividerLine() {
        System.out.println("_________________________________");
    }


}

package mei.response;

import java.util.Arrays;

import mei.javafx.MainWindow;

/**
 * Represents the responses base class.
 */
public abstract class Response {
    /**
     * Returns back the input array to the main window to be set as Mei's responses to the user.
     *
     * @param inputs The input array to return back.
     */
    public static void setInputsAsApplicationResponse(String[] inputs) {
        MainWindow.setMeiResponses(inputs);
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
     * Appends a task as its string representation defined in its class to a response array
     * corresponding to the given array key.
     * This method also returns the final appended array.
     *
     * @param responses The responses array where the task string is to be appended to.
     * @param taskString The task represented as a string.
     * @return The resulting appended array.
     */
    public String[] appendTaskStringToResponseArrayAndReturn(String[] responses, String taskString) {
        int unmarkTaskResponseLength = responses.length + 1;

        String[] resultResponses = Arrays.copyOf(responses, unmarkTaskResponseLength);
        resultResponses[unmarkTaskResponseLength - 1] = taskString;

        return resultResponses;
    }

    /**
     * Forms the appropriate responses depending on the response class.
     * After the formation is complete, the responses are set as the application response.
     */
    public abstract void formResponsesAndSet();

}

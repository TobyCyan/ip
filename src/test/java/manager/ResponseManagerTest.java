package manager;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import mei.task.Task;
import mei.response.MarkTaskResponse;
import mei.response.Response;
import mei.response.UnmarkTaskResponse;


/**
 * Represents the class to test response manager methods.
 */
public class ResponseManagerTest {
    @Test
    public void concatResponse_bothEmpty_success() {
        Response response = new Response() {
            @Override
            public void formResponsesAndSet() {
                return;
            }
        };
        String[] firstStr = new String[]{};
        String[] secondStr = new String[]{};
        String[] actual = response.concatResponses(firstStr, secondStr);
        String[] expected = new String[] {};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void concatResponse_oneEmpty_success() {
        Response response = new Response() {
            @Override
            public void formResponsesAndSet() {
                return;
            }
        };

        String[] firstStr = new String[]{"Hello World!"};
        String[] secondStr = new String[]{};
        String[] actual = response.concatResponses(firstStr, secondStr);
        String[] expected = new String[] {"Hello World!"};
        assertArrayEquals(expected, actual);

        String[] firstStr2 = new String[]{};
        String[] secondStr2 = new String[]{"Testing 2 cases in the same method lmaoo"};
        String[] actual2 = response.concatResponses(firstStr2, secondStr2);
        String[] expected2 = new String[] {"Testing 2 cases in the same method lmaoo"};
        assertArrayEquals(expected2, actual2);
    }

    @Test
    public void concatResponse_noEmpty_success() {
        Response response = new Response() {
            @Override
            public void formResponsesAndSet() {
                return;
            }
        };

        String[] firstStr = new String[]{"Hello guys!"};
        String[] secondStr = new String[]{"Welcome to my YouTube channel."};
        String[] actual = response.concatResponses(firstStr, secondStr);
        String[] expected = new String[] {"Hello guys!", "Welcome to my YouTube channel."};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void appendTaskStringToResponseArrayAndReturn_markTask_success() {
        Task task = new Task("task 1");
        MarkTaskResponse markTaskResponse = new MarkTaskResponse(task);
        task.completeTask();
        String taskString = task.toString();
        String[] markTaskResponses = new String[] {
            "You've completed this? That's amazing!", "I've noted down your achievement, "
                + "congratulations!"
        };

        String[] actual = markTaskResponse.appendTaskStringToResponseArrayAndReturn(markTaskResponses, taskString);
        String[] expected = new String[] {
            "You've completed this? That's amazing!",
            "I've noted down your achievement, congratulations!",
            taskString};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void appendTaskStringToResponseArrayAndReturn_unmarkTask_success() {
        Task task = new Task("task 1");
        UnmarkTaskResponse unmarkTaskResponse = new UnmarkTaskResponse(task);
        String taskString = task.toString();
        String[] markTaskResponses = new String[] {
            "It's alright to take things easy.",
            "I've unchecked this task for you to revisit next time!"
        };

        String[] actual = unmarkTaskResponse.appendTaskStringToResponseArrayAndReturn(markTaskResponses, taskString);
        String[] expected = new String[] {
            "It's alright to take things easy.",
            "I've unchecked this task for you to revisit next time!",
            taskString};
        assertArrayEquals(expected, actual);
    }


}

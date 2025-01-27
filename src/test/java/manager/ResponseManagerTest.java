package manager;

import org.junit.jupiter.api.Test;
import stub.FileStorageStub;
import tasks.Task;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseManagerTest {
    @Test
    public void concatResponse_bothEmpty_success() {
        ResponseManager rm = new ResponseManager(new TaskManager(new ArrayList<Task>(), new FileStorageStub("./")));
        String[] firstStr = new String[]{};
        String[] secondStr = new String[]{};
        String[] actual = rm.concatResponses(firstStr, secondStr);
        String[] expected = new String[] {};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void concatResponse_oneEmpty_success() {
        ResponseManager rm = new ResponseManager(new TaskManager(new ArrayList<Task>(), new FileStorageStub("./")));
        String[] firstStr = new String[]{"Hello World!"};
        String[] secondStr = new String[]{};
        String[] actual = rm.concatResponses(firstStr, secondStr);
        String[] expected = new String[] {"Hello World!"};
        assertArrayEquals(expected, actual);

        String[] firstStr2 = new String[]{};
        String[] secondStr2 = new String[]{"Testing 2 cases in the same method lmaoo"};
        String[] actual2 = rm.concatResponses(firstStr2, secondStr2);
        String[] expected2 = new String[] {"Testing 2 cases in the same method lmaoo"};
        assertArrayEquals(expected2, actual2);
    }

    @Test
    public void concatResponse_noEmpty_success() {
        ResponseManager rm = new ResponseManager(new TaskManager(new ArrayList<Task>(), new FileStorageStub("./")));
        String[] firstStr = new String[]{"Hello guys!"};
        String[] secondStr = new String[]{"Welcome to my YouTube channel."};
        String[] actual = rm.concatResponses(firstStr, secondStr);
        String[] expected = new String[] {"Hello guys!", "Welcome to my YouTube channel."};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void appendTaskStringToResponseArrayAndReturn_MarkTask_success() {
        ResponseManager rm = new ResponseManager(new TaskManager(new ArrayList<Task>(), new FileStorageStub("./")));
        String arrayKey = "MarkTask";
        Task task = new Task("task 1");
        task.completeTask();
        String taskString = task.toString();
        String[] actual = rm.appendTaskStringToResponseArrayAndReturn(arrayKey, taskString);
        String[] expected = new String[] {"You've completed this? That's amazing!", "I've noted down your achievement, congratulations!", taskString};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void appendTaskStringToResponseArrayAndReturn_UnmarkTask_success() {
        ResponseManager rm = new ResponseManager(new TaskManager(new ArrayList<Task>(), new FileStorageStub("./")));
        String arrayKey = "UnmarkTask";
        Task task = new Task("task 1");
        String taskString = task.toString();
        String[] actual = rm.appendTaskStringToResponseArrayAndReturn(arrayKey, taskString);
        String[] expected = new String[] {"It's alright to take things easy.", "I've unchecked this task for you to revisit next time!", taskString};
        assertArrayEquals(expected, actual);
    }


}

package manager;

import mei.manager.TaskManager;
import org.junit.jupiter.api.Test;
import mei.stub.FileStorageStub;
import mei.task.Task;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Represents the class to test task manager methods.
 */
public class TaskManagerTest {

    @Test
    public void getTaskStringsToDisplay_noTasks_success() {
        FileStorageStub fs = new FileStorageStub("./");
        List<Task> tasks = new ArrayList<>();
        TaskManager tm = new TaskManager(tasks, fs);
        String[] actual = tm.getTaskStringsToDisplay();
        assertArrayEquals(null, actual);
    }

    @Test
    public void getTaskStringsToDisplay_gotTasks_success() {
        FileStorageStub fs = new FileStorageStub("./");
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("task 1"));
        tasks.add(new Task("task 2"));
        tasks.add(new Task("task 3"));
        tasks.add(new Task("task 4"));
        tasks.add(new Task("task 5"));

        TaskManager tm = new TaskManager(tasks, fs);
        String[] actual = tm.getTaskStringsToDisplay();
        String[] expected = new String[] {"1. [ ] task 1", "2. [ ] task 2", "3. [ ] task 3", "4. [ ] task 4", "5. [ ] task 5"};
        assertArrayEquals(expected, actual);
    }


}

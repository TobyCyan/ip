package manager;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import mei.manager.TaskManager;
import mei.stub.FileStorageStub;
import mei.task.Task;


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
        tasks.add(new Task("task 1", "add task"));
        tasks.add(new Task("task 2", "add task"));
        tasks.add(new Task("task 3", "add task"));
        tasks.add(new Task("task 4", "add task"));
        tasks.add(new Task("task 5", "add task"));

        TaskManager tm = new TaskManager(tasks, fs);
        String[] actual = tm.getTaskStringsToDisplay();
        String[] expected = new String[] {
            "1. [ ] task 1",
            "2. [ ] task 2",
            "3. [ ] task 3",
            "4. [ ] task 4",
            "5. [ ] task 5"};
        assertArrayEquals(expected, actual);
    }


}

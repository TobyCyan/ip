package stub;

import fileaccess.FileStorage;
import manager.TaskManager;
import tasks.Task;

import java.util.List;

public class TaskManagerStub extends TaskManager {
    /**
     * The constructor.
     * Made private to prevent direct initialization.
     * Should call getInstance instead.
     * Attempts to read the list of tasks from the .txt file and store it into tasks.
     *
     * @param tasks
     * @param fileStorage
     */
    public TaskManagerStub(List<Task> tasks, FileStorage fileStorage) {
        super(tasks, fileStorage);
    }
}

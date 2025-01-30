package mei.stub;

import mei.fileaccess.FileStorage;
import mei.tasks.Task;

import java.util.ArrayList;

public class FileStorageStub extends FileStorage {

    public FileStorageStub(String fileStorePath) {
        super(fileStorePath);
    }

    public ArrayList<Task> readNTasks(int n) {
        ArrayList<Task> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(new Task("task " + (i + 1)));
        }
        return result;
    }
}

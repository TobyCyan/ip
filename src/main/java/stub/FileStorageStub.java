package stub;

import fileaccess.FileStorage;
import tasks.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.Callable;

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

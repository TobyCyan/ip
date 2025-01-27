package fileaccess;

import tasks.Task;

import java.io.IOException;
import java.util.ArrayList;

public class FileStorage {
    private final FileRead FILE_READER;
    private final FileWrite FILE_WRITER;

    public FileStorage(String fileStorePath) {
        this.FILE_READER = new FileRead(fileStorePath);
        this.FILE_WRITER = new FileWrite(fileStorePath);
    }

    public ArrayList<Task> readTasks() {
        try {
            return FILE_READER.readFromFile();
        } catch (IOException e) {
            System.out.println("Error reading from file in filestorage: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public void writeTask(Task task) {
        try {
            FILE_WRITER.writeTaskToFile(task);
        } catch (IOException e) {
            System.out.println("Error writing task to file in filestorage: " + e.getMessage());
        }
    }

    public void overwriteTask(int lineNumber, String taskData) {
        try {
            FILE_WRITER.overwriteTaskData(lineNumber, taskData);
        } catch (IOException e) {
            System.out.println("Error overwriting task to file in filestorage: " + e.getMessage());
        }
    }

    public void removeTask(int lineNumber) {
        try {
            FILE_WRITER.removeTaskData(lineNumber);
        } catch (IOException e) {
            System.out.println("Error removing task to file in filestorage: " + e.getMessage());
        }
    }
}

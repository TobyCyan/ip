package fileaccess;

import tasks.Task;

import java.io.IOException;
import java.util.ArrayList;

public class FileStorage {
    private final FileRead fileReader;
    private final FileWrite fileWriter;

    public FileStorage(String fileStorePath) {
        this.fileReader = new FileRead(fileStorePath);
        this.fileWriter = new FileWrite(fileStorePath);
    }

    public ArrayList<Task> readTasks() {
        try {
            return fileReader.readFromFile();
        } catch (IOException e) {
            System.out.println("Error reading from file in filestorage: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public void writeTask(Task task) {
        try {
            fileWriter.writeTaskToFile(task);
        } catch (IOException e) {
            System.out.println("Error writing task to file in filestorage: " + e.getMessage());
        }
    }

    public void overwriteTask(int lineNumber, String taskData) {
        try {
            fileWriter.overwriteTaskData(lineNumber, taskData);
        } catch (IOException e) {
            System.out.println("Error overwriting task to file in filestorage: " + e.getMessage());
        }
    }

    public void removeTask(int lineNumber) {
        try {
            fileWriter.removeTaskData(lineNumber);
        } catch (IOException e) {
            System.out.println("Error removing task to file in filestorage: " + e.getMessage());
        }
    }
}

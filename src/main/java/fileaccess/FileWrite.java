package fileaccess;

import tasks.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Represents a class that acts as a utility to write to the designated file path.
 * This class holds methods that can write, overwrite, and remove task data that supposedly holds all the saved task data.
 * This class should not be interacted with directly but rather all methods here can be called from the FileStorage class.
 */
public class FileWrite {
    private final String fileWritePath;

    public FileWrite(String fileWritePath) {
        this.fileWritePath = fileWritePath;
    }

    /**
     * Writes a new task to the defined file path during initialization.
     * This method checks whether the file path exists, then appends the new task data as a string to it.
     * If it doesn't exist yet, attempts to create the directory and file, then appends the new task data to it.
     *
     * @param task The new task to write to the file path.
     * @throws IOException if an error occurred during the creation of the file path.
     */
    public void writeTaskToFile(Task task) throws IOException {
        File file = new File(fileWritePath);
        boolean isFilePathExist = isFilePathExist(file);
        String taskDataAsString = task.getTaskDataString();

        if (isFilePathExist) {
            appendToFile(taskDataAsString);
            return;
        }

        try {
            boolean isCreateFilePathSuccess = FileStorage.createFilePath(file);
            if (isCreateFilePathSuccess) {
                appendToFile(taskDataAsString);
            }
        } catch (Exception e) {
            System.out.println("Error creating file path: " + e.getMessage());
        }
    }

    private void appendToFile(String textToAppend) throws IOException {
        FileWriter fw = new FileWriter(fileWritePath, true);
        fw.write(textToAppend + "\n");
        fw.close();
    }

    /**
     * @@author TobyCyan-reused.
     * Reused from stackoverflow.com/questions/31375972/how-to-replace-a-specific-line-in-a-file-using-java
     * Overwrites a line of task data from the tasks.txt file at the given line number.
     * @param lineNumber The line number within the .txt file to be overwritten.
     * @param taskData The new task data string to replace the old data.
     */
    public void overwriteTaskData(int lineNumber, String taskData) throws IOException {
        Path path = Paths.get(fileWritePath);
        List<String> taskDatas = Files.readAllLines(path, StandardCharsets.UTF_8);
        taskDatas.set(lineNumber - 1, taskData);
        Files.write(path, taskDatas, StandardCharsets.UTF_8);
    }

    /**
     * Removes the task data that is currently written at the given line number within the task data file.
     * This method first retrieves the current task data, stores them as a list,
     * then removes the task at the appropriate index.
     * Finally, the final updated list gets written back.
     * <p>
     * This method always returns as a success due to a check for invalid task index before the call.
     *
     * @param lineNumber The task index number, or the line number in the task data file to remove.
     * @throws IOException if an error occurred during the writing of the final updated task list.
     */
    public void removeTaskData(int lineNumber) throws IOException {
        Path path = Paths.get(fileWritePath);
        List<String> taskDatas = Files.readAllLines(path, StandardCharsets.UTF_8);
        taskDatas.remove(lineNumber - 1);
        Files.write(path, taskDatas, StandardCharsets.UTF_8);
    }

}

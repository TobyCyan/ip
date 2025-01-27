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

public class FileWrite {
    private final String FILE_WRITE_PATH;

    public FileWrite(String fileWritePath) {
        this.FILE_WRITE_PATH = fileWritePath;
    }

    public void writeTaskToFile(Task task) throws IOException {
        File file = new File(FILE_WRITE_PATH);
        boolean isFilePathExist = isFilePathExist(file);
        String taskDataAsString = task.getTaskDataString();

        if (isFilePathExist) {
            appendToFile(taskDataAsString);
            return;
        }

        try {
            boolean isCreateFilePathSuccess = createFilePath(file);
            if (isCreateFilePathSuccess) {
                appendToFile(taskDataAsString);
            }
        } catch (Exception e) {
            System.out.println("Error creating file path: " + e.getMessage());
        }
    }

    public static boolean createFilePath(File file) throws IOException {
        boolean isCreateParentDirSuccess = createFileParentDirectory(file);
        if (!isCreateParentDirSuccess) {
            throw new IOException("Parent directory creation failed.");
        }
        boolean isCreateFileSuccess = createFileUnderParent(file);
        if (!isCreateFileSuccess) {
            throw new IOException("File path creation failed.");
        }
        return true;
    }

    public static boolean isFilePathExist(File file) {
        return file.exists();
    }

    /**
     * Creates the parent directory of the given file.
     * This function must be called before createFileUnderParent because it depends on this.
     * @param file The file in which the parent directory is to be created.
     * @return true or false whether the parent directory has been successfully created.
     */
    public static boolean createFileParentDirectory(File file) {
        File parentDir = file.getParentFile();
        if (parentDir != null && parentDir.exists()) {
            return true;
        }
        assert parentDir != null;
        return parentDir.mkdir();
    }

    /**
     * Creates a file specified at the end of the given path under a parent folder.
     * At this point, we must be sure that the parent folder should exist.
     * @param file The file to be created.
     * @return true or false whether the file has been successfully created.
     * @throws IOException when error creating the new file.
     */
    public static boolean createFileUnderParent(File file) throws IOException {
        if (file.exists()) {
            return true;
        }
        return file.createNewFile();
    }

    private void appendToFile(String textToAppend) throws IOException {
        FileWriter fw = new FileWriter(FILE_WRITE_PATH, true);
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
        Path path = Paths.get(FILE_WRITE_PATH);
        List<String> taskDatas = Files.readAllLines(path, StandardCharsets.UTF_8);
        taskDatas.set(lineNumber - 1, taskData);
        Files.write(path, taskDatas, StandardCharsets.UTF_8);
    }

    public void removeTaskData(int lineNumber) throws IOException {
        Path path = Paths.get(FILE_WRITE_PATH);
        List<String> taskDatas = Files.readAllLines(path, StandardCharsets.UTF_8);
        taskDatas.remove(lineNumber - 1);
        Files.write(path, taskDatas, StandardCharsets.UTF_8);
    }

}

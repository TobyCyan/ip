package mei.fileaccess;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import mei.task.Task;

/**
 * Represents the storage class that is responsible for interacting directly with the file-related calls.
 * This class holds a file reader and writer that does exactly what the class names suggest.
 * Methods from these utilities should be called from this class.
 */
public class FileStorage {
    private final FileRead fileReader;
    private final FileWrite fileWriter;

    /**
     * Initializes the file reader and writer with the given file store path.
     *
     * @param fileStorePath The path string to store the task data file.
     */
    public FileStorage(String fileStorePath) {
        this.fileReader = new FileRead(fileStorePath);
        this.fileWriter = new FileWrite(fileStorePath);
    }

    /**
     * Reads from the initialized file path
     * using the file reader.
     * This method should be called when initializing the Task Manager for the first time
     * to load the current list of tasks.
     *
     * @return The list of tasks read from the file path.
     */
    public ArrayList<Task> readTasks() {
        try {
            return fileReader.readFromFile();
        } catch (IOException e) {
            System.out.println("Error reading from file in filestorage: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Writes a new task to the initialized file path
     * using the file writer.
     * This method should be called whenever the user wants to add a new task to the current list of tasks.
     *
     * @param task The new task to be added to the current list of tasks.
     */
    public void writeTask(Task task) {
        try {
            fileWriter.writeTaskToFile(task);
        } catch (IOException e) {
            System.out.println("Error writing task to file in filestorage: " + e.getMessage());
        }
    }

    /**
     * Overwrites an existing task at the initialized file path
     * using the file writer.
     * This method should be called whenever the user wants to update information
     * of one of the existing task in the list of tasks.
     *
     * @param lineNumber The task index, or the line number that the overwritten task is currently located at.
     * @param taskData The new task data to write.
     */
    public void overwriteTask(int lineNumber, String taskData) {
        try {
            fileWriter.overwriteTaskData(lineNumber, taskData);
        } catch (IOException e) {
            System.out.println("Error overwriting task to file in filestorage: " + e.getMessage());
        }
    }

    /**
     * Removes an existing task at the initialized file path
     * using the file writer.
     * This method should be called whenever the user wants to delete
     * one of the existing task in the list of tasks.
     *
     * @param lineNumber The task index, or the line number that the task to be deleted is currently located at.
     */
    public void removeTask(int lineNumber) {
        try {
            fileWriter.removeTaskData(lineNumber);
        } catch (IOException e) {
            System.out.println("Error removing task to file in filestorage: " + e.getMessage());
        }
    }

    /**
     * Creates the given file at the initialized file path.
     * This method first creates the file's parent directory if it doesn't exist yet,
     * then creates the file itself that is used to store all the task data.
     *
     * @param file The file object to be created at the given path.
     * @return true or false depending on whether the file has been successfully created or not.
     * @throws IOException if an error occurred during the creation of either the parent directory or the file.
     */
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

    /**
     * Checks whether the file path exists.
     *
     * @param file The file to check for its existence.
     * @return true or false whether the given file exists or not.
     */
    public static boolean isFilePathExist(File file) {
        return file.exists();
    }

    /**
     * Creates the parent directory of the given file.
     * This method must be called before createFileUnderParent because it depends on this.
     *
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
     *
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
}

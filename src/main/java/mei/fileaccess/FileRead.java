package mei.fileaccess;

import mei.tasks.Deadline;
import mei.tasks.Event;
import mei.tasks.Task;
import mei.tasks.ToDo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a class that acts as a utility to read from the designated file path.
 * This class holds methods that can read from a file path that supposedly holds all the saved task data
 * and can process the task data within it.
 * This class should not be interacted with directly but rather all methods here can be called from the FileStorage class.
 */
public class FileRead {
    private static final String SPLIT_TASK_FILE_DATA_REGEX = "\\|";
    private final String fileReadPath;

    public FileRead(String fileReadPath) {
        this.fileReadPath = fileReadPath;
    }

    /**
     * Reads from the defined file path during initialization.
     * This method first checks whether the file path exists, and ensures that the directory and files are created first.
     * Then, the task data are retrieved iteratively, line by line from the file, and returned as an array list.
     *
     * @return The array list that holds all the retrieved task data.
     * @throws IOException if an error occurred while reading from the task data file.
     */
    public ArrayList<Task> readFromFile() throws IOException {
        ArrayList<Task> resultTasks = new ArrayList<>();
        File file = new File(fileReadPath);

        boolean isFilePathExist = FileStorage.isFilePathExist(file);
        if (!isFilePathExist) {
            FileStorage.createFilePath(file);
        }

        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            Task taskData = processFileTaskData(scanner.nextLine());
            resultTasks.add(taskData);
        }

        return resultTasks;
    }

    private Task processFileTaskData(String fileData) {
        String[] splitFileData = fileData.split(SPLIT_TASK_FILE_DATA_REGEX, 5);
        Task newTask = null;
        String taskType = splitFileData[0];
        boolean isTaskDone = splitFileData[1].equals("[X]");
        String description = splitFileData[2];

        switch (taskType) {
        case "ToDo":
            newTask = new ToDo(description);
            break;
        case "Deadline":
            String deadlineDateTime = splitFileData[3];
            newTask = new Deadline(description, deadlineDateTime);
            break;
        case "Event":
            String startDateTime = splitFileData[3];
            String endDateTime = splitFileData[4];
            newTask = new Event(description, startDateTime, endDateTime);
            break;
        }

        if (newTask != null && isTaskDone) {
            newTask.completeTask();
        }
        return newTask;
    }
}

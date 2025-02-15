package mei.fileaccess;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import mei.exception.DateTimeConversionException;
import mei.exception.ProcessTaskDateTimeConversionException;
import mei.task.Deadline;
import mei.task.Event;
import mei.task.Task;
import mei.task.ToDo;

/**
 * Represents a class that acts as a utility to read from the designated file path.
 * This class holds methods that can read from a file path that supposedly holds all the saved task data
 * and can process the task data within it.
 * This class should not be interacted with directly but rather all methods here can be called
 * from the FileStorage class.
 */
public class FileRead {
    private final String fileReadPath;

    public FileRead(String fileReadPath) {
        this.fileReadPath = fileReadPath;
    }

    /**
     * Reads from the defined file path during initialization.
     * This method first checks whether the file path exists,
     * and ensures that the directory and files are created first.
     * Then, the task data are retrieved iteratively, line by line from the file, and returned as an array list.
     *
     * @return The array list that holds all the retrieved task data.
     * @throws IOException if an error occurred while reading from the task data file.
     */
    public ArrayList<Task> readFromFile() throws IOException {
        File file = new File(fileReadPath);

        boolean isFilePathExist = FileStorage.isFilePathExist(file);
        if (!isFilePathExist) {
            FileStorage.createFilePath(file);
        }

        Scanner scanner = new Scanner(file);

        return scanTasksThenReturn(scanner);
    }

    private Task processFileTaskData(String fileData) throws DateTimeConversionException {
        String splitTaskFileDataRegex = "\\|";
        String[] splitFileData = fileData.split(splitTaskFileDataRegex, 6);
        Task newTask = null;

        // Extract the necessary task fields.
        String taskType = splitFileData[0];
        boolean isTaskDone = splitFileData[1].equals("[X]");
        String description = splitFileData[2];

        switch (taskType) {
        case "ToDo":
            String addTodoTaskCommand = splitFileData[3];
            newTask = new ToDo(description, addTodoTaskCommand);
            break;

        case "Deadline":
            String deadlineDateTime = splitFileData[3];
            String addDeadlineTaskCommand = splitFileData[4];
            newTask = new Deadline(description, deadlineDateTime, addDeadlineTaskCommand);
            break;

        case "Event":
            String startDateTime = splitFileData[3];
            String endDateTime = splitFileData[4];
            String addEventTaskCommand = splitFileData[5];
            newTask = new Event(description, startDateTime, endDateTime, addEventTaskCommand);
            break;

        default:
            // Do nothing.
            break;
        }

        assert newTask != null : "Shouldn't encounter an empty line when scanning for tasks.";

        if (isTaskDone) {
            newTask.completeTask();
        }

        return newTask;
    }

    private ArrayList<Task> scanTasksThenReturn(Scanner scanner) {
        ArrayList<Task> resultTasks = new ArrayList<>();

        while (scanner.hasNext()) {
            String newLine = scanner.nextLine();
            processThenAddTo(newLine, resultTasks);
        }

        return resultTasks;
    }

    private void processThenAddTo(String newLine, ArrayList<Task> resultTasks) {
        try {
            Task taskData = processFileTaskData(newLine);
            resultTasks.add(taskData);

        } catch (DateTimeConversionException e) {
            // A date/time conversion exception is thrown,
            // but we want the chatbot to prompt a process task version of the exception instead.
            ProcessTaskDateTimeConversionException trueException = new ProcessTaskDateTimeConversionException();
            trueException.echoErrorResponse();
        }
    }
}

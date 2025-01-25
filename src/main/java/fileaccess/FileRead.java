package fileaccess;

import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

public class FileRead {
    private static final String SPLIT_TASK_FILE_DATA_REGEX = "\\|";
    private static final String READ_FROM_FILE_PATH = "./taskdata/tasks.txt";

    public static ArrayList<Task> readFromFile() throws IOException {
        ArrayList<Task> resultTasks = new ArrayList<>();
        File file = new File(READ_FROM_FILE_PATH);
        boolean isFilePathExist = FileWrite.isFilePathExist(file);
        if (!isFilePathExist) {
            FileWrite.createFilePath(file);
        }
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            Task taskData = processFileTaskData(scanner.nextLine());
            resultTasks.add(taskData);
        }

        return resultTasks;
    }

    private static Task processFileTaskData(String fileData) {
        String[] splitFileData = fileData.split(SPLIT_TASK_FILE_DATA_REGEX, 4);
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

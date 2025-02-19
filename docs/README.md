# Mei User Guide

![Screenshot of the user interface.](Ui.png)

## Welcome User😄! 
And behold, your personal Task Managing Assistant, **Mei**!
This application is optimized for usage via a **Command Line Interface** (CLI) while still having a Graphical User Interface (GUI).

With **Mei**, you can:
- [x]  Add
    - [ToDo](#adding-todos)
    - [Deadlines & Events](#adding-deadlines--events)
- [x]  [List](#listing-all-tasks)
- [x]  [Mark/ Unmark as complete](#marking-unmarking-a-task)
- [x]  [Find](#find-feature)
- [x]  [Undo](#undo-feature)
- [x]  [Save](#save-feature)

***ALL your tasks***!! 🥳

## Getting Started

Follow these steps to start chatting with Mei:
1. Download this [JAR file.](https://github.com/TobyCyan/ip/releases/download/A-Release/mei.jar)
2. Move/ copy it to an empty folder.
3. Open up your command prompt.
4. Navigate to the folder where the JAR file is at using the `cd` command.
5. Type `java -jar mei.jar` to launch the application.
6. **All Done!** You may proceed to chat with Mei 🥳


## Adding ToDos

You may add ToDo tasks by typing the following command:
```
todo {task name}
```

For instance:
```
todo return books
```

You may expect an output like this:

![Expected output for adding a ToDo task](AddToDoOutput.png)


## Adding Deadlines & Events

You may add Deadline and Event tasks by typing the following command:
```
deadline {task name} /by {date/time}
event {task name} /from {date/time} /to {date/time}
```

> **IMPORTANT**
> 
> The format of the date/time has to be in day/month/year HoursMinutes in the 24-hour time format.
> However, you may swap out the `/` for `-`, but it also has to be either all `/` or `-`!
> The day and year may be swapped too.

For instance:
```
deadline go to library /by 25/02/2025 1600
event watch a movie /from 2025-02-25 1100 /to 2025/02/25 1300
```

You may expect an output like this:

![Expected output for adding a Deadline task](AddDeadlineOutput.png)

![Expected output for adding an Event task](AddEventOutput.png)


## Listing All Tasks

After adding a few tasks, you may want to see what you have added so far.
Simply type `list`, and you can see:

![Expected output for listing tasks.](ListOutput.png)


## Marking/ Unmarking a Task

> What's a task management assistant if she can't even `mark`/ `unmark` your tasks for you? - Me

To mark/ unmark the completion of your tasks, simply type:
```
mark {task number}
unmark {task number}
```

> **TIP**
> 
> Use the `list` command to see the number of your tasks on the list!

For instance:
```
mark 1
unmark 1
```

You may expect an output like this:

![Expected output for marking a task](MarkOutput.png)

![Expected output for unmarking a task](UnmarkOutput.png)


## Find Feature

Finding specific tasks from your long list is ~~tough sometimes~~ **MADE EASY**!
You may find any tasks by typing the following command:
```
find {keyword}
```
> **NOTE**
> 
> The keyword to find a task must be part of the task description!

For instance:
```
find library
```

You may expect an output like this:

![Expected output for finding a task](FindOutput.png)


## Undo Feature

**Undo** previous commands within the ***same*** user session by typing `undo`!


## Save Feature

> **NOTE**
> 
> You may be wondering, where do the tasks get saved to in your devices?
> Well *fear not*! Here's the `main` method 😉
> You may find all of your saved task data from the given relative path!
> 
> Note that this path is relative to the folder that the .jar file is kept in.
```java
public static void main(String[] args) {
    new Mei("./taskdata/tasks.txt").run();
}
```

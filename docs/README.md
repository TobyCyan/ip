# Mei User Guide

![Screenshot of the user interface.](./Ui.png)

## Welcome User😄! 
And behold, your personal Task Managing Assistant, **Mei**!

With **Mei**, you can:
+ Add
+ List
+ Mark/ Unmark as complete
+ Find
+ Undo
+ Save

***ALL your tasks***!! 🥳

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

![Expected output for adding a ToDo task](../resources/images/AddToDoOutput.png)

## Adding Deadlines & Events

You may add Deadline and Event tasks by typing the following command:
```
deadline {task name} /by {date/time}
event {task name} /from {date/time} /to {date/time}
```

> [!IMPORTANT]
> The format of the date/time has to be in day/month/year HoursMinutes in the 24-hour time format.
> However, you may swap out the `/` for `-`.
> The day and year may be swapped too.

For instance:
```
deadline go to library /by 25/02/2025 1600
event watch a movie /from 2025-02-25 1100 /to 2025/02/25 1300
```

You may expect an output like this:

![Expected output for adding a Deadline task](../resources/images/AddDeadlineOutput.png)

![Expected output for adding an Event task](../resources/images/AddEventOutput.png)

## Listing All Tasks

After adding a few tasks, you may want to see what you have added so far.
Simply type `list`, and you can see:

![Expected output for listing tasks.](../resources/images/ListOutput.png)

## Marking/ Unmarking a Task

> What's a task management assistant if she can't even mark/ unmark your tasks for you? - Me

To mark/ unmark the completion of your tasks, simply type:
```
mark {task number}
unmark {task number}
```

> [!TIP]
> Use the list command to see the number of your tasks on the list!

For instance:
```
mark 1
unmark 1
```

You may expect an output like this:

![Expected output for marking a task](../resources/images/MarkOutput.png)

![Expected output for unmarking a task](../resources/images/UnmarkOutput.png)


## Find Feature

Finding specific tasks from your long list is ~~tough sometimes~~ **MADE EASY**!
You may find any tasks by typing the following command:
```
find {keyword}
```
> [!NOTE]
> The keyword to find a task must be part of the task description!

For instance:
```
find library
```

You may expect an output like this:

![Expected output for finding a task](../resources/images/FindOutput.png)

## Undo Feature

**Undo** previous commands within the ***same*** user session by typing `undo`!

## Save Feature

> [!NOTE]
> You may be wondering, where do the tasks get saved to in your devices?
> Well *fear not*! Here's the `main` method 😉
> You may find all of your saved task data from the given relative path!
```java
public static void main(String[] args) {
    new Mei("./taskdata/tasks.txt").run();
}
```

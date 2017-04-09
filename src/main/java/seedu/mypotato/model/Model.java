package seedu.mypotato.model;

import java.util.Set;
import java.util.Stack;

import seedu.mypotato.commons.core.Config;
import seedu.mypotato.commons.core.UnmodifiableObservableList;
import seedu.mypotato.commons.events.ui.LoadFirstTaskEvent;
import seedu.mypotato.model.tag.UniqueTagList.DuplicateTagException;
import seedu.mypotato.model.task.ReadOnlyTask;
import seedu.mypotato.model.task.Task;
import seedu.mypotato.model.task.UniqueTaskList;
import seedu.mypotato.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.mypotato.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {

    //@@author A0125221Y
    Stack<String> getUndoStack();
    Stack<ReadOnlyTask> getAddedStackOfTasks();
    Stack<ReadOnlyTask> getDeletedStackOfTasks();
    Stack<Integer> getDeletedStackOfTasksIndex();
    Stack<ReadOnlyTask> getOldTask();
    Stack<ReadOnlyTask> getCurrentTask();
    //@@author

    /** Clears existing backing model and replaces with the provided new data.
     * @throws DuplicateTaskException
     * @throws DuplicateTagException */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**Returns the config*/
    Config getConfig();

    /** Raises an event to indicate the model has changed */
    void updateFileLocation();

    /** Deletes the given task. */
    int deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    // @@author A0125221Y
    // Add task to the specific Index in the list
    void addTaskIdx(Task task, int idx) throws DuplicateTaskException;

    void revertData();

    /**
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException;

    void updateTask(ReadOnlyTask old, Task toUpdate) throws TaskNotFoundException, DuplicateTaskException;

    /**Updates current showing list*/
    void setCurrentList(String currentList);

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list corresponding to current tab */
    public void updateFilteredListBasedOnTab();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to show today tasks */
    void updateFilteredListToShowToday();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(boolean isInContent, Set<String> keywords);

    /** Updates the filter of the filtered task list to show completed tasks */
    void updateFilteredListToShowCompleted();

    UnmodifiableObservableList<ReadOnlyTask> getDoneTaskList();

    /** Loads the first task from panelList */
    void handleLoadFirstTaskEvent(LoadFirstTaskEvent event);
}

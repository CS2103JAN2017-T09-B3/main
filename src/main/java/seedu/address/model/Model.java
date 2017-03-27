package seedu.address.model;

import java.util.Set;
import java.util.Stack;

import seedu.address.commons.core.Config;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {

    Stack<String> getUndoStack();
    Stack<ReadOnlyTask> getDeletedStackOfTasksAdd();
    Stack<ReadOnlyTask> getDeletedStackOfTasks();
    Stack<Integer> getDeletedStackOfTasksIndex();

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

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered person list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to show today tasks */
    void updateFilteredListToShowToday();

    /** Updates the filter of the filtered person list to filter by the given keywords*/
    void updateFilteredTaskList(boolean isInContent, Set<String> keywords);

    UnmodifiableObservableList<ReadOnlyTask> getDoneTaskList();
}

package seedu.address.model;

import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.storage.ChangedFileLocationRequestEvent;
import seedu.address.commons.events.ui.UpdateStatusBarFooterEvent;
import seedu.address.commons.events.ui.UpdateUiTaskDescriptionEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.DateComparator;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final Stack<String> stackOfUndo;
    private final Stack<ReadOnlyTask> stackOfDeletedTasksAdd;
    private final Stack<ReadOnlyTask> stackOfDeletedTasks;
    private final Stack<Integer> stackOfDeletedTaskIndex;
    private final Stack<ReadOnlyTask> stackOfOldTask;
    private final Stack<ReadOnlyTask> stackOfCurrentTask;
    private final Stack<ReadOnlyTask> stackOfOldNextTask;
    private final Stack<ReadOnlyTask> stackOfNewNextTask;
    private final Stack<ReadOnlyAddressBook> stackOfAddressBook;

    private Config config;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     *
     * @throws DuplicateTaskException
     * @throws DuplicateTagException
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs, Config config)
            throws DuplicateTagException, DuplicateTaskException {
        super();
        assert !CollectionUtil.isAnyNull(addressBook, userPrefs);

        stackOfUndo = new Stack<>();
        stackOfDeletedTasksAdd = new Stack<>();
        stackOfDeletedTasks = new Stack<>();
        stackOfDeletedTaskIndex = new Stack<>();
        stackOfAddressBook = new Stack<>();
        stackOfOldTask = new Stack<>();
        stackOfCurrentTask = new Stack<>();
        stackOfOldNextTask = new Stack<>();
        stackOfNewNextTask = new Stack<>();
        this.config = config;

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.taskManager = new AddressBook(addressBook);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
    }

    public ModelManager() throws DuplicateTagException, DuplicateTaskException {
        this(new AddressBook(), new UserPrefs(), new Config());
    }

    //@@author A0125221Y
    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        stackOfAddressBook.push(new AddressBook(taskManager));
        taskManager.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void revertData() {
        resetData(this.stackOfAddressBook.pop());
        // AddressBook.revertEmptyAddressBook(stackOfAddressBook.pop());
        indicateAddressBookChanged();
    }
    //@@author

    @Override
    public Config getConfig() {
        return config;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(taskManager));
    }

    //@@author A0135807A
    /** Raises events to update the file location in storage and status bar in UI */
    @Override
    public void updateFileLocation() {
        raise(new ChangedFileLocationRequestEvent(config));
        raise(new UpdateStatusBarFooterEvent());
        indicateAddressBookChanged();
    }

    /** Raises event to update the Ui TaskDescription when task is edited using command line */
    public void updateUiTaskDescription(ReadOnlyTask editedTask) {
        raise(new UpdateUiTaskDescriptionEvent(editedTask));
    }
    //@@author

    //@@author A0125221Y
    @Override
    public synchronized int deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        int indexRemoved = taskManager.removeTask(target);
        indicateAddressBookChanged();
        return indexRemoved;
    }
    //@@author

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int taskIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskIndex, editedTask);
        updateUiTaskDescription(editedTask);
        indicateAddressBookChanged();
    }

    //@@author A0125221Y
    @Override
    public synchronized void updateTask(ReadOnlyTask old, Task toUpdate)
            throws TaskNotFoundException, DuplicateTaskException {
        taskManager.updateTask(old, toUpdate);
        updateUiTaskDescription(toUpdate);
        indicateAddressBookChanged();
    }

    //@@author A0125221Y
    @Override
    public Stack<String> getUndoStack() {
        return stackOfUndo;
    }

    @Override
    public Stack<ReadOnlyTask> getDeletedStackOfTasksAdd() {
        return stackOfDeletedTasksAdd;
    }

    @Override
    public Stack<ReadOnlyTask> getDeletedStackOfTasks() {
        return stackOfDeletedTasks;
    }

    @Override
    public Stack<Integer> getDeletedStackOfTasksIndex() {
        return stackOfDeletedTaskIndex;
    }

    @Override
    public Stack<ReadOnlyTask> getOldTask() {
        return stackOfOldTask;
    }

    @Override
    public Stack<ReadOnlyTask> getCurrentTask() {
        return stackOfCurrentTask;
    }

    @Override
    public Stack<ReadOnlyTask> getOldNextTask() {
        return stackOfOldNextTask;
    }

    @Override
    public Stack<ReadOnlyTask> getNewNextTask() {
        return stackOfNewNextTask;
    }
    //@@author

    // =========== Filtered Person List Accessors
    // =============================================================
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        // here to change the list order according to the date comparator.
        //return new UnmodifiableObservableList<>(filteredTasks);
        return new UnmodifiableObservableList<>(filteredTasks.sorted(new
         DateComparator()));
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(boolean isInContent, Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(isInContent, keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    // ========== Inner classes/interfaces used for filtering
    // =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);

        @Override
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);

        @Override
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;
        private boolean isInContent;

        NameQualifier(boolean isInContent, Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
            this.isInContent = isInContent;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(isInContent
                            ? keyword -> (StringUtil.containsWordIgnoreCase(task.getTitle().fullTitle, keyword)
                                    || StringUtil.containsWordIgnoreCase(task.getContent().fullContent, keyword))
                                    : keyword -> StringUtil.containsWordIgnoreCase(task.getTitle().fullTitle, keyword))
                    .findAny().isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}

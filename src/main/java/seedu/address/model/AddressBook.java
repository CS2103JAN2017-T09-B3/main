package seedu.address.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by
 * .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block,
     * sometimes used to avoid duplication between constructors. See
     * https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are
     * other ways to avoid duplication among constructors.
     */
    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Persons and Tags in the
     * {@code toBeCopied}
     * @throws DuplicateTaskException
     * @throws DuplicateTagException
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {

//this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());

        this();
        resetData(toBeCopied);
    }

    //@@author A0125221Y
    public AddressBook(UniqueTaskList tasks, UniqueTagList tags) {
        resetData(tasks.getInternalList(), tags.getInternalList());
    }

    //// list overwrite operations

    public void setTasks(List<? extends ReadOnlyTask> tasks) throws UniqueTaskList.DuplicateTaskException {
        this.tasks.setTasks(tasks);
    }

    public void setTags(Collection<Tag> tags) throws UniqueTagList.DuplicateTagException {
        this.tags.setTags(tags);
    }

    public void resetData(ReadOnlyAddressBook newData) {
        assert newData != null;
        try {
            setTasks(newData.getTaskList());
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "AddressBooks should not have duplicate tasks";
        }
        try {
            setTags(newData.getTagList());
        } catch (UniqueTagList.DuplicateTagException e) {
            assert false : "AddressBooks should not have duplicate tags";
        }
        syncMasterTagListWith(tasks);
    }

    //@@author A0125221Y
    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Tag> newTags) {
        try {
            setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "AddressBooks should not have duplicate tasks";
        }
        try {
            setTags(newTags);
        } catch (UniqueTagList.DuplicateTagException e) {
            assert false : "AddressBooks should not have duplicate tags";
        }
        syncMasterTagListWith(tasks);
    }

    public synchronized  void revertEmptyAddressBook(ReadOnlyAddressBook backUp) throws
                                                        DuplicateTagException, DuplicateTaskException {
        resetData(backUp.getTaskList(), backUp.getTagList());
    }

    //@@author
    //// person-level operations

    /**
     * Adds a person to the address book. Also checks the new person's tags and
     * updates {@link #tags} with any new tags found, and updates the Tag
     * objects in the person to point to those in {@link #tags}.
     *
     * @throws UniquePersonList.DuplicatePersonException
     *             if an equivalent person already exists.
     */
    public void addTask(Task t) throws UniqueTaskList.DuplicateTaskException {
        syncMasterTagListWith(t);
        tasks.add(t);
    }

    /**
     * Updates the person in the list at position {@code index} with
     * {@code editedReadOnlyPerson}. {@code AddressBook}'s tag list will be
     * updated with the tags of {@code editedReadOnlyPerson}.
     *
     * @see #syncMasterTagListWith(Person)
     *
     * @throws DuplicatePersonException
     *             if updating the person's details causes the person to be
     *             equivalent to another existing person in the list.
     * @throws IndexOutOfBoundsException
     *             if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, ReadOnlyTask editedReadOnlyTask) throws UniqueTaskList.DuplicateTaskException {
        assert editedReadOnlyTask != null;

        Task editedTask = new Task(editedReadOnlyTask);
        syncMasterTagListWith(editedTask);
        // TODO: the tags master list will be updated even though the below line
        // fails.
        // This can cause the tags master list to have additional tags that are
        // not tagged to any person
        // in the person list.
        tasks.updateTask(index, editedTask);
    }

    /**
     * Ensures that every tag in this person: - exists in the master list
     * {@link #tags} - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Task task) {
        final UniqueTagList taskTags = task.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the
        // master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        taskTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        task.setTags(new UniqueTagList(correctTagReferences));
    }

    /**
     * Ensures that every tag in these persons: - exists in the master list
     * {@link #tags} - points to a Tag object in the master list
     *
     * @see #syncMasterTagListWith(Person)
     */
    private void syncMasterTagListWith(UniqueTaskList tasks) {
        tasks.forEach(this::syncMasterTagListWith);
    }

    public int removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {

        int indexRemoved = tasks.getInternalList().indexOf(key);
        if (tasks.remove(key)) {
            return indexRemoved;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return tasks.asObservableList().size() + " tasks, " + tags.asObservableList().size() + " tags";
        // TODO: refine later
    }

    //@@author A0125221Y
    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        return new UnmodifiableObservableList<>(tasks.asObservableList());
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return new UnmodifiableObservableList<>(tags.asObservableList());
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                        && this.tasks.equals(((AddressBook) other).tasks)
                        && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(tasks, tags);
    }

    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }
}

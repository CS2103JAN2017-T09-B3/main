package seedu.mypotato.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.mypotato.commons.core.ComponentManager;
import seedu.mypotato.commons.core.LogsCenter;
import seedu.mypotato.commons.events.model.TaskManagerChangedEvent;
import seedu.mypotato.commons.events.storage.ChangedFileLocationRequestEvent;
import seedu.mypotato.commons.events.storage.DataSavingExceptionEvent;
import seedu.mypotato.commons.exceptions.DataConversionException;
import seedu.mypotato.model.ReadOnlyTaskManager;
import seedu.mypotato.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskManagerStorage taskManagerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(TaskManagerStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.taskManagerStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String addressBookFilePath, String userPrefsFilePath) {
        this(new XmlTaskManagerStorage(addressBookFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public String getAddressBookFilePath() {
        return taskManagerStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskManager> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(taskManagerStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskManager> readAddressBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskManagerStorage.readAddressBook(filePath);
    }

    @Override
    public void saveTaskList(ReadOnlyTaskManager addressBook) throws IOException {
        saveTaskList(addressBook, taskManagerStorage.getAddressBookFilePath());
    }

    @Override
    public void saveTaskList(ReadOnlyTaskManager addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskManagerStorage.saveTaskList(addressBook, filePath);
    }

  //@@author A0135807A
    @Override
    public void setFileLocation(String filepath) {
        taskManagerStorage.setFileLocation(filepath);
    }

    @Override
    @Subscribe
    public void handleTaskListChangedEvent(TaskManagerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            if (taskManagerStorage == null) {
                saveTaskList(event.data);
            } else {
                saveTaskList(event.data, taskManagerStorage.getAddressBookFilePath());
            }
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleTaskManagerChangedEvent(ChangedFileLocationRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Setting Storage Location Path"));
        this.setFileLocation(event.config.getAddressBookFilePath());
    }

}

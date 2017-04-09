package seedu.mypotato.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.mypotato.commons.exceptions.DataConversionException;
import seedu.mypotato.model.ReadOnlyTaskManager;

/**
 * Represents a storage for {@link seedu.mypotato.model.TaskManager}.
 */
public interface TaskManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    String getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyTaskManager}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskManager> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyTaskManager> readAddressBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskManager} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskList(ReadOnlyTaskManager addressBook) throws IOException;

    /**
     * @see #saveTaskList(ReadOnlyTaskManager)
     */
    void saveTaskList(ReadOnlyTaskManager addressBook, String filePath) throws IOException;

    void setFileLocation(String filepath);

}

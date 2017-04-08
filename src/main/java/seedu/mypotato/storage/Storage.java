package seedu.mypotato.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.mypotato.commons.events.model.AddressBookChangedEvent;
import seedu.mypotato.commons.events.storage.ChangedFileLocationRequestEvent;
import seedu.mypotato.commons.events.storage.DataSavingExceptionEvent;
import seedu.mypotato.commons.exceptions.DataConversionException;
import seedu.mypotato.model.ReadOnlyAddressBook;
import seedu.mypotato.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);

    void handleTaskManagerChangedEvent(ChangedFileLocationRequestEvent event);
}

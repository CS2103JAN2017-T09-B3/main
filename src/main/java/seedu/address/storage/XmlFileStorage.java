package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Stores addressbook data in an XML file
 */
public class XmlFileStorage implements AddressBookStorage{
	//the logger allow Jim to access his data within his account
	private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);
	//this filePath is the name of the file that a specified task being saved in
	private String filePath;
    /**
     * Saves the given addressbook data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableAddressBook addressBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, addressBook);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializableAddressBook loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableAddressBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

	@Override
	public String getAddressBookFilePath(){
		return filePath;
	}

	@Override
	public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
		return readAddressBook(filePath);
	}

	@Override
	public Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws DataConversionException, IOException {
		assert filePath != null;

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("AddressBook file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyAddressBook addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
	}

	@Override
	public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
		saveAddressBook(addressBook, filePath);
	}

	@Override
	public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
		assert addressBook != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBook(addressBook));
	}

}

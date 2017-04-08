package seedu.myPotato.logic.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import seedu.myPotato.commons.core.Config;
import seedu.myPotato.commons.util.ConfigUtil;
import seedu.myPotato.commons.util.FileUtil;
import seedu.myPotato.commons.util.XmlUtil;
import seedu.myPotato.logic.commands.exceptions.CommandException;
import seedu.myPotato.storage.XmlSerializableAddressBook;

//@@author A0135807A
/**
 * Save to a user specified location locally.
 * Default saved filename set to /taskmanager.xml.
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": saves tasks to a specified location. "
            + "Parameters: FILELOCATION" + "Example: " + COMMAND_WORD
            + " data/taskmanager.xml";

    public static final String MESSAGE_SUCCESS = "Tasks saved to %1$s";
    public static final String MESSAGE_CONVERT_SUCCESS = "Convert Success!";
    public static final String MESSAGE_INVALID_PATH = "Invalid Path";
    public static final String MESSAGE_UNMARSHALLING_ERROR = "Unmarshalling error!";
    public static final String MESSAGE_FILE_NOT_FOUND = "File not Found!";
    public static final String DEFAULT_FILE = "/taskmanager.xml";
    public static final String FILE_VALIDATION_REGEX = "^[\\w-:/\\\\._]+$";

    private File file;

    /**
     * Creates a SaveCommand using a File.
     */
    public SaveCommand(File file) {
        this.file = file;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;

        try {
            if (file != null && isValidFile(file)) {
                if (file.isDirectory()) {
                    file = new File(file.getPath() + DEFAULT_FILE);
                }
                if (!file.getPath().endsWith(".xml")) {
                    file = new File(file.getPath() + ".xml");
                }
                FileUtil.createIfMissing(file);
                saveDataToFile(file, new XmlSerializableAddressBook(model.getAddressBook()));
                model.getConfig().setAddressBookFilePath(file.getCanonicalPath());
                ConfigUtil.saveConfig(model.getConfig(), Config.DEFAULT_CONFIG_FILE);
                model.updateFileLocation();
            } else {
                throw new IOException(MESSAGE_INVALID_PATH);
            }
        } catch (IOException io) {
            return new CommandResult(io.getMessage());
        } catch (JAXBException e) {
            return new CommandResult(MESSAGE_UNMARSHALLING_ERROR);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, file.getPath()));
    }

    public static <T> void saveDataToFile(File file, T convertToXml) throws JAXBException, IOException {
        try {
            XmlUtil.saveDataToFile(file, convertToXml);
        } catch (JAXBException e) {
            throw new JAXBException(MESSAGE_UNMARSHALLING_ERROR);
        } catch (FileNotFoundException e) {
            throw new IOException(MESSAGE_FILE_NOT_FOUND);
        }
    }

    public static boolean isValidFile(File file) {
        return file.getAbsolutePath().matches(FILE_VALIDATION_REGEX);
    }

}


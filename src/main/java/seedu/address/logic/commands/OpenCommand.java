package seedu.address.logic.commands;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import seedu.address.commons.core.Config;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.XmlSerializableAddressBook;

public class OpenCommand extends Command {

    public static final String COMMAND_WORD = "open";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": load task to myPotato."
            + "Parameters: FILELOCATION" + "Example: " + COMMAND_WORD
            + " data/taskmanager.xml";

    public static final String MESSAGE_SUCCESS = "Loaded ";
    public static final String MESSAGE_INVALID_PATH = "Invalid Path";
    public static final String MESSAGE_READ_ACCESS_DENIED = "File Read Access Denied";
    public static final String MESSAGE_INVALID_FILE = "Invalid File";
    public static final String DEFAULT_FILE = "taskmanager.xml";

    private File file;

    /**
     * Creates a OpenCommand using a File
     */
    public OpenCommand(File file) {
        this.file = file;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            ReadOnlyAddressBook taskData = XmlUtil.getDataFromFile(file, XmlSerializableAddressBook.class);
            model.getConfig().setAddressBookFilePath(file.getAbsolutePath());
            ConfigUtil.saveConfig(model.getConfig(), Config.DEFAULT_CONFIG_FILE);
            model.updateFileLocation();
            model.resetData(taskData);
        } catch (IOException io) {
            return new CommandResult(MESSAGE_INVALID_PATH);
        } catch (JAXBException Exception) {
            return new CommandResult(MESSAGE_READ_ACCESS_DENIED);
        }
        return new CommandResult(MESSAGE_SUCCESS + file.getName());
    }

}
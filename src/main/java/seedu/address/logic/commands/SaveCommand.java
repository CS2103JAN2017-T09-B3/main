package seedu.address.logic.commands;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import seedu.address.commons.core.Config;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.storage.XmlSerializableAddressBook;

public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": saves tasks to a specified location. "
            + "Parameters: FILELOCATION" + "Example: " + COMMAND_WORD
            + " data/taskmanager.xml";

    public static final String MESSAGE_SUCCESS = "Tasks saved to ";
    public static final String MESSAGE_INVALID_PATH = "Invalid Path";
    public static final String MESSAGE_WRITE_ACCESS_DENIED = "File Write Access Denied";
    public static final String MESSAGE_INVALID_FILE = "Invalid File";
    public static final String DEFAULT_FILE = "taskmanager.xml";

    private File file;

    /**
     * Creates a SaveCommand using a File
     */
    public SaveCommand(File file) {
        this.file = file;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            if (file != null) {
                FileUtil.createIfMissing(file);
                if (file.isDirectory()) {
                    file = new File(file.getPath() + DEFAULT_FILE);
                }
                if (!file.getPath().endsWith(".xml")) {
                    file = new File(file.getPath() + ".xml");
                }
                XmlUtil.saveDataToFile(file, new XmlSerializableAddressBook(model.getAddressBook()));
                model.getConfig().setAddressBookFilePath(file.getCanonicalPath());
                ConfigUtil.saveConfig(model.getConfig(), Config.DEFAULT_CONFIG_FILE);
                model.updateFileLocation();
            } else {
                return new CommandResult(MESSAGE_INVALID_FILE);
            }
        } catch (IOException io) {
            return new CommandResult(MESSAGE_INVALID_PATH);
        } catch (JAXBException Exception) {
            return new CommandResult(MESSAGE_WRITE_ACCESS_DENIED);
        }
         return new CommandResult(MESSAGE_SUCCESS + file.getName());
    }

}


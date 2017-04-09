package seedu.mypotato.logic.commands;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import seedu.mypotato.commons.core.Config;
import seedu.mypotato.commons.events.ui.LoadFirstTaskEvent;
import seedu.mypotato.commons.exceptions.DataConversionException;
import seedu.mypotato.commons.util.ConfigUtil;
import seedu.mypotato.commons.util.XmlUtil;
import seedu.mypotato.logic.commands.exceptions.CommandException;
import seedu.mypotato.model.ReadOnlyAddressBook;
import seedu.mypotato.storage.XmlSerializableAddressBook;

//@@author A0135807A
/**
 * Open a .xml file from user specified location locally.
 * Default saved file location to ./data/taskmanager.xml.
 */
public class OpenCommand extends Command {

    public static final String COMMAND_WORD = "open";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": load task to myPotato."
            + "Parameters: FILELOCATION" + "Example: " + COMMAND_WORD
            + " data/taskmanager.xml";

    public static final String MESSAGE_SUCCESS = "Loaded %1$s";
    public static final String MESSAGE_INVALID_PATH = "Invalid Path";
    public static final String MESSAGE_INVALID_CLASS_FILE = "Invalid Class Instance/Invalid File";
    public static final String MESSAGE_INVALID_CONFIG_FILE = "Invalid Config File!";
    public static final String MESSAGE_READCONFIG_FAIL = "Failed to read config file!";
    public static final String MESSAGE_FILE_NOT_FOUND = "File not Found!";

    public static final String DEFAULT_FILE = "taskmanager.xml";

    private static String appTitle = "myPotato";
    private static String userPrefsFilePath = "preferences.json";
    private static String taskManagerName = "MyTaskManager";

    private File file;


    /** Creates a OpenCommand using a File */
    public OpenCommand(File file) {
        this.file = file;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        assert file != null;
        try {
            ReadOnlyAddressBook taskData = getDataFromFile(file, XmlSerializableAddressBook.class);
            setConfig(model.getConfig(), file.getAbsolutePath());
            model.updateFileLocation();
            setModelData(taskData);
        } catch (IOException io) {
            return new CommandResult(MESSAGE_INVALID_PATH);
        } catch (JAXBException Exception) {
            return new CommandResult(MESSAGE_INVALID_CLASS_FILE);
        } catch (DataConversionException e) {
            return new CommandResult(MESSAGE_READCONFIG_FAIL);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, file.getName()));
    }

    /** Conversion from .xml file to readable data.
    *
    * @param file must not be null.
    * @param readFromXml to convert to readable data.
    * @throws JAXBException is thrown when either the file or the class is invalid.
    * @throws IOException is thrown when the file is not found.
    */
   public static ReadOnlyAddressBook getDataFromFile(File file,
           Class <XmlSerializableAddressBook> readFromXml)throws JAXBException, IOException {
           return XmlUtil.getDataFromFile(file, readFromXml);
   }

    /**
     * @param config to be modified with the path from the .xml file.
     * @param filename is the file location of the file to be read.
     * @throws IOException is thrown when with the {@code Config config}file is not found or unwritable.
     */
    public static void setConfig(Config config, String filename) throws IOException, DataConversionException{
        config.setAddressBookFilePath(filename);
        config.setAppTitle(appTitle);
        config.setUserPrefsFilePath(userPrefsFilePath);
        config.setAddressBookName(taskManagerName);
        try {
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
        } catch (IOException e) {
            throw new IOException(MESSAGE_INVALID_CONFIG_FILE);
        }
        readConfig(Config.DEFAULT_CONFIG_FILE);
    }

    /** load config file */
    public static void readConfig(String config) throws DataConversionException {
        ConfigUtil.readConfig(config);
    }

    /** reset data in model and load first task from the PanelList */
    public void setModelData(ReadOnlyAddressBook taskData) {
        model.resetData(taskData);
        model.handleLoadFirstTaskEvent(new LoadFirstTaskEvent());
    }

}

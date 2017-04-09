package seedu.mypotato.logic.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import seedu.mypotato.commons.core.Config;
import seedu.mypotato.commons.exceptions.DataConversionException;
import seedu.mypotato.commons.util.ConfigUtil;
import seedu.mypotato.commons.util.FileUtil;
import seedu.mypotato.commons.util.XmlUtil;
import seedu.mypotato.logic.commands.exceptions.CommandException;
import seedu.mypotato.storage.XmlSerializableAddressBook;

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
    public static final String MESSAGE_INVALID_CONFIG_FILE = "Invalid Config File!";
    public static final String MESSAGE_READCONFIG_FAIL = "Failed to read config file!";
    public static final String DEFAULT_FILE = "/taskmanager.xml";
    public static final String FILE_VALIDATION_REGEX = "^[\\w-:/\\\\._]+$";

    private static String appTitle = "myPotato";
    private static String userPrefsFilePath = "preferences.json";
    private static String taskManagerName = "MyTaskManager";

    private File file;

    /** Creates a SaveCommand using a File. */
    public SaveCommand(File file) {
        this.file = file;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;

        try {
            if (file != null && isValidPath(file)) {
                file = createValidPathAndConvertToXml(file);
                setConfig(model.getConfig(), file.getCanonicalPath());
                model.updateFileLocation();
            } else {
                throw new IOException(MESSAGE_INVALID_PATH);
            }
        } catch (IOException io) {
            return new CommandResult(io.getMessage());
        } catch (JAXBException e) {
            return new CommandResult(MESSAGE_UNMARSHALLING_ERROR);
        } catch (DataConversionException e) {
            return new CommandResult(MESSAGE_READCONFIG_FAIL);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, file.getPath()));
    }

    /** Conversion to .xml file given data.
     *
     * @param file must not be null.
     * @param convertToXml data to read from for writing to {@code File file}.
     * @throws JAXBException is thrown when either the file or the data is invalid.
     * @throws IOException is thrown when the file is not found.
     */
    public static <T> void saveDataToFile(File file, T convertToXml) throws JAXBException, IOException {
        try {
            XmlUtil.saveDataToFile(file, convertToXml);
        } catch (FileNotFoundException e) {
            throw new IOException(MESSAGE_FILE_NOT_FOUND);
        }
    }

    public static boolean isValidPath(File file) {
        return file.getAbsolutePath().matches(FILE_VALIDATION_REGEX);
    }

    /** Ensure that the path is valid and the right file extension is used.
     * @param file must not be null.
     * @return {@code File file} after path is validated and created.
     * @throws JAXBException when saveDataToFile throws a JAXBException exception.
     * @throws IOException when saveDataToFile throws an IOException exception.
     */
    public File createValidPathAndConvertToXml(File file) throws JAXBException, IOException{
        if (file.isDirectory()) {
            file = new File(file.getPath() + DEFAULT_FILE);
        }
        if (!file.getPath().endsWith(".xml")) {
            file = new File(file.getPath() + ".xml");
        }
        FileUtil.createIfMissing(file);
        saveDataToFile(file, new XmlSerializableAddressBook(model.getAddressBook()));
        return file;
    }

    /**
     * @param config to be modified with user specified file location.
     * @param filename is the file location that the user specify.
     * @throws IOException is thrown when with the {@code Config config}file is not found or unwritable.
     */
    public static void setConfig(Config config, String filename) throws IOException, DataConversionException {
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

}


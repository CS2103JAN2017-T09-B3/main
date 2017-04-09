package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.mypotato.commons.core.Messages;
import seedu.mypotato.commons.exceptions.DataConversionException;
import seedu.mypotato.commons.util.FileUtil;
import seedu.mypotato.logic.commands.OpenCommand;
import seedu.mypotato.logic.commands.SaveCommand;
import seedu.mypotato.model.TaskManager;
import seedu.mypotato.testutil.TestTask;
import seedu.mypotato.testutil.TestUtil;

//@@author A0135807A
/** files are automatically written to the saved location. */
public class OpenAndSaveCommandTest extends TaskManagerGuiTest {
    public static final String FILE_DIR = "src/test/data/sandbox/";
    public static final String FILE_NAME = "src/test/data/sandbox/taskmanager";
    public static final String FILE_ALTERNATE_NAME = "src/test/data/sandbox/myPotato";
    public static final String FILE_XML_EXTENSION = ".xml";
    public static final String INVALID_FILENAME = "data/?*&^";
    private static final File VALID_FILE = new File(FILE_DIR + "validTaskManager.xml");
    private static final File MISSING_FILE = new File(FILE_DIR + "missing.xml");

    public static final String COMMAND_SAVE = "save ";
    public static final String COMMAND_OPEN = "open ";
    public static final String COMMAND_DELETE = "delete ";
    public static final String EMPTY = "";

    public final int index = 1;
    public TestTask[] testTask = td.getTypicalTasks();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /** Test for Valid and Invalid fileNames */
    @Test
    public void isValidPath() {
        assertTrue(SaveCommand.isValidPath(new File(FILE_DIR)));
        assertTrue(SaveCommand.isValidPath(new File(FILE_NAME + FILE_XML_EXTENSION)));

        assertFalse(SaveCommand.isValidPath(new File(INVALID_FILENAME)));
    }

    /** Test for Exception catch */
    @Test
    public void saveDataToFile_StringClass_JAXBException() throws Exception {
        thrown.expect(JAXBException.class);
        VALID_FILE.createNewFile();
        SaveCommand.saveDataToFile(VALID_FILE, new String());
    }

    @Test
    public void saveDataToFile_missingFile_IOException() throws Exception {
        thrown.expect(IOException.class);
        SaveCommand.saveDataToFile(MISSING_FILE, new TaskManager());
    }

    @Test
    public void read_XmlFile_DataConversionException() throws DataConversionException, IOException {
        thrown.expect(DataConversionException.class);
        SaveCommand.readConfig(VALID_FILE.toString());;
    }

    /** Open and save files functionality. */
    @Test
    public void saveAndOpenFile() {
        // Default filename :taskmanager.xml
        assertSaveResult(COMMAND_SAVE + FILE_DIR, FILE_NAME);
        assertOpenResult(COMMAND_OPEN + FILE_DIR, testTask); // FileDirectory
        assertSaveResult(COMMAND_SAVE + FILE_NAME, FILE_NAME); // without .xml
        assertOpenResult(COMMAND_OPEN + FILE_NAME, testTask); // without .xml
        assertSaveResult(COMMAND_SAVE + FILE_NAME + FILE_XML_EXTENSION, FILE_NAME); // with .xml
        assertOpenResult(COMMAND_OPEN + FILE_NAME + FILE_XML_EXTENSION, testTask); // with .xml
        assertSaveResult(COMMAND_SAVE + FILE_ALTERNATE_NAME, FILE_ALTERNATE_NAME);
        // Ensure successful save, to be used for test cases
    }

    /**
     * Save file, Delete and Open file to retrieve the saved data. Alternate
     * file is required as data will be automatically written to the saved
     * location.
     */
    @Test
    public void saveAndCheckTaskList() {
        assertTrue(taskListPanel.isListMatching(testTask));
        assertSaveResult(COMMAND_SAVE + FILE_NAME, FILE_NAME);
        assertSaveResult(COMMAND_SAVE + FILE_ALTERNATE_NAME, FILE_ALTERNATE_NAME);
        commandBox.runCommand(COMMAND_DELETE + index);
        assertTrue(taskListPanel.isListMatching(TestUtil.removeTaskFromList(testTask, index)));
        assertOpenResult(COMMAND_OPEN + FILE_NAME + FILE_XML_EXTENSION, testTask);
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
    }

    /** Test invalid Command confirmation. */
    @Test
    public void invalidCommand() {
        commandBox.runCommand(SaveCommand.COMMAND_WORD + FILE_DIR);
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        commandBox.runCommand(OpenCommand.COMMAND_WORD + FILE_NAME);
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        commandBox.runCommand(COMMAND_SAVE + EMPTY);
        assertResultMessage(SaveCommand.MESSAGE_INVALID_PATH);
        commandBox.runCommand(COMMAND_OPEN + EMPTY);
        assertResultMessage(OpenCommand.MESSAGE_INVALID_PATH);

        commandBox.runCommand(COMMAND_SAVE + INVALID_FILENAME);
        assertResultMessage(SaveCommand.MESSAGE_INVALID_PATH);
    }

    /** Ensure save functions properly. */
    private void assertSaveResult(String command, String filename) {
        File testfile = new File(filename + FILE_XML_EXTENSION);
        if (FileUtil.isFileExists(testfile)) {
            FileUtil.deleteFile(testfile);
        }
        commandBox.runCommand(command);
        assertTrue(FileUtil.isFileExists(testfile));
        assertResultMessage(
                String.format(SaveCommand.MESSAGE_SUCCESS, FileUtil.getPath(filename + FILE_XML_EXTENSION)));
    }

    /** Ensure open functions properly. */
    private void assertOpenResult(String command, TestTask... expectedhits) {
        commandBox.runCommand(command);
        assertTrue(taskListPanel.isListMatching(expectedhits));
    }

}

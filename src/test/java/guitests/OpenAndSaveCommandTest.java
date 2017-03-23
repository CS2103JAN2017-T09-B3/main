package guitests;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.FileUtil;
import seedu.address.testutil.TestTask;

//@@author A0135807A
//files are automatically written to the saved location
public class OpenAndSaveCommandTest extends AddressBookGuiTest {
    public static final String FILE_DIR = "data/";
    public static final String FILE_NAME = "data/taskmanager";
    public static final String FILE_ALTERNATE_NAME = "data/myPotato";
    public static final String FILE_XML_EXTENSION = ".xml";

    // Open and save files functionality
    @Test
    public void save_file() {
        assertSaveResult("save " + FILE_DIR, FILE_NAME); // Default filename: taskmanager.xml
        assertOpenResult("open " + FILE_DIR, td.getTypicalTasks()); // FileDirectory
        assertSaveResult("save " + FILE_NAME, FILE_NAME); // without .xml
        assertOpenResult("open " + FILE_NAME, td.getTypicalTasks()); // without .xml
        assertSaveResult("save " + FILE_NAME + FILE_XML_EXTENSION, FILE_NAME); // with .xml
        assertOpenResult("open " + FILE_NAME + FILE_XML_EXTENSION, td.getTypicalTasks()); // with .xml
        assertSaveResult("save " + FILE_ALTERNATE_NAME, FILE_ALTERNATE_NAME);
        // Ensure successful save, to be used for test cases
    }

    // Save file, Delete and Open file to retrieve the saved data
    // td.getTypicalTasks() = TestTask[]{alice, benson, carl, daniel, elle,
    // fiona}
    @Test
    public void save_checkTaskList() {
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertSaveResult("save " + FILE_NAME, FILE_NAME);
        assertSaveResult("save " + FILE_ALTERNATE_NAME, FILE_ALTERNATE_NAME);
        commandBox.runCommand("delete 1");
        assertTrue(taskListPanel.isListMatching(new TestTask[] { td.benson, td.carl, td.daniel, td.elle, td.fiona }));
        assertOpenResult("open " + FILE_NAME + FILE_XML_EXTENSION, td.getTypicalTasks());
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
    }

    // Test invalid Command confirmation
    @Test
    public void save_invalidCommand_fail() {
        commandBox.runCommand("save" + FILE_DIR);
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        commandBox.runCommand("open" + FILE_NAME);
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    // Ensure save functions properly
    private void assertSaveResult(String command, String filename) {
        File testfile = new File(filename + FILE_XML_EXTENSION);
        if (FileUtil.isFileExists(testfile)) {
            FileUtil.deleteFile(testfile);
        }
        commandBox.runCommand(command);
        assertTrue(FileUtil.isFileExists(testfile));
    }

    // Ensure open functions properly
    private void assertOpenResult(String command, TestTask... expectedhits) {
        commandBox.runCommand(command);
        assertTrue(taskListPanel.isListMatching(expectedhits));
    }
}

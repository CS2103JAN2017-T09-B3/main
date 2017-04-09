package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.mypotato.logic.commands.ListCommand;
import seedu.mypotato.testutil.TestTask;

//@@author A0144895N
public class ListCommandTest extends TaskManagerGuiTest {

    @Test
    public void testNoRequestedList() {
        commandBox.runCommand("list  ");
        assertResultMessage(ListCommand.MESSAGE_NO_LIST);
    }

    @Test
    public void testMultipleList() {
        commandBox.runCommand("list today completed");
        assertResultMessage(ListCommand.MESSAGE_MULTIPLE_LIST);
    }

    @Test
    public void testListAll() {
        commandBox.runCommand("list all");
        assertListSize(td.getTypicalTasks().length);
        assertResultMessage(ListCommand.MESSAGE_ALL_SUCCESS);
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
    }

    @Test
    public void testListCompleted() {
        commandBox.runCommand("list completed");
        TestTask[] expectedList = { td.carl, td.daniel };
        assertListSize(expectedList.length);
        assertResultMessage(ListCommand.MESSAGE_COMPLETED_SUCCESS);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

    @Test
    public void testListToday() {
        commandBox.runCommand("list today");
        TestTask[] expectedList = { td.elle };
        assertListSize(expectedList.length);
        assertResultMessage(ListCommand.MESSAGE_TODAY_SUCCESS);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}

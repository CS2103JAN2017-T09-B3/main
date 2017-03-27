package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;

public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); // no results
        assertFindResult("find Meier", td.benson, td.daniel); // multiple
                                                              // results

        // find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Meier", td.daniel);
    }

    // @@author A0144895N
    @Test
    public void find_caseInsensitive() {
        assertFindResult("find fiona", td.fiona);
        assertFindResult("find alice", td.alice);
    }

    // @@author A0144895N
    @Test
    public void find_partialWord() {
        assertFindResult("find on", td.benson, td.fiona);
    }

    // @@author A0144895N
    @Test
    public void find_inContent() {
        assertFindResult("find content/ con", td.alice, td.benson, td.fiona);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}

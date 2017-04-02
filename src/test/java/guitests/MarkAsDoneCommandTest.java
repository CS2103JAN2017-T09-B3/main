//@@author A0135753A
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.MarkAsDoneCommand.MESSAGE_MARK_TASK_SUCCESS;

import org.junit.Test;

import seedu.address.testutil.TestTask;

public class MarkAsDoneCommandTest extends AddressBookGuiTest {

    @Test
    public void mark() {

    //mark the first task as done
    TestTask[] currentList = td.getTypicalTasks();
    int targetIndex = 1;
    assertMarkSuccess(targetIndex, currentList);

    //mark the last task as done
    targetIndex = currentList.length;
    assertMarkSuccess(targetIndex, currentList);

    //mark the middle task of the list as done
    targetIndex = currentList.length / 2;
    assertMarkSuccess(targetIndex, currentList);

    //invalid index
    commandBox.runCommand("markasdone " + currentList.length + 1);
    assertResultMessage("The task index provided is invalid");

    }
    /**
     * Runs the mark as done command to mark the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to mark the first task in the list,
     * @param currentList A copy of the current list of tasks (before mark).
     */
    private void assertMarkSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToMark = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        boolean expectedStatus = true;
        taskToMark.getStatus().setStatus(true);

        commandBox.runCommand("markasdone " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskToMark.getStatus().getStatus() == expectedStatus);

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark));
    }

}

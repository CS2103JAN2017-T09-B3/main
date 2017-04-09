package guitests;

//@@author A0125221Y
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.mypotato.commons.exceptions.IllegalValueException;
import seedu.mypotato.logic.commands.EditCommand;
import seedu.mypotato.logic.commands.UndoCommand;
import seedu.mypotato.testutil.TaskBuilder;
import seedu.mypotato.testutil.TestTask;
import seedu.mypotato.testutil.TestUtil;

public class UndoCommandTest extends AddressBookGuiTest {

    @Test
    public void undoAddSuccess() {

        TestTask[] currentList = td.getTypicalTasks();

        //Begin by adding one task
        TestTask taskToAdd = td.hoon;
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertListSize(currentList.length + 1);

        //Next undo the previous add command
        commandBox.runCommand("undo");
        assertListSize(currentList.length);
        assertTrue(taskListPanel.isListMatching(currentList));

    }

    @Test
    public void undoDeleteSuccess() {

        TestTask[] currentList = td.getTypicalTasks();

        //Now test deletion of one single task
        commandBox.runCommand("delete 1");
        assertListSize(currentList.length - 1);
        currentList = TestUtil.removeTaskFromList(currentList, 1);

        //Try to undo the previous delete command
        commandBox.runCommand("undo");
        currentList = TestUtil.addTasksToListAtIndex(currentList, 0, td.alice);
        assertListSize(currentList.length);
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    @Test
    public void undoClearSuccess() {
        TestTask[] allTaskList = td.getTypicalTasks();

        //clear all tasks
        commandBox.runCommand("clear");
        assertListSize(0);

        //undo
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(allTaskList));
    }

    @Test
    public void undoEditSuccess() throws IllegalValueException {
        TestTask[] oldTaskList = td.getTypicalTasks();

        //edit start time of a task
        String detailsToEdit = "start/9 Nov 2010 12pm";
        int addressBookIndex = 2;

        TestTask taskToEdit = oldTaskList[addressBookIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withStartDateTime("9 Nov 2010 12pm").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTask);

        //undo
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(oldTaskList));

        //edit tags of a task
        String newTags = "#friend #lol";

        TestTask taskToBeEdit = oldTaskList[addressBookIndex - 1];
        TestTask nextEditedTask = new TaskBuilder(taskToBeEdit).withTags("friend", "lol").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, newTags, nextEditedTask);

        //undo
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(oldTaskList));

        //remove tags of a task
        String detailsToEdit2 = "#";

        TestTask taskToEdit2 = oldTaskList[addressBookIndex - 1];
        TestTask editedTask2 = new TaskBuilder(taskToEdit2).withTags().build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit2, editedTask2);

        //undo
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(oldTaskList));

        //edit deadline of task
        String detailsToEdit3 = "end/10 Dec 10pm";

        TestTask taskToEdit3 = oldTaskList[addressBookIndex - 1];
        TestTask editedTask3 = new TaskBuilder(taskToEdit3).withEndDateTime("10 Dec 10pm").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit3, editedTask3);

        //undo
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(oldTaskList));
    }

    @Test
    public void undoMultipleSuccess() throws IllegalValueException {
        TestTask[] allTaskList = td.getTypicalTasks();
        TestTask[] oldTaskList = td.getTypicalTasks();

        //edit a task
        String detailsToEdit = "start/9 Nov 2010 12pm";
        int addressBookIndex = 2;

        TestTask taskToEdit = oldTaskList[addressBookIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withStartDateTime("9 Nov 2010 12pm").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTask);

        //clear all tasks
        commandBox.runCommand("clear");
        assertListSize(0);

        //undo clear
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(allTaskList));

        //undo edit
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(oldTaskList));
    }

    @Test
    public void undoCommandFail() {
        // Undo without any previous commands
	commandBox.runCommand("undo");
	assertResultMessage(UndoCommand.MESSAGE_FAIL);

	// Undo those commands that are not undoable
	commandBox.runCommand("list all");
	commandBox.runCommand("undo");
	assertResultMessage(UndoCommand.MESSAGE_FAIL);

	// Undo again after you had undo all the previous commands
	commandBox.runCommand("delete 1");
	commandBox.runCommand("delete 2");
	commandBox.runCommand("clear");
	commandBox.runCommand("undo");
	commandBox.runCommand("undo");
	commandBox.runCommand("undo");
	commandBox.runCommand("undo");
	assertResultMessage(UndoCommand.MESSAGE_FAIL);
	}

    private void assertEditSuccess(int filteredTaskListIndex, int addressBookIndex,
                String detailsToEdit, TestTask editedTask) {

        TestTask[] oldTaskList = td.getTypicalTasks();
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getTitle().fullTitle);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        oldTaskList[addressBookIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(oldTaskList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

}

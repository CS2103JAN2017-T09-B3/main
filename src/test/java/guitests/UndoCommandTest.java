package guitests;

//@@author A0125221Y
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class UndoCommandTest extends AddressBookGuiTest{

	@Test
	public void undoWithOneModification_Success(){

		TestTask[] currentList = td.getTypicalTasks();

		//Begin by adding one task
		TestTask taskToAdd = td.hoon;
		commandBox.runCommand(taskToAdd.getAddCommand());
		assertListSize(currentList.length + 1);

		//Next undo the previous add command
		commandBox.runCommand("undo");
		assertListSize(currentList.length);
		assertTrue(taskListPanel.isListMatching(currentList));

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



}

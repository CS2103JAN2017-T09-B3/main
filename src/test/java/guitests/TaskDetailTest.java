package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.parser.CliSyntax;
import seedu.address.ui.TaskDetail;

public class TaskDetailTest extends AddressBookGuiTest {

    private static final String TAGS_THAT_SUCCEEDS = "#JUnit";
    private static final String TAGS_THAT_FAILS = "#JUnit.";

    private ArrayList<String> defaultStyleOfTaskDetail;
    private ArrayList<String> errorStyleOfTaskDetail;

    @Before
    public void setUp() {
        defaultStyleOfTaskDetail = new ArrayList<>(taskDetail.getTagsStyleClass());
        assertFalse("TaskDescription default style classes should not contain error style class.",
                defaultStyleOfTaskDetail.contains(TaskDetail.ERROR_STYLE_CLASS));

        // build style class for error
        errorStyleOfTaskDetail = new ArrayList<>(defaultStyleOfTaskDetail);
        errorStyleOfTaskDetail.add(TaskDetail.ERROR_STYLE_CLASS);
    }

    @Test
    public void taskDetail_commandSucceeds_textClearedAndStyleClassRemainsTheSame() {
        taskDetail.runTags(TAGS_THAT_SUCCEEDS);

        assertEquals(CliSyntax.EMPTY_STRING, commandBox.getCommandInput());
        assertEquals(defaultStyleOfTaskDetail, taskDetail.getTagsStyleClass());
    }

    @Test
    public void taskDetail_commandFails_textStaysAndErrorStyleClassAdded() {

        commandBox.clickOnTextField();
        taskDetail.runTags(TAGS_THAT_FAILS);
        assertEquals(TAGS_THAT_FAILS, taskDetail.getTagsInput());
        assertEquals(errorStyleOfTaskDetail, taskDetail.getTagsStyleClass());

        taskDetail.runTitle(CliSyntax.EMPTY_STRING);
        assertEquals(CliSyntax.EMPTY_STRING, taskDetail.getTitleInput());
        assertEquals(errorStyleOfTaskDetail, taskDetail.getTagsStyleClass());

    }

    @Test
    public void taskDetail_commandSucceedsAfterFailedCommand_textClearedAndErrorStyleClassRemoved() {
        // add error style to simulate a failed command
        taskDetail.getTagsStyleClass().add(TaskDetail.ERROR_STYLE_CLASS);

        taskDetail.runTags(TAGS_THAT_SUCCEEDS);

        assertEquals(TAGS_THAT_SUCCEEDS, taskDetail.getTagsInput());
        assertEquals(defaultStyleOfTaskDetail, taskDetail.getTagsStyleClass());
        assertEquals(defaultStyleOfTaskDetail, taskDetail.getTitleStyleClass());
    }

}


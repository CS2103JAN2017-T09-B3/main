package guitests.guihandles;

import guitests.GuiRobot;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import seedu.address.TestApp;

//@@author A0135807A
/**
 * A handle to the title, startTime, endTime, tags TextField in the GUI.
 */
public class TaskDetailHandle extends GuiHandle {

    private static final String TITLE_INPUT_FIELD_ID = "#titleTextField";
    private static final String STARTTIME_INPUT_FIELD_ID = "#startTimeTextField";
    private static final String ENDTIME_INPUT_FIELD_ID = "#endTimeTextField";
    private static final String TAGS_INPUT_FIELD_ID = "#tagsTextField";

    public TaskDetailHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    /** Clicks on the Title TextField. */
    public void clickOnTitleTextField() {
        guiRobot.clickOn(TITLE_INPUT_FIELD_ID);
    }

    /** Clicks on the StartTime TextField. */
    public void clickOnStartTimeTextField() {
        guiRobot.clickOn(STARTTIME_INPUT_FIELD_ID);
    }

    /** Clicks on the EndTime TextField. */
    public void clickOnEndTimeTextField() {
        guiRobot.clickOn(ENDTIME_INPUT_FIELD_ID);
    }

    /** Clicks on the Tags TextField. */
    public void clickOnTagsTextField() {
        guiRobot.clickOn(TAGS_INPUT_FIELD_ID);
    }

    public void enterTitle(String title) {
        setTextField(TITLE_INPUT_FIELD_ID, title);
    }

    public void enterStartTime(String startTime) {
        setTextField(STARTTIME_INPUT_FIELD_ID, startTime);
    }

    public void enterEndTime(String endTime) {
        setTextField(ENDTIME_INPUT_FIELD_ID, endTime);
    }

    public void enterTags(String tags) {
        setTextField(TAGS_INPUT_FIELD_ID, tags);
    }

    public String getTitleInput() {
        return getTextFieldText(TITLE_INPUT_FIELD_ID);
    }

    public String getStartTimeInput() {
        return getTextFieldText(STARTTIME_INPUT_FIELD_ID);
    }

    public String getEndTimeInput() {
        return getTextFieldText(ENDTIME_INPUT_FIELD_ID);
    }

    public String getTagsInput() {
        return getTextFieldText(TAGS_INPUT_FIELD_ID);
    }

    /**
     * Enters the given Title in the Title TextField and press ENTER.
     */
    public void runTitle(String title) {
        enterTitle(title);
        pressEnter();
        guiRobot.sleep(200); //Give time for the content to be saved
    }

    /**
     * Enters the given Tags in the Tags TextField and press ENTER.
     */
    public void runTags(String tags) {
        enterTags(tags);
        pressEnter();
        guiRobot.sleep(200); //Give time for the content to be saved
    }

    public ObservableList<String> getTagsStyleClass() {
        return getNode(TAGS_INPUT_FIELD_ID).getStyleClass();
    }

    public ObservableList<String> getTitleStyleClass() {
        return getNode(TAGS_INPUT_FIELD_ID).getStyleClass();
    }
}


package guitests.guihandles;

import guitests.GuiRobot;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

/**
 * A handle to the Content TextField in the GUI.
 */
public class TaskDescriptionHandle extends GuiHandle {

    private static final String CONTENT_INPUT_FIELD_ID = "#contentTextArea";

    public TaskDescriptionHandle(GuiRobot guiRobot, Stage primaryStage, String stageTitle) {
        super(guiRobot, primaryStage, stageTitle);
    }

    /**
     * Clicks on the TextArea.
     */
    public void clickOnTextArea() {
        guiRobot.clickOn(CONTENT_INPUT_FIELD_ID);
    }

    public void enterContent(String content) {
        setTextArea(CONTENT_INPUT_FIELD_ID, content);
    }

    public String getContentInput() {
        return getTextAreaText(CONTENT_INPUT_FIELD_ID);
    }

    /**
     * Enters the given content in the Content TextArea and presses enter.
     */
    public void runContent(String content) {
        enterContent(content);
        //guiRobot.clickOn(new Point2D(500.0, 450.0), MouseButton.PRIMARY);
        guiRobot.clickOn(content, MouseButton.PRIMARY);
        pressEnter();
        guiRobot.sleep(200); //Give time for the content to be saved
    }

    public ObservableList<String> getStyleClass() {
        return getNode(CONTENT_INPUT_FIELD_ID).getStyleClass();
    }
}

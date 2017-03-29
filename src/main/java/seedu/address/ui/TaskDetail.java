//@@author A0135807A
package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.EMPTY_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentTokenizer.Prefix;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Displays the task details and content.
 */
public class TaskDetail extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TaskDetail.class);

    private static final String FXML = "TaskDetail.fxml";
    private static final String COMMAND_EDIT = "edit %1$s %2$s";

    private static final String MESSAGE_SUPPORT = "Press Enter to save %1$s!";
    private static final String MESSAGE_TAG = "Create a tag with a Prefix '#'.";

    private Logic logic;


    @FXML
    private TextField title;

    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;

    @FXML
    private TextField tags;

    @FXML
    private Label labelTaskTitle;

    @FXML
    private Label labelStartTime;

    @FXML
    private Label labelEndTime;

    @FXML
    private Label labelTags;

    /**
     * @param placeholder
     * The AnchorPane where the TaskDetail must be inserted.
     */
    public TaskDetail(AnchorPane placeholder, Logic logic) {
        super(FXML);
        initPromptText();
        initStyle();
        initAnchorBoundary();
        stopFocusTranversable();
        placeholder.getChildren().addAll(getRoot(), title, startTime, endTime, tags);
        this.logic = logic;
    }

    /**
     * Update the task with the newDetail.
     *
     * @param taskToEdit selected task.
     * @param prefix format to differentiate parameters.
     * @param newDetail to be updated to the right task.
     * @param field text field to obtain user input.
     */
    public void saveAndShowContent(ReadOnlyTask taskToEdit, Prefix prefix, String newDetail, TextField field) {
        assert logic != null;

        try {
            CommandResult commandResult = logic.execute(String.format(COMMAND_EDIT,
                    logic.getFilteredTaskList().indexOf(taskToEdit) + 1, prefix.toString() + newDetail));
            setStyleToIndicateCommandSuccess(field);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
        } catch (CommandException e) {
            setStyleToIndicateCommandFailure(field);
            logger.info("Invalid Command: " + newDetail);
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    /** load task details from TaskCard.
     *  SetKeyPressedEvent on enter to save details.
     *
     * @param task cannot be null.
     */
    public void loadTaskPage(ReadOnlyTask task) {
        assert task != null;

        init(task);
        initKeyPressedEvents(task, PREFIX_TITLE, title.getText(), title);
        initKeyPressedEvents(task, PREFIX_DATE_TIME_START, startTime.getText(), startTime);
        initKeyPressedEvents(task, PREFIX_DATE_TIME_END, endTime.getText(), endTime);

        tags.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER && !tags.getText().isEmpty()) {
                if (tags.getText().startsWith(PREFIX_TAG.toString())) {
                    saveAndShowContent(task, PREFIX_TAG, tags.getText().substring(1), tags);
                } else {
                    saveAndShowContent(task, PREFIX_TAG, tags.getText(), tags);
                }
            } else if (keyEvent.getCode() == KeyCode.ENTER && tags.getText().isEmpty()) {
                saveAndShowContent(task, PREFIX_TAG, EMPTY_STRING, tags);
            }
        });
    }

    /**
     * @param task cannot be null.
     * @param prefix to differentiate command.
     * @param updatedText to read from User Interface.
     * @param field must be a valid TextField.
     */
    public void initKeyPressedEvents(ReadOnlyTask task, Prefix prefix, String updatedText, TextField field) {
        field.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                saveAndShowContent(task, prefix, field.getText(), field);
            }
        });
    }

    /** PromptTexts to remind users how to save*/
    public void initPromptText() {
        title.promptTextProperty().set(String.format(MESSAGE_SUPPORT, "Title"));
        startTime.promptTextProperty().set(String.format(MESSAGE_SUPPORT, "Start Time"));
        endTime.promptTextProperty().set(String.format(MESSAGE_SUPPORT, "End Time"));
        tags.promptTextProperty().set(MESSAGE_TAG);
    }

    /** fill Label color*/
    public void initStyle() {
        labelTaskTitle.setStyle("-fx-text-fill: white");
        labelStartTime.setStyle("-fx-text-fill: white");
        labelEndTime.setStyle("-fx-text-fill: white");
        labelTags.setStyle("-fx-text-fill: white");
    }

    /** Allocate TextFields Boundary*/
    public void initAnchorBoundary() {
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(title, 120.0, 100.0, 10.0, 150.0);
        FxViewUtil.applyAnchorBoundaryParameters(startTime, 120.0, 100.0, 50.0, 110.0);
        FxViewUtil.applyAnchorBoundaryParameters(endTime, 120.0, 100.0, 90.0, 70.0);
        FxViewUtil.applyAnchorBoundaryParameters(tags, 120.0, 100.0, 130.0, 30.0);
    }

    /** stop FocusTranversable in the following fields */
    public void stopFocusTranversable() {
        labelTaskTitle.setFocusTraversable(false);
        labelStartTime.setFocusTraversable(false);
        labelEndTime.setFocusTraversable(false);
        labelTags.setFocusTraversable(false);
    }

    /** Read Task details and displays on User Interface
     *
     * @param task cannot be null.
     */
    public void init(ReadOnlyTask task) {
        assert task != null;

        String taggings = EMPTY_STRING;
        title.setText(task.getTitle().toString());
        startTime.setText(task.getDateTime().getStartDateTime().isPresent()
                ? task.getDateTime().getStartDateTime().get().toString() : EMPTY_STRING);
        endTime.setText(task.getDateTime().getEndDateTime().isPresent()
                ? task.getDateTime().getEndDateTime().get().toString() : EMPTY_STRING);
        for (Tag tag : task.getTags()) {
            taggings += tag.toString();
        }
        tags.setText(taggings);
    }

    /**
     * Sets the Task Details style to indicate a successful command.
     */
    private void setStyleToIndicateCommandSuccess(TextField textField) {
        textField.getStyleClass().remove(CommandBox.ERROR_STYLE_CLASS);
    }

    /**
     * Sets the Task Details style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure(TextField textField) {
        textField.getStyleClass().add(CommandBox.ERROR_STYLE_CLASS);
    }

}


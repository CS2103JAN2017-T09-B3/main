package seedu.myPotato.ui;

import static seedu.myPotato.logic.parser.CliSyntax.EMPTY_STRING;
import static seedu.myPotato.logic.parser.CliSyntax.PREFIX_DATE_TIME_END;
import static seedu.myPotato.logic.parser.CliSyntax.PREFIX_DATE_TIME_START;
import static seedu.myPotato.logic.parser.CliSyntax.PREFIX_EMPTY;
import static seedu.myPotato.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.myPotato.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.myPotato.commons.core.LogsCenter;
import seedu.myPotato.commons.events.ui.NewResultAvailableEvent;
import seedu.myPotato.commons.util.FxViewUtil;
import seedu.myPotato.logic.Logic;
import seedu.myPotato.logic.commands.CommandResult;
import seedu.myPotato.logic.commands.exceptions.CommandException;
import seedu.myPotato.logic.parser.ArgumentTokenizer.Prefix;
import seedu.myPotato.model.tag.Tag;
import seedu.myPotato.model.task.ReadOnlyTask;
//@@author A0135807A
/**
 * Displays the task details and content.
 */
public class TaskDetail extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TaskDetail.class);

    private static final String FXML = "TaskDetail.fxml";
    private static final String COMMAND_EDIT = "edit %1$s %2$s";

    private static final String MESSAGE_SUPPORT = "Press Enter to save %1$s!";
    private static final String MESSAGE_TAG = "Leave a whitespace between tags and press ENTER to save!";
    public static final String ERROR_STYLE_CLASS = "error";
    private static final int INDEX = 1; //filteredTaskList index starts from 0.

    private Logic logic;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField startTimeTextField;

    @FXML
    private TextField endTimeTextField;

    @FXML
    private TextField tagsTextField;

    @FXML
    private Label labelTaskTitle;

    @FXML
    private Label labelStartTime;

    @FXML
    private Label labelEndTime;

    @FXML
    private Label labelTags;

    /**
     * @param AnchorPane placeholder
     * The AnchorPane where the TaskDetail must be inserted.
     */
    public TaskDetail(AnchorPane placeholder, Logic logic) {
        super(FXML);
        initPromptText();
        initStyle();
        initAnchorBoundary();
        stopFocusTranversable();
        placeholder.getChildren().addAll(getRoot(), titleTextField, startTimeTextField, endTimeTextField,
                tagsTextField);
        this.logic = logic;
    }

    /**
     * Update the task with the newDetail.
     *
     * @param ReadOnlyTask taskToEdit cannot be null.
     * @param Prefix prefix format to differentiate parameters.
     * @param String newDetail to be updated to the right task.
     * @param TextField field to read user input.
     */
    public void saveAndShowContent(ReadOnlyTask taskToEdit, Prefix prefix, String newDetail, TextField field) {
        assert logic != null;

        try {
            CommandResult commandResult = logic.execute(String.format(COMMAND_EDIT,
                    logic.getFilteredTaskList().indexOf(taskToEdit) + INDEX, prefix.toString() + newDetail));
            setStyleToIndicateCommandSuccess();
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
        } catch (CommandException e) {
            setStyleToIndicateCommandFailure(field);
            logger.info("Invalid Command: " + newDetail);
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    /**
     * load Task details from TaskCard.
     * SetKeyPressedEvent on enter to save details.
     *
     * @param ReadOnlyTask task cannot be null.
     */
    public void loadTaskPage(ReadOnlyTask task) {
        assert task != null;

        init(task);
        initKeyPressedEvents(task, PREFIX_TITLE, titleTextField.getText(), titleTextField);
        initKeyPressedEvents(task, PREFIX_DATE_TIME_START, startTimeTextField.getText(), startTimeTextField);
        initKeyPressedEvents(task, PREFIX_DATE_TIME_END, endTimeTextField.getText(), endTimeTextField);

        tagsTextField.setOnKeyPressed(keyEvent -> {
            String tagging = "";
            String[] tagarray = tagsTextField.getText().split(" ");
            for (String tag : tagarray) {
                if (tag.startsWith(PREFIX_TAG.toString())) {
                    tagging += tag;
                } else {
                    tagging += PREFIX_TAG + tag;
                }
            }
            if (keyEvent.getCode() == KeyCode.ENTER && !tagsTextField.getText().isEmpty()) {
                saveAndShowContent(task, PREFIX_EMPTY, tagging, tagsTextField);
            } else if (keyEvent.getCode() == KeyCode.ENTER && tagsTextField.getText().isEmpty()) {
                saveAndShowContent(task, PREFIX_TAG, EMPTY_STRING, tagsTextField);
            }
        });
    }

    public void loadTaskDetail(ReadOnlyTask task) {
        init(task);
    }

    /**
     * @param ReadOnlyTask task cannot be null.
     * @param Prefix prefix to differentiate command.
     * @param String updatedText to read from User Interface.
     * @param TextField field must be a valid TextField.
     */
    public void initKeyPressedEvents(ReadOnlyTask task, Prefix prefix, String updatedText, TextField field) {
        field.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                saveAndShowContent(task, prefix, field.getText(), field);
            }
        });
    }

    /** PromptTexts to remind users how to save */
    public void initPromptText() {
        titleTextField.promptTextProperty().set(String.format(MESSAGE_SUPPORT, "Title"));
        startTimeTextField.promptTextProperty().set(String.format(MESSAGE_SUPPORT, "Start Time"));
        endTimeTextField.promptTextProperty().set(String.format(MESSAGE_SUPPORT, "End Time"));
        tagsTextField.promptTextProperty().set(MESSAGE_TAG);
    }

    /** fill Label color */
    public void initStyle() {
        labelTaskTitle.setStyle("-fx-text-fill: white");
        labelStartTime.setStyle("-fx-text-fill: white");
        labelEndTime.setStyle("-fx-text-fill: white");
        labelTags.setStyle("-fx-text-fill: white");
    }

    /** Allocate TextFields Boundary */
    public void initAnchorBoundary() {
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(titleTextField, 120.0, 100.0, 10.0, 150.0);
        FxViewUtil.applyAnchorBoundaryParameters(startTimeTextField, 120.0, 100.0, 50.0, 110.0);
        FxViewUtil.applyAnchorBoundaryParameters(endTimeTextField, 120.0, 100.0, 90.0, 70.0);
        FxViewUtil.applyAnchorBoundaryParameters(tagsTextField, 120.0, 100.0, 130.0, 30.0);
    }

    /** stop FocusTranversable in the following fields */
    public void stopFocusTranversable() {
        labelTaskTitle.setFocusTraversable(false);
        labelStartTime.setFocusTraversable(false);
        labelEndTime.setFocusTraversable(false);
        labelTags.setFocusTraversable(false);
    }

    /**
     * Read Task details and displays on User Interface
     *
     * @param ReadOnlyTask task cannot be null.
     */
    public void init(ReadOnlyTask task) {
        assert task != null;

        String taggings = EMPTY_STRING;
        titleTextField.setText(task.getTitle().toString());
        startTimeTextField.setText(task.getDateTime().getStartDateTime().isPresent()
                ? task.getDateTime().getStartDateTime().get().toString() : EMPTY_STRING);
        endTimeTextField.setText(task.getDateTime().getEndDateTime().isPresent()
                ? task.getDateTime().getEndDateTime().get().toString() : EMPTY_STRING);
        for (Tag tag : task.getTags()) {
            taggings += tag.toString();
        }
        tagsTextField.setText(taggings);
    }

    /**
     * Sets the Task Details style to indicate a successful command.
     * Removes old error styles.
     */
    private void setStyleToIndicateCommandSuccess() {
        titleTextField.getStyleClass().remove(CommandBox.ERROR_STYLE_CLASS);
        startTimeTextField.getStyleClass().remove(CommandBox.ERROR_STYLE_CLASS);
        endTimeTextField.getStyleClass().remove(CommandBox.ERROR_STYLE_CLASS);
        tagsTextField.getStyleClass().remove(CommandBox.ERROR_STYLE_CLASS);
    }

    /**
     * Sets the Task Details style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure(TextField textField) {
        textField.getStyleClass().add(CommandBox.ERROR_STYLE_CLASS);
    }

}

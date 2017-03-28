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

//@@author A0135807A
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

    private String saveTitle;
    private String saveStartTime;
    private String saveEndTime;
    private String saveTags;

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

    public String getSaveTitle() {
        return saveTitle;
    }

    public String getSaveStartTime() {
        return saveStartTime;
    }

    public String getSaveEndTime() {
        return saveEndTime;
    }

    public String getSaveTags() {
        return saveTags;
    }

    public void setSaveTitle(String saveTitle) {
        this.saveTitle = saveTitle;
    }

    public void setSaveStartTime(String saveStartTime) {
        this.saveStartTime = saveStartTime;
    }

    public void setSaveEndTime(String saveEndTime) {
        this.saveEndTime = saveEndTime;
    }

    public void setSaveTags(String saveTags) {
        this.saveTags = saveTags;
    }

    /**
     * @param placeholder
     * The AnchorPane where the TaskDetail must be inserted.
     */
    public TaskDetail(AnchorPane placeholder, Logic logic) {
        super(FXML);
        title.promptTextProperty().set(String.format(MESSAGE_SUPPORT, "Title"));
        startTime.promptTextProperty().set(String.format(MESSAGE_SUPPORT, "Start Time"));
        endTime.promptTextProperty().set(String.format(MESSAGE_SUPPORT, "End Time"));
        tags.promptTextProperty().set(MESSAGE_TAG);

        title.setText(String.format(MESSAGE_SUPPORT, "Title"));
        startTime.setText(String.format(MESSAGE_SUPPORT, "Start Time"));
        endTime.setText(String.format(MESSAGE_SUPPORT, "End Time"));
        tags.setText(MESSAGE_NOT_EDITABLE);

        labelTaskTitle.setStyle("-fx-text-fill: white");
        labelStartTime.setStyle("-fx-text-fill: white");
        labelEndTime.setStyle("-fx-text-fill: white");
        labelTags.setStyle("-fx-text-fill: white");

        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().addAll(getRoot());
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
        field.setText(newDetail);
    }

    public void loadTaskPage(ReadOnlyTask task) {
        assert task != null;

        String taggings = "";
        title.setText(task.getTitle().toString());

        startTime.setText(task.getDateTime().getStartDateTime().isPresent()
                ? task.getDateTime().getStartDateTime().get().toString() : "");
        endTime.setText(task.getDateTime().getEndDateTime().isPresent()
                ? task.getDateTime().getEndDateTime().get().toString() : "");

        for (Tag tag : task.getTags()) {
            taggings += tag.toString();
        }
        tags.setText(taggings);

        title.textProperty().addListener((observable, oldTitle, newTitle) -> setSaveTitle(newTitle));
        startTime.textProperty()
                .addListener((observable, oldStartTime, newStartTime) -> setSaveStartTime(newStartTime));
        endTime.textProperty().addListener((observable, oldEndTime, newEndTime) -> setSaveEndTime(newEndTime));
        tags.textProperty().addListener((observable, oldTags, newTags) -> setSaveTags(newTags));

        title.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                saveAndShowContent(task, PREFIX_TITLE, getSaveTitle(), title);
            }
        });

        startTime.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                saveAndShowContent(task, PREFIX_DATE_TIME_START, getSaveStartTime(), startTime);
            }
        });

        endTime.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                saveAndShowContent(task, PREFIX_DATE_TIME_END, getSaveEndTime(), endTime);
            }
        });

        tags.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER && !getSaveTags().isEmpty()) {
                if (getSaveTags().startsWith(PREFIX_TAG.toString())) {
                    saveAndShowContent(task, PREFIX_TAG, getSaveTags().substring(1), tags);
                } else {
                    saveAndShowContent(task, PREFIX_TAG, getSaveTags(), tags);
                }
            } else if (keyEvent.getCode() == KeyCode.ENTER && getSaveTags().isEmpty()) {
                saveAndShowContent(task, PREFIX_TAG, EMPTY_STRING, tags);
            }
        });
    }

    /**
     * Sets the Task Description style to indicate a successful command.
     */
    private void setStyleToIndicateCommandSuccess(TextField textField) {
        textField.getStyleClass().remove(CommandBox.ERROR_STYLE_CLASS);
    }

    /**
     * Sets the Task Description style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure(TextField textField) {
        textField.getStyleClass().add(CommandBox.ERROR_STYLE_CLASS);
    }

}

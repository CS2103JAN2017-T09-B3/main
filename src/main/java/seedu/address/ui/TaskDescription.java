package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;

//@@author A0135807A
/**
 * The Task Description of the App.
 */
public class TaskDescription extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TaskDescription.class);

    private static final String MESSAGE_SUPPORT = "Data will be saved when you press ENTER.";
    private static final String FXML = "TaskDescription.fxml";
    private static final String COMMAND_EDIT = "edit %1$s %2$s";

    private Logic logic;

    private String newContent; // store latest Content

    @FXML
    private TextArea content;

    public String getNewContent() {
        return newContent;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent;
    }

    /**
     * @param placeholder
     * The AnchorPane where the TaskDescription must be inserted
     */
    public TaskDescription(AnchorPane placeholder, Logic logic) {
        super(FXML);
        content.promptTextProperty().set(MESSAGE_SUPPORT);
        content.setWrapText(true);
        FxViewUtil.applyAnchorBoundaryParameters(content, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().addAll(content);
        this.logic = logic;
    }

    public void saveAndShowContent(ReadOnlyTask taskToEdit, String newContent) {
        try {
            CommandResult commandresult = logic.execute(String.format(COMMAND_EDIT,
                    logic.getFilteredTaskList().indexOf(taskToEdit) + 1, PREFIX_CONTENT + newContent));
            setStyleToIndicateCommandSuccess();
            raise(new NewResultAvailableEvent(commandresult.feedbackToUser));
        } catch (CommandException e) {
            setStyleToIndicateCommandFailure();
            logger.info("Invalid Command: " + newContent);
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    public void loadTaskPage(ReadOnlyTask task) {
        content.setText(task.getContent().toString());
        setNewContent(task.getContent().toString());
      
        content.textProperty().addListener((observable, oldContent, newContent) -> setNewContent(newContent));
      
        content.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                saveAndShowContent(task, getNewContent());
            }
        });
    }

    /**
     * Sets the Task Description style to indicate a successful command.
     */
    private void setStyleToIndicateCommandSuccess() {
        content.getStyleClass().remove(CommandBox.ERROR_STYLE_CLASS);
    }

    /**
     * Sets the Task Description style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        content.getStyleClass().add(CommandBox.ERROR_STYLE_CLASS);
    }

}

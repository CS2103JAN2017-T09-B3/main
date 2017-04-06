//@@author A0135807A
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
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.task.Content;
import seedu.address.model.task.ReadOnlyTask;

/**
 * The Task Description of myPotato.
 */
public class TaskDescription extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TaskDescription.class);

    private static final String MESSAGE_SUPPORT = "Content will be saved when you press ENTER.";
    private static final String FXML = "TaskDescription.fxml";
    private static final String COMMAND_EDIT = "edit %1$s %2$s";
    private static final int INDEX = 1;

    public static final String ERROR_STYLE_CLASS = "error";

    private Logic logic;

    @FXML
    private TextArea contentTextArea;

    /**
     * @param AnchorPane placeholder
     * The AnchorPane where the TaskDescription must be inserted.
     * @param Logic logic
     * Retrieve logic component to execute command.
     */
    public TaskDescription(AnchorPane placeholder, Logic logic) {
        super(FXML);
        contentTextArea.promptTextProperty().set(MESSAGE_SUPPORT);
        contentTextArea.setWrapText(true);
        FxViewUtil.applyAnchorBoundaryParameters(contentTextArea, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().addAll(contentTextArea);
        this.logic = logic;
    }

    /**
     * @param ReadOnlyTask taskToEdit
     * The task to be updated.
     * @param String newContent
     * The Content to be updated in taskToEdit.
     */
    public void saveAndShowContent(ReadOnlyTask taskToEdit, String newContent) {
        try {
            if (!Content.isValidContent(newContent)) {
                throw new IllegalValueException(Content.MESSAGE_CONTENT_CONSTRAINTS);
            }
            CommandResult commandresult = logic.execute(String.format(COMMAND_EDIT,
                    logic.getFilteredTaskList().indexOf(taskToEdit) + INDEX, PREFIX_CONTENT + newContent));
            setStyleToIndicateCommandSuccess();
            raise(new NewResultAvailableEvent(commandresult.feedbackToUser));
        } catch (CommandException e) {
            setStyleToIndicateCommandFailure();
            logger.info("Cannot find Task: " + newContent);
            raise(new NewResultAvailableEvent(e.getMessage()));
        } catch (IllegalValueException e) {
            setStyleToIndicateCommandFailure();
            logger.info("Invalid Content: " + newContent);
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    /**
     * @param ReadOnlyTask task
     * task cannot be null.
     */
    public void loadTaskPage(ReadOnlyTask task) {
        assert task != null;

        contentTextArea.setText(task.getContent().toString());

        contentTextArea.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                saveAndShowContent(task, contentTextArea.getText().trim());
            }
        });
    }

    /**
     * @param ReadOnlyTask task
     * task cannot be null, load task on UI after editing.
     */
    public void loadTaskDescription(ReadOnlyTask task) {
        if (!compareWithoutNewLine(task.getContent().toString(), contentTextArea.getText())) {
            contentTextArea.setText(task.getContent().toString());
        }
    }

    /**
     * @param String textOne to be compared with textTwo.
     * @param String textTwo to be compared with textOne.
     * @return true if textOne w/o newline equals to textTwo w/o newline else return false.
     */
    public boolean compareWithoutNewLine(String textOne, String textTwo) {
        String trimmedOne = textOne.replaceAll("\\n", CliSyntax.EMPTY_STRING);
        String trimmedTwo = textTwo.replaceAll("\\n", CliSyntax.EMPTY_STRING);
        return trimmedOne.equals(trimmedTwo);
    }

    /**
     * Sets the Task Description style to indicate a successful command.
     */
    private void setStyleToIndicateCommandSuccess() {
        contentTextArea.getStyleClass().remove(CommandBox.ERROR_STYLE_CLASS);
    }

    /**
     * Sets the Task Description style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        contentTextArea.getStyleClass().add(CommandBox.ERROR_STYLE_CLASS);
    }

}


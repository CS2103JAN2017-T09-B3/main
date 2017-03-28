package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;

//@@author A0135807A
/**
 * The Task Description of the App.
 */
public class TaskDescription extends UiPart<Region> {

    private static final String MESSAGE_SUPPORT = "Data will be saved when you press SPACE,ENTER,BACKSPACE & DELETE.";
    private static final String FXML = "TaskDescription.fxml";
    private static final String COMMAND_EDIT = "edit %1$s %2$s";
    private static final String UNABLE_TO_EDIT = "Unable to edit Task Content";


    private Logic logic;
    private String oldContent; //store previous Content
    private String newContent; //store latest Content

    @FXML
    private TextArea content;

    public String getOldContent() {
        return oldContent;
    }

    public String getNewContent() {
        return newContent;
    }

    public void setOldContent(String newContent) {
        this.oldContent = newContent;
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
        content.setText(MESSAGE_SUPPORT);
        content.setWrapText(true);
        FxViewUtil.applyAnchorBoundaryParameters(content, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().addAll(content);
        this.logic = logic;
        setOldContent("");
    }

    public void saveAndShowContent(ReadOnlyTask taskToEdit, String newContent) {
        try {
            logic.execute(String.format(
                    COMMAND_EDIT,
                    logic.getFilteredTaskList().indexOf(taskToEdit) + 1,
                    PREFIX_CONTENT + newContent));
        } catch (CommandException ce) {
            new CommandException(UNABLE_TO_EDIT);
        }
    }

    public void loadTaskPage(ReadOnlyTask task) {
        setOldContent(task.getContent().toString());
        content.setText(task.getContent().toString());
        setNewContent(task.getContent().toString());

        content.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldContent, String newContent) {
                setNewContent(newContent);
            }
        });
        content.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER
                    || keyEvent.getCode() == KeyCode.BACK_SPACE || keyEvent.getCode() == KeyCode.DELETE ) {
              setOldContent(getNewContent());
              saveAndShowContent(task, getNewContent());
            }
        });
    }

}

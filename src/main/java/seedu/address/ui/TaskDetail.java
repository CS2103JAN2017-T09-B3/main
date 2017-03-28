package seedu.address.ui;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME_START;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentTokenizer.Prefix;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;

//@@author A0135807A
/**
 * Displays the task details and content.
 */
public class TaskDetail extends UiPart<Region> {

    private static final String FXML = "TaskDetail.fxml";
    private static final String COMMAND_EDIT = "edit %1$s %2$s";
    private static final String UNABLE_TO_EDIT = "Unable to edit Time/Tags";
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
     * @param placeholder The AnchorPane where the TaskDetail must be inserted
     */
    public TaskDetail(AnchorPane placeholder, Logic logic) {
        super(FXML);

        labelTaskTitle.setStyle("-fx-text-fill: white");
        labelStartTime.setStyle("-fx-text-fill: white");
        labelEndTime.setStyle("-fx-text-fill: white");
        labelTags.setStyle("-fx-text-fill: white");

        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().addAll(getRoot());
        this.logic = logic;
    }

    public void saveAndShowContent(ReadOnlyTask taskToEdit, Prefix prefix,
            String newContent, TextField field) {
        try {
            logic.execute(String.format(
                    COMMAND_EDIT,
                    logic.getFilteredTaskList().indexOf(taskToEdit) + 1,
                    prefix.toString() + newContent));
        } catch (CommandException ce) {
            new CommandException(UNABLE_TO_EDIT);
        }
        field.setText(newContent);
    }

    public void loadTaskPage(ReadOnlyTask task) {
        String taggings = "";
        title.setText(task.getTitle().toString());
        title.editableProperty().set(false);;

        startTime.setText(task.getDateTime().getStartDateTime().isPresent() ?
                task.getDateTime().getStartDateTime().get().getDateValue() : "");
        endTime.setText(task.getDateTime().getEndDateTime().isPresent() ?
                task.getDateTime().getEndDateTime().get().getDateValue() : "");

        for (Tag tag: task.getTags()) {
            taggings += tag.toString();
        }
        tags.setText(taggings);
        tags.editableProperty().set(false);

        startTime.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldStartTime, String newStartTime) {
                saveAndShowContent(task, PREFIX_DATE_TIME_START, newStartTime, startTime);
            }
        });

        endTime.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldEndTime, String newEndTime) {
                saveAndShowContent(task, PREFIX_DATE_TIME_END, newEndTime, endTime);
            }
        });
    }

}

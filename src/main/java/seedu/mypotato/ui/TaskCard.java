package seedu.mypotato.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.mypotato.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label title;
    @FXML
    private Label dateTime;
    @FXML
    private Label content;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        title.setText(task.getTitle().fullTitle + " ");
        id.setText(displayedIndex + ".");
        dateTime.setText(task.getDateTime().toString());
        content.setText(task.getContent().fullContent);
        if (task.getStatus().getStatus()) {
            title.setStyle("-fx-text-fill: green");
            id.setStyle("-fx-text-fill: green");
            dateTime.setStyle("-fx-text-fill: green");
            content.setStyle("-fx-text-fill: green");
        }
        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}

package seedu.myPotato.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.myPotato.MainApp;
import seedu.myPotato.commons.core.ComponentManager;
import seedu.myPotato.commons.core.Config;
import seedu.myPotato.commons.core.LogsCenter;
import seedu.myPotato.commons.events.model.ReadFirstTaskEvent;
import seedu.myPotato.commons.events.storage.DataSavingExceptionEvent;
import seedu.myPotato.commons.events.ui.JumpToListRequestEvent;
import seedu.myPotato.commons.events.ui.ShowHelpRequestEvent;
import seedu.myPotato.commons.events.ui.SwitchToTabRequestEvent;
import seedu.myPotato.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.myPotato.commons.events.ui.UpdateStatusBarFooterEvent;
import seedu.myPotato.commons.events.ui.UpdateUiTaskDescriptionEvent;
import seedu.myPotato.commons.util.StringUtil;
import seedu.myPotato.logic.Logic;
import seedu.myPotato.model.UserPrefs;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/address_book_32.png";
    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    private Logic logic;
    private Config config;
    private UserPrefs prefs;
    private MainWindow mainWindow;

    public UiManager(Logic logic, Config config, UserPrefs prefs) {
        super();
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        primaryStage.setTitle(config.getAppTitle());

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = new MainWindow(primaryStage, config, prefs, logic);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stop() {
        prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
        mainWindow.hide();
    }

    private void showFileOperationAlertAndWait(String description, String details, Throwable cause) {
        final String content = details + ":\n" + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, "File Op Error", description, content);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    //==================== Event Handling Code ===============================================================

    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait("Could not save data", "Could not save data to file", event.exception);
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.handleHelp();
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getTaskListPanel().scrollTo(event.targetIndex);
    }

    @Subscribe
    private void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.loadTaskPage(event.getNewSelection());
    }

    //author A0144895N
    @Subscribe
    private void handleSwitchToTabRequestEvent(SwitchToTabRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.switchTab(event.getTargetTab());
    }

    //author A0135807A
    @Subscribe
    private void handleUpdateStatusBarFooterEvent(UpdateStatusBarFooterEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.updateStatusBarFooter();
    }

    @Subscribe
    private void handleUpdateUiTaskDescriptionEvent(UpdateUiTaskDescriptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.refreshPage(event.getTask());
    }

    @Subscribe
    private void handleReadFirstTaskEvent(ReadFirstTaskEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.loadTaskPage(event.getTask());
    }

}

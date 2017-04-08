package seedu.myPotato.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.myPotato.commons.core.ComponentManager;
import seedu.myPotato.commons.core.LogsCenter;
import seedu.myPotato.logic.commands.Command;
import seedu.myPotato.logic.commands.CommandResult;
import seedu.myPotato.logic.commands.exceptions.CommandException;
import seedu.myPotato.logic.parser.Parser;
import seedu.myPotato.model.Model;
import seedu.myPotato.model.task.ReadOnlyTask;
import seedu.myPotato.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new Parser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}

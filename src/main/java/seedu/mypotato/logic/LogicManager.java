package seedu.mypotato.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.mypotato.commons.core.ComponentManager;
import seedu.mypotato.commons.core.LogsCenter;
import seedu.mypotato.logic.commands.Command;
import seedu.mypotato.logic.commands.CommandResult;
import seedu.mypotato.logic.commands.exceptions.CommandException;
import seedu.mypotato.logic.parser.Parser;
import seedu.mypotato.model.Model;
import seedu.mypotato.model.task.ReadOnlyTask;
import seedu.mypotato.storage.Storage;

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

package ru.andrey.kvstorage;

import ru.andrey.kvstorage.console.DatabaseCommandResult;
import ru.andrey.kvstorage.console.ExecutionEnvironment;
import ru.andrey.kvstorage.exception.DatabaseException;
import ru.andrey.kvstorage.logic.DatabaseCommands;

public class DatabaseServer {

    private final ExecutionEnvironment env;

    public DatabaseServer(ExecutionEnvironment env) {
        this.env = env;
    }

    public static void main(String[] args) {

    }

    DatabaseCommandResult executeNextCommand(String commandText) {
        if (commandText == null) {
            return new DatabaseCommandResult.DatabaseCommandResultClass(false, "Command Text is not given");
        }
        try {
            return DatabaseCommands.commandRun(commandText, env);
        } catch (DatabaseException | IllegalArgumentException e) {
            return new DatabaseCommandResult.DatabaseCommandResultClass(false, e.getMessage());
        }
    }
}

package ru.andrey.kvstorage.console;

import ru.andrey.kvstorage.exception.DatabaseException;
import ru.andrey.kvstorage.logic.Database;

import java.util.Optional;

public class CommandCreateTable implements DatabaseCommand {
    ExecutionEnvironment env;
    String databaseName;
    String tableName;

    public CommandCreateTable(ExecutionEnvironment env, String databaseName, String tableName) {
        this.databaseName = databaseName;
        this.tableName = tableName;
        this.env = env;
    }

    @Override
    public DatabaseCommandResult execute() {
        try {
            Optional<Database> database = env.getDatabase(databaseName);
            if (database.isPresent()) {
                Database databaseValue = database.get();
                databaseValue.createTableIfNotExists(tableName);
                return new DatabaseCommandResult.DatabaseCommandResultClass(true, null);
            }
            return new DatabaseCommandResult.DatabaseCommandResultClass(false, "There is no such database");
        } catch (DatabaseException e) {
            return new DatabaseCommandResult.DatabaseCommandResultClass(false, e.getMessage());
        }
    }
}

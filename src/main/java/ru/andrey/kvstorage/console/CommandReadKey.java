package ru.andrey.kvstorage.console;

import ru.andrey.kvstorage.exception.DatabaseException;
import ru.andrey.kvstorage.logic.Database;

import java.util.Optional;

public class CommandReadKey implements DatabaseCommand {
    private ExecutionEnvironment env;
    private String databaseName;
    private String tableName;
    private String key;

    public CommandReadKey(ExecutionEnvironment env, String databaseName, String tableName, String key) {
        this.env = env;
        this.databaseName = databaseName;
        this.tableName = tableName;
        this.key = key;
    }

    @Override
    public DatabaseCommandResult execute() {
        if (env == null || databaseName == null || tableName == null || key == null) {
            return new DatabaseCommandResult.DatabaseCommandResultClass(false, "Some parameters are missing");
        }
        try {
            Optional<Database> database = env.getDatabase(databaseName);
            if (database.isPresent()) {
                Database databaseValue = database.get();
                databaseValue.read(tableName, key);
                return new DatabaseCommandResult.DatabaseCommandResultClass(true, null);
            }
            return new DatabaseCommandResult.DatabaseCommandResultClass(false, "There is no such database");
        } catch (DatabaseException e) {
            return new DatabaseCommandResult.DatabaseCommandResultClass(false, e.getMessage());
        }
    }
}

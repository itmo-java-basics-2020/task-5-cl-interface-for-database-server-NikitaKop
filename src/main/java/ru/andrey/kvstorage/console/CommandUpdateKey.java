package ru.andrey.kvstorage.console;

import ru.andrey.kvstorage.exception.DatabaseException;
import ru.andrey.kvstorage.logic.Database;

import java.util.Optional;

public class CommandUpdateKey implements DatabaseCommand {
    private ExecutionEnvironment env;
    private String databaseName;
    private String tableName;
    private String key;
    private String value;

    public CommandUpdateKey(ExecutionEnvironment env, String databaseName, String tableName, String key, String value) {
        this.env = env;
        this.databaseName = databaseName;
        this.tableName = tableName;
        this.key = key;
        this.value = value;
    }

    @Override
    public DatabaseCommandResult execute() {
        if (env == null || databaseName == null || tableName == null || key == null || value == null) {
            return new DatabaseCommandResult.DatabaseCommandResultClass(false, "Some parameters are missing");
        }
        try {
            Optional<Database> database = env.getDatabase(databaseName);
            if (database.isPresent()) {
                Database databaseValue = database.get();
                databaseValue.write(tableName, key, value);
                return new DatabaseCommandResult.DatabaseCommandResultClass(true, null);
            }
            return new DatabaseCommandResult.DatabaseCommandResultClass(false, "There is no such database");
        } catch (DatabaseException | NullPointerException e) {
            return new DatabaseCommandResult.DatabaseCommandResultClass(false, e.getMessage());
        }
    }
}

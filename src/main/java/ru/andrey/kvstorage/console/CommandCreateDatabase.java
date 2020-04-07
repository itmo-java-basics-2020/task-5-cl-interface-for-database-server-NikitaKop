package ru.andrey.kvstorage.console;

import ru.andrey.kvstorage.logic.Database;
import ru.andrey.kvstorage.logic.DatabaseClass;

public class CommandCreateDatabase implements DatabaseCommand {
    private ExecutionEnvironment env;
    private String databaseName;

    public CommandCreateDatabase(ExecutionEnvironment env, String name) {
        this.env = env;
        this.databaseName = name;
    }

    @Override
    public DatabaseCommandResult execute() {
        Database database = new DatabaseClass(env, databaseName);
        env.addDatabase(database);
        if (env == null || databaseName == null) {
            return new DatabaseCommandResult.DatabaseCommandResultClass(false, "Some parameters are missing");
        }
        return new DatabaseCommandResult.DatabaseCommandResultClass(true, null);
    }
}

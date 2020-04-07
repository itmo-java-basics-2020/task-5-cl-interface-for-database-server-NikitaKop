package ru.andrey.kvstorage.logic;

import ru.andrey.kvstorage.console.*;
import ru.andrey.kvstorage.exception.DatabaseException;

interface Commands {
    DatabaseCommand executeCommand(ExecutionEnvironment env, String[] args);
}

public enum DatabaseCommands implements Commands {
    CREATE_DATABASE {
        @Override
        public DatabaseCommand executeCommand(ExecutionEnvironment env, String[] parameters) {
            return new CommandCreateDatabase(env, parameters[1]);
        }
    },
    CREATE_TABLE {
        @Override
        public DatabaseCommand executeCommand(ExecutionEnvironment env, String[] parameters) {
            return new CommandCreateTable(env, parameters[1], parameters[2]);
        }
    },
    UPDATE_KEY {
        @Override
        public DatabaseCommand executeCommand(ExecutionEnvironment env, String[] parameters) {
            return new CommandUpdateKey(env, parameters[1], parameters[2], parameters[3], parameters[4]);
        }
    },
    READ_KEY {
        @Override
        public DatabaseCommand executeCommand(ExecutionEnvironment env, String[] parameters) {
            return new CommandReadKey(env, parameters[1], parameters[2], parameters[3]);
        }
    };

    public static DatabaseCommandResult commandRun(String args, ExecutionEnvironment env) throws DatabaseException {
        String[] parameters = args.split(" ");
        var command = DatabaseCommands.valueOf(parameters[0]).executeCommand(env, parameters);
        return command.execute();
    }
}

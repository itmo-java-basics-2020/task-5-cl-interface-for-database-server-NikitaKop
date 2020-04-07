package ru.andrey.kvstorage.logic;

import ru.andrey.kvstorage.console.ExecutionEnvironment;
import ru.andrey.kvstorage.exception.DatabaseException;

import java.util.List;

public class DatabaseClass implements Database{
    private String name;
    private List<String> currentTables;
    private ExecutionEnvironment env;

    public DatabaseClass(ExecutionEnvironment env, String databaseName) {
        this.env = env;
        this.name = databaseName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void createTableIfNotExists(String tableName) throws DatabaseException {

    }

    @Override
    public void createTableIfNotExists(String tableName, int segmentSizeInBytes) throws DatabaseException {

    }

    @Override
    public void write(String tableName, String objectKey, String objectValue) throws DatabaseException {

    }

    @Override
    public String read(String tableName, String objectKey) throws DatabaseException {
        return null;
    }
}

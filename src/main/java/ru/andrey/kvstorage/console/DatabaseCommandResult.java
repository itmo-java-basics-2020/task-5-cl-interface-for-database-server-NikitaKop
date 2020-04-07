package ru.andrey.kvstorage.console;

import java.util.Optional;

public interface DatabaseCommandResult {

    Optional<String> getResult();

    DatabaseCommandStatus getStatus();

    boolean isSuccess();

    String getErrorMessage();

    enum DatabaseCommandStatus {
        SUCCESS, FAILED
    }



    class DatabaseCommandResultClass implements DatabaseCommandResult {
        private boolean success;
        private String errorMessage;

        public DatabaseCommandResultClass(boolean success, String errorMessage) {
            this.success = success;
            this.errorMessage = errorMessage;
        }

        @Override
        public DatabaseCommandStatus getStatus() {
            if (isSuccess()) {
                return DatabaseCommandStatus.SUCCESS;
            }
            return DatabaseCommandStatus.FAILED;
        }

        @Override
        public Optional<String> getResult() {
            switch (getStatus()) {
                case SUCCESS:
                    return Optional.of("Command completed successfully");
                case FAILED:
                    return Optional.of("An error occurred during execution" + getErrorMessage());
            }
            return Optional.empty();
        }

        @Override
        public boolean isSuccess() {
            return success;
        }

        @Override
        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
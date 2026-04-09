package database.exception;

public enum DataAccessError {

    ;

    private final String message;

    DataAccessError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}

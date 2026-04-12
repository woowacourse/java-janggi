package exception.custom;

import exception.InfraErrorMessage;

public class DatabaseConnectionException extends InfraException {
    public DatabaseConnectionException(){
        super(InfraErrorMessage.DEFAULT_DATABASE_CONNECTION_ERROR.getMessage());
    }

    public DatabaseConnectionException(String message){
        super(message);
    }
}

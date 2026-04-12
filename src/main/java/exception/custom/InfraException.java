package exception.custom;

import exception.InfraErrorMessage;

public class InfraException extends RuntimeException {
    private final String message;

    public InfraException() {
        this.message = InfraErrorMessage.DEFAULT_INFRA_ERROR.getMessage();
    }

    protected InfraException(String message) {
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}

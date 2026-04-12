package exception.custom;

import exception.InfraErrorMessage;

public class ConfigLoadException extends InfraException {
    public ConfigLoadException(){
        super(InfraErrorMessage.DEFAULT_CONFIG_LOAD_ERROR.getMessage());
    }

    public ConfigLoadException(String message){
        super(message);
    }
}

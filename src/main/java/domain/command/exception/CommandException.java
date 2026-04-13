package domain.command.exception;

import common.exception.JanggiException;

public class CommandException extends JanggiException {

    public CommandException(String errorMessage) {
        super(errorMessage);
    }

}

package domain.game.exception;

import domain.exception.JanggiException;

public class InvalidTurnException extends JanggiException {

    public InvalidTurnException(GameErrorMessage errorMessage) {
        super(errorMessage.message());
    }
}

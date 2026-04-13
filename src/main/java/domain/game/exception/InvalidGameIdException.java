package domain.game.exception;

import domain.exception.JanggiException;

public class InvalidGameIdException extends JanggiException {

    public InvalidGameIdException(GameErrorMessage errorMessage) {
        super(errorMessage.message());
    }
}
